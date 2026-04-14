package frc.robot.subsystems.snatcher.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.snatcher.SnatcherSubsystem;



public class SmackdownSnatcherCommand extends Command {
    private SnatcherSubsystem snatcherSubsystem;
    private int isStable = 0;
    
    public SmackdownSnatcherCommand(SnatcherSubsystem sub) {
        this.snatcherSubsystem = sub;
        addRequirements(sub);
    }

    @Override
    public void initialize() {
        snatcherSubsystem.snatcherMotor.set(90);
    }

    public void execute() {
        if (snatcherSubsystem.snatcherMotor.getAngle() >= 90) {//90 is temp value we need the angle for down
            isStable++;
        } else {
            isStable = 0;
        }
    }

    @Override
    public boolean isFinished() {
        return (isStable >= 10);
    }

    @Override
    public void end(boolean isInterrupted){
        snatcherSubsystem.snatcherMotor.stopTurning();
    } 
}
