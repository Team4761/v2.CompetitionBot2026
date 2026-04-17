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
        for (int i = 0; i < 2; i++) {
            snatcherSubsystem.snatcherMotor.turn(20);
            snatcherSubsystem.snatcherMotor.turn(-20);
        }
    }

    public void execute() {}

    @Override
    public boolean isFinished() {
        return true;
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
        snatcherSubsystem.snatcherMotor.set(-70);
    }
}
