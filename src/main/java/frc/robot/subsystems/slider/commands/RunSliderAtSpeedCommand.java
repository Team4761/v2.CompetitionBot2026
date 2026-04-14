package frc.robot.subsystems.slider.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.slider.SliderSubsystem;

public class RunSliderAtSpeedCommand extends Command {
    private final SliderSubsystem sliderSubsystem;
    private final double speed;

    public RunSliderAtSpeedCommand(
        SliderSubsystem sliderSubsystem,
        double speed
    ) {
        this.sliderSubsystem = sliderSubsystem;
        this.speed = speed;
        addRequirements(sliderSubsystem);
    }

    @Override
    public void initialize() {
        sliderSubsystem.sliderMotor.setRawSpeed(speed);
    }

    @Override
    public void execute() {   
    }

    @Override
    public void end(boolean interrupted) {
        sliderSubsystem.sliderMotor.stopTurning();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
