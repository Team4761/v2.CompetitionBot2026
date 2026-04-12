package frc.robot.subsystems.shooter.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class ShootWithPowerCommand extends Command {
    private final ShooterSubsystem m_shooter;

    public ShootWithPowerCommand(ShooterSubsystem shooter, double power) {
        this.m_shooter = shooter;
        addRequirements(shooter);
    }

    public ShootWithPowerCommand(ShooterSubsystem shooter, DoubleSupplier powerSupplier) {
        this.m_shooter = shooter;
        addRequirements(shooter);
    }
}
