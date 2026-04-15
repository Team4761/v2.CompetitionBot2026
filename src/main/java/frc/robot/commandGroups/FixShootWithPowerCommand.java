package frc.robot.commandGroups;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.Kicker;
import frc.robot.subsystems.kicker.KickerSubsystem;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.slider.SliderSubsystem;

/*
 * Generalized shoot command. It shoots in an arc.
 */
public class FixShootWithPowerCommand extends Command {
    private final ShooterSubsystem shooterSubsystem;
    private final KickerSubsystem kickerSubsystem;
    private final SliderSubsystem sliderSubsystem;
    
    private final DoubleSupplier rpmSupplier;
    private final Timer feederDelayTimer = new Timer();
    private boolean feedersStarted;

    /**
     * @param sub The turret subsystem holding the shooter components
     * @param spitterSpeed Speed for the main flywheel/shooter
     * @param kickerSpeed Speed for the feed mechanism (kicker)
     */
    public FixShootWithPowerCommand(ShooterSubsystem shooter, SliderSubsystem slider, KickerSubsystem kicker, double rpm) {
        this(shooter, slider, kicker, () -> rpm);
    }

    public FixShootWithPowerCommand(ShooterSubsystem shooter, SliderSubsystem slider, KickerSubsystem kicker, DoubleSupplier rpmSupplier) {
        this.shooterSubsystem = shooter;
        this.sliderSubsystem = slider;
        this.kickerSubsystem = kicker;
        this.rpmSupplier = rpmSupplier;
        
        // IMPORTANT: Intentionally NOT use addRequirements(sub) here.
        // If require the TurretSubsystem, will interrupt the TurretManualAimCommand,
        // which prevents the operator from aiming while shooting. (I think)
    }

    @Override
    public void initialize() {
        feedersStarted = false;
        feederDelayTimer.restart();
        this.shooterSubsystem.spitterMotor.setRawSpeed(this.rpmSupplier.getAsDouble());
    }

    @Override
    public void execute() {
        // Delay feeding without stalling the scheduler thread.
        if (!feedersStarted && feederDelayTimer.hasElapsed(0.5)) {
            kickerSubsystem.fatKickerInnerMotor.setRawSpeed(-Constants.Kicker.KICKER_SPEED);
            kickerSubsystem.fatKickerOuterMotor.setRawSpeed(-Constants.Kicker.KICKER_SPEED);

            shooterSubsystem.backspinMotor.setRawSpeed(-Constants.Shooter.ShootConfig.BACKSPIN_RPM);

            this.sliderSubsystem.sliderMotor.setRawSpeed(Constants.Slider.SLIDER_RPM);
            feedersStarted = true;
        }
    }

    @Override
    public boolean isFinished() { 
        return false; 
    }

    @Override
    public void end(boolean isInterrupted){
        feederDelayTimer.stop();
        this.kickerSubsystem.fatKickerInnerMotor.stopTurning();
        this.kickerSubsystem.fatKickerOuterMotor.stopTurning();
        this.shooterSubsystem.backspinMotor.stopTurning();
        this.sliderSubsystem.sliderMotor.stopTurning();
    }
}
