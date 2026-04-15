package frc.robot.subsystems.shooter.commands;



import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class BackspinAndSpitCommand extends Command {
    private final ShooterSubsystem shooterSubsystem;
    private final double spitterSpeed;
    private final double backspinSpeed;

    public BackspinAndSpitCommand(ShooterSubsystem sub, double backspinSpeed, double spitterSpeed) {
        this.shooterSubsystem = sub;
        this.backspinSpeed = backspinSpeed;
        this.spitterSpeed = spitterSpeed;
        addRequirements(sub);
    }

    @Override
    public void initialize() {
        this.shooterSubsystem.backspinMotor.setRawSpeed(this.backspinSpeed);
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
        this.shooterSubsystem.backspinMotor.stopTurning();
    }
}
