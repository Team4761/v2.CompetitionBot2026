package frc.robot.subsystems.shooter.commands;



import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class UnRunSpitterCommand extends Command {
    private final ShooterSubsystem shooterSubsystem;


    public UnRunSpitterCommand(ShooterSubsystem sub) {
        this.shooterSubsystem = sub;
        addRequirements(sub);
    }

    @Override
    public void initialize() {
        this.shooterSubsystem.spitterMotor.setRawSpeed(-1 * Constants.Shooter.ShootConfig.MEDIUM_SPITTER_SPEED);
        this.shooterSubsystem.backspinMotor.setSpeedPercent(-1 * Constants.Shooter.ShootConfig.BACKSPIN_MOTOR_MAX_SPEED);
        
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isInterrupted) {
        this.shooterSubsystem.spitterMotor.stopTurning();
        this.shooterSubsystem.backspinMotor.stopTurning();
    }
}
