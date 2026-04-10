package frc.robot.subsystems.snatcher;
//AKA intake subsystem
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.SmartTalonMotor;

public class SnatcherSubsystem extends SubsystemBase{
    // Make the code aware there should be 2 motors
    public final SmartTalonMotor smackdownMotor;
    public final SmartTalonMotor snatcherMotor;

    // Tell the code what those motor are/should be like and were to find them
    public SnatcherSubsystem() {
        this.smackdownMotor = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Snatcher.SMACKDOWN_MOTOR_PORT)
            .PID(0.5, 0.0, 0.0) // Temp Values
            .outputRange(-1.0, 1.0) // Duty cycle output limits
            .mechanismAngleLimits(Constants.Snatcher.MIN_SMACKDOWN_ANGLE, Constants.Snatcher.MAX_SMACKDOWN_ANGLE)
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            .gearRatio(Constants.Snatcher.MOTOR_ROTATIONS_PER_EXTENDER_ROTATION)
            .build();
        this.snatcherMotor = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Snatcher.SNATCHER_MOTOR_PORT)
            .PID(0.1, 0.0, 0.0) // Temp Values
            .outputRange(-1.0, 1.0) // Duty cycle output limits
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            .build();
    }
}
