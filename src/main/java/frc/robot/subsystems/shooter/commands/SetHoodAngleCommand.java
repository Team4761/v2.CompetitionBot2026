package frc.robot.subsystems.shooter.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class SetHoodAngleCommand extends Command {

    private ShooterSubsystem shooterSubsystem;
    private double degrees;

    public SetHoodAngleCommand(ShooterSubsystem sub, double degrees) {
        this.shooterSubsystem = sub;
        this.degrees = degrees;
        addRequirements(sub);
    }

    @Override
    public void initialize() {
        this.shooterSubsystem.hoodMotor.set(degrees);
    }

    @Override
    public void execute() {
        //keep motors running
    }

    @Override
    public boolean isFinished() {
        return this.shooterSubsystem.hoodMotor.getAngle() == degrees;
    }

    @Override
    public void end(boolean isInterrupted) {
        this.shooterSubsystem.hoodMotor.stopTurning();
    }
}
