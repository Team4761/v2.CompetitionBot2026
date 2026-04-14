package frc.robot.commandGroups;

import java.io.SequenceInputStream;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Constants.Slider;
import frc.robot.subsystems.kicker.KickerSubsystem;
import frc.robot.subsystems.kicker.commands.KickAtSpeedCommand;
import frc.robot.subsystems.kicker.commands.RunKickerAtPercentCommand;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.shooter.commands.backspin.RunBackspinCommand;
import frc.robot.subsystems.shooter.commands.spitter.RunSpitterCommand;
import frc.robot.subsystems.slider.SliderSubsystem;
import frc.robot.subsystems.slider.commands.RunSliderAtSpeedCommand;

public class ShootWithPowerCommand extends ParallelCommandGroup {
    public ShootWithPowerCommand(ShooterSubsystem shooter, SliderSubsystem slider, KickerSubsystem kicker, DoubleSupplier powerSupplier) {
        super(
            new RunSpitterCommand(shooter, powerSupplier.getAsDouble()),//InstantCommand(() -> shooter.spitterMotor.setRawSpeed(12000)), // [TODO] Change this to a real command that sets the spitter motors to the correct speed based on the supplied power
            new RunBackspinCommand(shooter, -Constants.Shooter.ShootConfig.BACKSPIN_RPM),//InstantCommand(() -> shooter.backspinMotor.setRawSpeed(-12000)), // [TODO] Change this to a real command that sets the backspin motors to the correct speed based on the supplied power
            new SequentialCommandGroup(
                new WaitCommand(0.5), // [TODO] Remove this and make the above command into a real command that times out when its up to speed
                new ParallelCommandGroup(
                    new KickAtSpeedCommand(kicker, -Constants.Kicker.KICKER_SPEED), // [TODO] Change this to a real command that sets the kicker motors to the correct speed based on the supplied power
                    new RunSliderAtSpeedCommand(slider, Constants.Slider.SLIDER_RPM)
                )
            )
            //new RunKickerAtPercentCommand(kicker, () -> 1000, () -> 1000)
        );
    }
}
