package frc.robot.subsystems.shooter.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import frc.robot.subsystems.kicker.KickerSubsystem;
import frc.robot.subsystems.kicker.commands.UnKickCommand;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.slider.SliderSubsystem;
import frc.robot.subsystems.slider.commands.UnSpinSliderCommand;

public class UnShootCommand extends ParallelCommandGroup{

    public UnShootCommand(KickerSubsystem subKick, SliderSubsystem subSlide, ShooterSubsystem subShoot) {
        addCommands(
            new UnSpinSliderCommand(subSlide),
            new UnKickCommand(subKick),
            new UnRunSpitterCommand(subShoot)
        );
    }
}
