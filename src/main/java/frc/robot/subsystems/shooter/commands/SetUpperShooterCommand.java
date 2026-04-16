package frc.robot.subsystems.shooter.commands;



import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class SetUpperShooterCommand extends Command {
    private final ShooterSubsystem shooterSubsystem;
    private final double spitterSpeed;
    private final double backspinSpeed;
    private final double hoodAngle;

    public SetUpperShooterCommand(ShooterSubsystem sub, double backspinSpeed, double spitterSpeed, double hoodAngle) {
        this.shooterSubsystem = sub;
        this.backspinSpeed = backspinSpeed;
        this.spitterSpeed = spitterSpeed;
        this.hoodAngle = hoodAngle;
        addRequirements(sub);
    }

    @Override
    public void initialize() {
        this.shooterSubsystem.backspinMotor.setRawSpeed(this.backspinSpeed);
        this.shooterSubsystem.spitterMotor.setRawSpeed(this.spitterSpeed);
        this.shooterSubsystem.hoodMotor.set(this.hoodAngle);
    }

    @Override
    public void execute() {}

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
