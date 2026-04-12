package frc.robot.commandGroups;

import java.io.SequenceInputStream;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Constants.Slider;
import frc.robot.subsystems.kicker.KickerSubsystem;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.slider.SliderSubsystem;

public class ShootWithPowerCommand extends SequentialCommandGroup {
    public ShootWithPowerCommand(ShooterSubsystem shooter, SliderSubsystem slider, KickerSubsystem kicker, DoubleSupplier powerSupplier) {
        super(
            new InstantCommand(() -> shooter.spitterMotor.setSpeedPercent(-0.05)),
            //new WaitCommand(0.5), // [TODO] Remove this and make the above command into a real command that times out when its up to speed
            new InstantCommand(() -> shooter.backspinMotor.setSpeedPercent(-0.1)),
            new InstantCommand(() -> kicker.fatKickerInnerMotor.setSpeedPercent(-0.1)),
            new InstantCommand(() -> kicker.fatKickerOuterMotor.setSpeedPercent(-0.1)),
            new InstantCommand(() -> slider.sliderMotor.setSpeedPercent(0.1))
        );
    }
}
