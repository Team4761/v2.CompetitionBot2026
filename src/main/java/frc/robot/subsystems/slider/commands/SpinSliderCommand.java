package frc.robot.subsystems.slider.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.slider.SliderSubsystem;

public class SpinSliderCommand extends Command{
    private final SliderSubsystem sliderSubsystem;
    public SpinSliderCommand(SliderSubsystem sliderSubsystem) {
        this.sliderSubsystem = sliderSubsystem;
        addRequirements(sliderSubsystem);
    }

    @Override
    public void initialize() {
        sliderSubsystem.setSliderMotorSpeed(Constants.Slider.SLIDER_RPM);
    }
    
    public void execute() {
        // No need to set the motor speed again since it's already set in initialize() and this command is meant to run until interrupted
    }

    @Override
    public void end(boolean interrupted) {
        sliderSubsystem.setSliderMotorSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false; // This command will run until interrupted
    }
}