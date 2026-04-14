package frc.robot.subsystems.shooter.commands.spitter;



import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class RunSpitterCommand extends Command {
    private final ShooterSubsystem shooterSubsystem;
    private final double spitterSpeed;

    public RunSpitterCommand(ShooterSubsystem sub, double spitterSpeed) {
        this.shooterSubsystem = sub;
        this.spitterSpeed = spitterSpeed;
        addRequirements(sub);
    }

    @Override
    public void initialize() {
        this.shooterSubsystem.spitterMotor.setRawSpeed(this.spitterSpeed);
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
    }
}
