package frc.robot.subsystems.snatcher.commands;

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
        snatcherSubsystem.snatcherMotor.set(90);
    }

    public void execute() {//can maybe use set angle
        if (snatcherSubsystem.snatcherMotor.getAngle() >= 90 && !runningNegative) {//90 is temp value might need to be negative because gear ratio 
            snatcherSubsystem.snatcherMotor.setSpeedPercent(-1);
            runningNegative = true;
        } else if (snatcherSubsystem.snatcherMotor.getAngle() <= 40 && runningNegative){//50 is a temp value might need to be negative because gear ratio 
            snatcherSubsystem.snatcherMotor.setSpeedPercent(1);
            runningNegative = false;
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    //this is one possible solution for ensuring the intake goes back down after jostling but we can also just run extendintake command on false when we bind to buttons
    @Override
    public void end(boolean isInterrupted){
        /*if (!(snatcherSubsystem.snatcherMotor.getAngle() >= 90)) {
            snatcherSubsystem.snatcherMotor.setSpeedPercent(1);
            while(!(snatcherSubsystem.snatcherMotor.getAngle() >= 90)) {}
            snatcherSubsystem.snatcherMotor.stopTurning();
        } else {
            snatcherSubsystem.snatcherMotor.stopTurning();
        }*/
        snatcherSubsystem.snatcherMotor.set(90);
    }
}
