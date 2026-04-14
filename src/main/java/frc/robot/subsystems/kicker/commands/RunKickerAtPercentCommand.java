package frc.robot.subsystems.kicker.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.kicker.KickerSubsystem;

public class RunKickerAtPercentCommand extends Command {
    private final KickerSubsystem kickerSubsystem;
    private final DoubleSupplier innerPercentSupplier;
    private final DoubleSupplier outerPercentSupplier;

    public RunKickerAtPercentCommand(
        KickerSubsystem kickerSubsystem,
        double innerPercent,
        double outerPercent
    ) {
        this(kickerSubsystem, () -> innerPercent, () -> outerPercent);
    }

    public RunKickerAtPercentCommand(
        KickerSubsystem kickerSubsystem,
        DoubleSupplier innerPercentSupplier,
        DoubleSupplier outerPercentSupplier
    ) {
        this.kickerSubsystem = kickerSubsystem;
        this.innerPercentSupplier = innerPercentSupplier;
        this.outerPercentSupplier = outerPercentSupplier;
        addRequirements(kickerSubsystem);
    }

    @Override
    public void initialize() {
        applyOutputs();
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
        kickerSubsystem.fatKickerInnerMotor.stopTurning();
        kickerSubsystem.fatKickerOuterMotor.stopTurning();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    private void applyOutputs() {
        kickerSubsystem.fatKickerInnerMotor.setRawSpeed(innerPercentSupplier.getAsDouble());
        kickerSubsystem.fatKickerOuterMotor.setRawSpeed(outerPercentSupplier.getAsDouble());
    }
}
