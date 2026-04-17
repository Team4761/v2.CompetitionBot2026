package frc.robot.subsystems.snatcher.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.snatcher.SnatcherSubsystem;
import frc.robot.Constants;
import frc.robot.subsystems.leds.LEDSubsystem;

public class SnatchCommand extends Command{
    private SnatcherSubsystem snatcherSubsystem;

    public SnatchCommand(SnatcherSubsystem sub) {
        this.snatcherSubsystem = sub;
        addRequirements(sub);
    }

    @Override
    public void initialize() {
        snatcherSubsystem.snatcherMotor.disableCoasting();
        snatcherSubsystem.snatcherMotor.setSpeedPercent(Constants.Snatcher.SNATCHER_SPEED_PERCENT);
        LEDSubsystem.isSnatching = 0.0;
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isInterrupted) {
        snatcherSubsystem.snatcherMotor.enableCoasting();
        snatcherSubsystem.snatcherMotor.stopTurning();
        LEDSubsystem.isSnatching = 1.0;
    }
    
}
