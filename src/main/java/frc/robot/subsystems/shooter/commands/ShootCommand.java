package frc.robot.subsystems.shooter.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.kicker.KickerSubsystem;
import frc.robot.subsystems.kicker.commands.KickCommand;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.shooter.commands.spitter.RunSpitterCommand;
import frc.robot.subsystems.slider.SliderSubsystem;
import frc.robot.subsystems.slider.commands.SpinSliderCommand;

public class ShootCommand extends ParallelCommandGroup{

    public ShootCommand(KickerSubsystem subKick, SliderSubsystem subSlide, ShooterSubsystem subShoot) {
        addCommands(
            new SpinSliderCommand(subSlide),
            new WaitCommand(0.5).andThen(new KickCommand(subKick)),
            new RunSpitterCommand(subShoot, Constants.Shooter.ShootConfig.MEDIUM_SPITTER_SPEED) // Change these values properly
        );
    }
}
