package frc.robot.subsystems.slider;

import edu.wpi.first.wpilibj2.command.Command;

public class SpinSliderCommand extends Command{
    private final SliderSubsystem sliderSubsystem;
    private final double rpm;
    public SpinSliderCommand(SliderSubsystem sliderSubsystem, double rpm) {
        this.sliderSubsystem = sliderSubsystem;
        this.rpm = rpm;
        addRequirements(sliderSubsystem);
    }

    @Override
    public void initialize() {
        sliderSubsystem.setSliderMotorSpeed(rpm);
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