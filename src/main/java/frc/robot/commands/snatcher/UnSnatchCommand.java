package frc.robot.commands.snatcher;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.snatcher.SnatcherSubsystem;

public class UnSnatchCommand extends Command{
    private SnatcherSubsystem snatcherSubsystem;

    public UnSnatchCommand(SnatcherSubsystem sub) {
        this.snatcherSubsystem = sub;
        addRequirements(sub);
    }

    @Override
    public void initialize() {
        snatcherSubsystem.snatcherMotor.disableCoasting();
        snatcherSubsystem.snatcherMotor.setSpeedPercent(-1);
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
    }
    
}
