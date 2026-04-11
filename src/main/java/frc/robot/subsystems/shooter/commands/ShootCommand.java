package frc.robot.subsystems.shooter.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.Slider;
import frc.robot.subsystems.kicker.KickerSubsystem;
import frc.robot.subsystems.kicker.commands.KickCommand;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.slider.SliderSubsystem;
import frc.robot.subsystems.slider.commands.SpinSliderCommand;

public class ShootCommand extends SequentialCommandGroup{
    private KickerSubsystem kickerSubsystem;
    private SliderSubsystem sliderSubsystem;
    private ShooterSubsystem shooterSubsystem;

    public ShootCommand(KickerSubsystem subKick, SliderSubsystem subSlide, ShooterSubsystem subShoot) {
        addCommands(
            new SpinSliderCommand(subSlide),
            new KickCommand(subKick),
            new RunSpitterCommand(subShoot)
        );
    }
}
