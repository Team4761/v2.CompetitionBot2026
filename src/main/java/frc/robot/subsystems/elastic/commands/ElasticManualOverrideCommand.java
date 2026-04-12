package frc.robot.subsystems.elastic.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * This command is a command for the Elastic Dashboard: it sets the "Manual Turret Control" interface to true or false.
 */
public class ElasticManualOverrideCommand extends InstantCommand{
    public ElasticManualOverrideCommand(boolean override) {
        super(() -> SmartDashboard.putBoolean("Manual Turret Control", override));
    }
}
