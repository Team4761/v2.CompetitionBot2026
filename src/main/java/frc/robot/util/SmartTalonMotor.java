package frc.robot.util;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import java.util.logging.Logger;

/**
 * SmartKrakenMotor wraps a TalonFX (Kraken X60) and adds:
 *   - Unified gear-ratio handling so all public API is in *mechanism* units
 *     (degrees / RPM at the output shaft), while raw* variants operate in
 *     *motor* units (rotations / RPM at the motor shaft).
 *   - Soft angle limits stored and compared in a single, consistent unit
 *     (mechanism degrees).
 *   - WRAPPED mode for continuous mechanisms (e.g. turrets) that should
 *     fold angles into [0, 360). Soft limits are ignored in WRAPPED mode
 *     because the mechanism has no hard stops.
 *
 * Gear ratio convention: gearRatio = motorRotations / mechanismRotations.
 *   e.g. a 4:1 reduction → gearRatio = 4.0
 *        one mechanism degree → 4/360 motor rotations
 */
public class SmartTalonMotor {

    public enum MotorMode {
        /** Mechanism has hard stops; soft limits are enforced. */
        CONTINUOUS,
        /** Mechanism wraps around (e.g. turret); soft limits are ignored. */
        WRAPPED
    }

    // -----------------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------------

    private final TalonFXConfiguration config = new TalonFXConfiguration();
    private final PositionDutyCycle positionRequest = new PositionDutyCycle(0);
    private final DutyCycleOut dutyCycleRequest = new DutyCycleOut(0);
    public final TalonFX motor;

    /** motorRotations / mechanismRotations */
    private final double gearRatio;

    /**
     * Soft limits stored in *mechanism degrees*.
     * Both are -1 when no limits are configured.
     */
    private final double minAngleDeg;
    private final double maxAngleDeg;

    private final MotorMode mode;
    private boolean coastingEnabled = false;

    private static final Logger LOGGER = Logger.getLogger(SmartTalonMotor.class.getName());
    // -----------------------------------------------------------------------
    // Constructor (via Builder)
    // -----------------------------------------------------------------------

    public SmartTalonMotor(Builder builder) {
        this.motor = new TalonFX(builder.port);
        this.gearRatio = builder.gearRatio;
        this.mode = builder.mode;

        this.config.Slot0.kP = builder.p;
        this.config.Slot0.kI = builder.i;
        this.config.Slot0.kD = builder.d;
        this.config.MotorOutput.PeakForwardDutyCycle = builder.maxOutput;
        this.config.MotorOutput.PeakReverseDutyCycle = builder.minOutput;
        this.motor.getConfigurator().apply(this.config);
        this.motor.setPosition(0.0);

        // Resolve soft limits into mechanism degrees (one canonical unit).
        if (builder.mechMinAngle != -1) {
            // Already in mechanism degrees — use directly.
            this.minAngleDeg = builder.mechMinAngle;
            this.maxAngleDeg = builder.mechMaxAngle;
        } else if (builder.motorMinAngle != -1) {
            // Convert motor degrees → mechanism degrees.
            this.minAngleDeg = builder.motorMinAngle / this.gearRatio;
            this.maxAngleDeg = builder.motorMaxAngle / this.gearRatio;
        } else {
            this.minAngleDeg = -1;
            this.maxAngleDeg = -1;
        }
    }

    // -----------------------------------------------------------------------
    // Duty-cycle (speed percent) control  — unchanged, always "raw"
    // -----------------------------------------------------------------------

    /** Set motor output directly as a duty cycle in [-1.0, 1.0]. */
    public void setRawSpeedPercent(double speedPercent) {
        this.motor.setControl(this.dutyCycleRequest.withOutput(clampDutyCycle(speedPercent)));
    }

    /** Convenience alias — duty cycle has no gear-ratio concept. */
    public void setSpeedPercent(double speedPercent) {
        setRawSpeedPercent(speedPercent);
    }

