package frc.robot.commands.snatcher;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.snatcher.SnatcherSubsystem;



public class SmackdownSnatcherCommand extends Command {
    private SnatcherSubsystem snatcherSubsystem;
    private int isStable = 0;
    
    public SmackdownSnatcherCommand(SnatcherSubsystem sub) {
        this.snatcherSubsystem = sub;
        addRequirements(sub);
    }

    public void initialize() {
        snatcherSubsystem.snatcherMotor.setSpeedPercent(1);
    }

    public void execute() {
        if (snatcherSubsystem.snatcherMotor.getAngle() >= 90) {//90 is temp value we need the angle for down
            isStable++;
        } else {
            isStable = 0;
        }
    }

    public boolean isFinished() {
        return (isStable >= 10);
    }

    public void end(boolean isInterrupted){
        snatcherSubsystem.snatcherMotor.stopTurning();
    } 
}
