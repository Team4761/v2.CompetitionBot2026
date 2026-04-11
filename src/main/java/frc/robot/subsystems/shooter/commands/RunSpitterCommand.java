package frc.robot.subsystems.shooter.commands;



import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class RunSpitterCommand extends Command {
    private final ShooterSubsystem shooterSubsystem;


    public RunSpitterCommand(ShooterSubsystem sub) {
        this.shooterSubsystem = sub;
    }

    @Override
    public void initialize() {
        this.shooterSubsystem.spitterMotorLeft.setRawSpeed(Constants.Shooter.ShootConfig.MEDIUM_SPITTER_SPEED);
        this.shooterSubsystem.spitterMotorRight.setRawSpeed(Constants.Shooter.ShootConfig.MEDIUM_SPITTER_SPEED);
        this.shooterSubsystem.backspinMotor.setSpeedPercent(Constants.Shooter.ShootConfig.BACKSPIN_MOTOR_MAX_SPEED);
        
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
        this.shooterSubsystem.spitterMotorLeft.stopTurning();
        this.shooterSubsystem.spitterMotorRight.stopTurning();
        this.shooterSubsystem.backspinMotor.stopTurning();
    }
}
