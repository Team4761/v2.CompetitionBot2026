package frc.robot.commandGroups;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.shooter.commands.hood.SetHoodAngleCommand;

//Dragomir and Julian helped extensively
public class ShootWithPowerAndAngle extends SequentialCommandGroup{
    public ShootWithPowerAndAngle(ShooterSubsystem sub, DoubleSupplier degrees, DoubleSupplier power){
        addCommands(
            SetHoodAngleCommand(sub, degrees);
        );
    }
}
