package frc.robot.subsystems.shooter.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.Shooter.ShootConfig;
import frc.robot.subsystems.kicker.KickerSubsystem;
import frc.robot.subsystems.kicker.commands.KickCommand;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.slider.SliderSubsystem;
import frc.robot.subsystems.slider.commands.SpinSliderCommand;

public class ShootCommand extends ParallelCommandGroup{

    public ShootCommand(KickerSubsystem subKick, SliderSubsystem subSlide, ShooterSubsystem subShoot) {
        addCommands(
            new SpinSliderCommand(subSlide),
            new WaitCommand(ShootConfig.KICKER_INIT_DELAY).andThen(new KickCommand(subKick)),
            new RunSpitterCommand(subShoot)
        );
    }
}