    // -----------------------------------------------------------------------
    // Velocity control
    // -----------------------------------------------------------------------

    /**
     * Set motor shaft velocity in RPM (motor side, before the gearbox).
     * Use when you want direct, unscaled motor control.
     */
    public void setRawSpeed(double motorRPM) {
        // TalonFX VelocityDutyCycle takes rotations-per-second.
        this.motor.setControl(new VelocityDutyCycle(motorRPM / 60.0));
    }

    /**
     * Set mechanism output velocity in RPM (after the gearbox).
     * Converts to motor RPM internally.
     */
    public void setSpeed(double mechRPM) {
        setRawSpeed(mechRPM * this.gearRatio);
    }

    // -----------------------------------------------------------------------
    // Position control — "set" (absolute) variants
    // -----------------------------------------------------------------------

    /**
     * Move to an absolute position given in *motor rotations*.
     * No soft-limit check — caller is responsible for bounds.
     *
     * @param motorRotations target position in raw motor rotations
     * @return always true (no limit check performed)
     */
    public boolean rawSet(double motorRotations) {
        this.motor.setControl(this.positionRequest.withPosition(motorRotations));
        return true;
    }

    /**
     * Move to an absolute position given in *mechanism degrees*.
     * Enforces soft limits in CONTINUOUS mode; wraps in WRAPPED mode.
     *
     * @param mechDegrees target mechanism angle in degrees
     * @return true if the command was sent; false if blocked by soft limits
     */
    public boolean set(double mechDegrees) {
        double target = mechDegrees;

        if (this.mode == MotorMode.WRAPPED) {
            // Fold into [0, 360). No soft-limit check for wrapped mechanisms.
            target = ((target % 360.0) + 360.0) % 360.0;
            this.motor.setControl(this.positionRequest.withPosition(toMotorRotations(target)));
            return true;
        }

        // CONTINUOUS: enforce soft limits.
        if (isWithinLimits(target)) {
            this.motor.setControl(this.positionRequest.withPosition(toMotorRotations(target)));
            return true;
        }

        LOGGER.warning(String.format(
            "[set] Rejected angle %.2f°; limits are [%.2f°, %.2f°]",
            target, this.minAngleDeg, this.maxAngleDeg));
        return false;
    }

    // -----------------------------------------------------------------------
    // Position control — "turn" (relative) variants
    // -----------------------------------------------------------------------

    /**
     * Move *relative* to the current position by a given number of *motor rotations*.
     * No soft-limit check — caller is responsible for bounds.
     *
     * @param motorRotations delta to apply in raw motor rotations
     * @return always true (no limit check performed)
     */
    public boolean rawTurn(double motorRotations) {
        double currentMotorRotations = this.motor.getPosition().getValueAsDouble();
        this.motor.setControl(
            this.positionRequest.withPosition(currentMotorRotations + motorRotations)
        );
        return true;
    }

    /**
     * Move *relative* to the current position by a given number of *mechanism degrees*.
     * Enforces soft limits in CONTINUOUS mode; wraps in WRAPPED mode.
     *
     * @param mechDegrees delta in mechanism degrees (positive = forward)
     * @return true if the command was sent; false if blocked by soft limits
     */
    public boolean turn(double mechDegrees) {
        double target = getAngle() + mechDegrees;

        if (this.mode == MotorMode.WRAPPED) {
            target = ((target % 360.0) + 360.0) % 360.0;
            this.motor.setControl(this.positionRequest.withPosition(toMotorRotations(target)));
            return true;
        }

        if (isWithinLimits(target)) {
            this.motor.setControl(this.positionRequest.withPosition(toMotorRotations(target)));
            return true;
        }

        LOGGER.warning(String.format(
            "[turn] Rejected target angle %.2f°; limits are [%.2f°, %.2f°]",
            target, this.minAngleDeg, this.maxAngleDeg));
        return false;
    }

    // -----------------------------------------------------------------------
    // Getters
    // -----------------------------------------------------------------------

