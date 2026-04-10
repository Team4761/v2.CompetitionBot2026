package frc.robot.commands.snatcher;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.snatcher.SnatcherSubsystem;

public class JostleSnatcherCommand extends Command{
    private SnatcherSubsystem snatcherSubsystem;
    private boolean runningNegative = true;

    public JostleSnatcherCommand(SnatcherSubsystem sub) {
        this.snatcherSubsystem = sub;
        addRequirements(sub);
    }

    @Override
    public void initialize() {
        snatcherSubsystem.snatcherMotor.setSpeedPercent(-1);
    }

    public void execute() {
        if (snatcherSubsystem.snatcherMotor.getAngle() >= 90 && !runningNegative) {//90 is temp value we need the angle for down
            snatcherSubsystem.snatcherMotor.setSpeedPercent(-1);
            runningNegative = true;
        } else if (snatcherSubsystem.snatcherMotor.getAngle() >= 90 && runningNegative){
            snatcherSubsystem.snatcherMotor.setSpeedPercent(1);
            runningNegative = false;
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isInterrupted){
        if (!(snatcherSubsystem.snatcherMotor.getAngle() >= 90)) {
            snatcherSubsystem.snatcherMotor.setSpeedPercent(1);
            while(!(snatcherSubsystem.snatcherMotor.getAngle() >= 90)) {}
            snatcherSubsystem.snatcherMotor.stopTurning();
        } else {
            snatcherSubsystem.snatcherMotor.stopTurning();
        }
    }
}
