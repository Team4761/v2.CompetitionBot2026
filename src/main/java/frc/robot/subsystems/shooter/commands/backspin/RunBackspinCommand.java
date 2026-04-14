package frc.robot.subsystems.shooter.commands.backspin;



import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class RunBackspinCommand extends Command {
    private final ShooterSubsystem shooterSubsystem;
    private final double backspinSpeed;

    public RunBackspinCommand(ShooterSubsystem sub, double backspinSpeed) {
        this.shooterSubsystem = sub;
        this.backspinSpeed = backspinSpeed;
        addRequirements(sub);
    }

    @Override
    public void initialize() {
        this.shooterSubsystem.backspinMotor.setRawSpeed(this.backspinSpeed);
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
        this.shooterSubsystem.backspinMotor.stopTurning();
    }
}