    /** Current mechanism angle in degrees (after gearbox). */
    public double getAngle() {
        return toMechanismDegrees(this.motor.getPosition().getValueAsDouble());
    }

    /** Current motor shaft angle in raw rotations (before gearbox). */
    public double getRawAngle() {
        return this.motor.getPosition().getValueAsDouble();
    }

    /** Current mechanism output speed in RPM (after gearbox). */
    public double getSpeedRPM() {
        return (this.motor.getVelocity().getValueAsDouble() * 60.0) / this.gearRatio;
    }

    /** Current motor shaft speed in RPM (before gearbox). */
    public double getRawSpeedRPM() {
        return this.motor.getVelocity().getValueAsDouble() * 60.0;
    }

    // -----------------------------------------------------------------------
    // Stop / coasting
    // -----------------------------------------------------------------------

    /**
     * Stop moving. If coasting is disabled, hold the current position with PID.
     * If coasting is enabled, the motor will coast to a stop.
     */
    public void stopTurning() {
        if (this.coastingEnabled) {
            this.motor.setControl(this.dutyCycleRequest.withOutput(0.0));
        } else {
            // Hold position by commanding the current motor position.
            this.motor.setControl(
                this.positionRequest.withPosition(this.motor.getPosition().getValueAsDouble())
            );
        }
    }

    public void enableCoasting() {
        this.coastingEnabled = true;
        this.motor.setControl(this.dutyCycleRequest.withOutput(0.0));
    }

    public void disableCoasting() {
        this.coastingEnabled = false;
        this.motor.setControl(
            this.positionRequest.withPosition(this.motor.getPosition().getValueAsDouble())
        );
    }

    // -----------------------------------------------------------------------
    // Private helpers
    // -----------------------------------------------------------------------

    /** Convert mechanism degrees → motor rotations. */
    private double toMotorRotations(double mechDegrees) {
        return (mechDegrees / 360.0) * this.gearRatio;
    }

    /** Convert motor rotations → mechanism degrees. */
    private double toMechanismDegrees(double motorRotations) {
        return (motorRotations * 360.0) / this.gearRatio;
    }

    /**
     * Returns true if no limits are configured, or if {@code mechDegrees}
     * falls within [minAngleDeg, maxAngleDeg].
     */
    private boolean isWithinLimits(double mechDegrees) {
        if (this.minAngleDeg == -1 && this.maxAngleDeg == -1) return true;
        return mechDegrees >= this.minAngleDeg && mechDegrees <= this.maxAngleDeg;
    }

    private static double clampDutyCycle(double output) {
        return Math.max(-1.0, Math.min(1.0, output));
    }

    // -----------------------------------------------------------------------
    // Builder
    // -----------------------------------------------------------------------

    public static class Builder {
        private int port;
        private double p, i, d;
        private double minOutput = -1.0;
        private double maxOutput = 1.0;
        private double motorMinAngle = -1;
        private double motorMaxAngle = -1;
        private double mechMinAngle = -1;
        private double mechMaxAngle = -1;
        private MotorMode mode = MotorMode.CONTINUOUS;
        private double gearRatio = 1.0;

        public static Builder newInstance() { return new Builder(); }

        public Builder port(int port)                                    { this.port = port; return this; }
        public Builder PID(double p, double i, double d)                 { this.p = p; this.i = i; this.d = d; return this; }
        public Builder outputRange(double min, double max)               { this.minOutput = clampDutyCycle(min); this.maxOutput = clampDutyCycle(max); return this; }
        public Builder motorAngleLimits(double min, double max)          { this.motorMinAngle = min; this.motorMaxAngle = max; return this; }
        public Builder mechanismAngleLimits(double min, double max)      { this.mechMinAngle = min; this.mechMaxAngle = max; return this; }
        public Builder mode(MotorMode mode)                              { this.mode = mode; return this; }
        public Builder gearRatio(double gearRatio)                       { this.gearRatio = gearRatio; return this; }

        public SmartTalonMotor build() { return new SmartTalonMotor(this); }
    }
}