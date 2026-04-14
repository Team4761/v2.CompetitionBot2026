package frc.robot.subsystems.shooter.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class RunShooterAtPowerCommand extends Command {
    private final ShooterSubsystem shooterSubsystem;
    private final DoubleSupplier spitterPowerSupplier;
    private final DoubleSupplier backspinSpeedSupplier;

    public RunShooterAtPowerCommand(
        ShooterSubsystem shooterSubsystem,
        DoubleSupplier spitterPowerSupplier,
        double backspinSpeed
    ) {
        this(shooterSubsystem, spitterPowerSupplier, () -> backspinSpeed);
    }

    public RunShooterAtPowerCommand(
        ShooterSubsystem shooterSubsystem,
        DoubleSupplier spitterPowerSupplier,
        DoubleSupplier backspinSpeedSupplier
    ) {
        this.shooterSubsystem = shooterSubsystem;
        this.spitterPowerSupplier = spitterPowerSupplier;
        this.backspinSpeedSupplier = backspinSpeedSupplier;
        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() {
        applyOutputs();
    }

    @Override
    public void execute() {
        applyOutputs();
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.spitterMotor.stopTurning();
        shooterSubsystem.backspinMotor.stopTurning();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    private void applyOutputs() {
        shooterSubsystem.spitterMotor.setRawSpeed(spitterPowerSupplier.getAsDouble());
        shooterSubsystem.backspinMotor.setSpeed(backspinSpeedSupplier.getAsDouble());
    }
}
