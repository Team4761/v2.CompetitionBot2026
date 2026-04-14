package frc.robot.subsystems.slider.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.slider.SliderSubsystem;

public class RunSliderAtPercentCommand extends Command {
    private final SliderSubsystem sliderSubsystem;
    private final DoubleSupplier speedPercentSupplier;

    public RunSliderAtPercentCommand(SliderSubsystem sliderSubsystem, double speedPercent) {
        this(sliderSubsystem, () -> speedPercent);
    }

    public RunSliderAtPercentCommand(
        SliderSubsystem sliderSubsystem,
        DoubleSupplier speedPercentSupplier
    ) {
        this.sliderSubsystem = sliderSubsystem;
        this.speedPercentSupplier = speedPercentSupplier;
        addRequirements(sliderSubsystem);
    }

    @Override
    public void initialize() {
        applyOutput();
    }

    @Override
    public void execute() {
        applyOutput();
    }

    @Override
    public void end(boolean interrupted) {
        sliderSubsystem.sliderMotor.stopTurning();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    private void applyOutput() {
        sliderSubsystem.sliderMotor.setSpeedPercent(speedPercentSupplier.getAsDouble());
    }
}
