package frc.robot.subsystems.kicker.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.kicker.KickerSubsystem;

public class KickAtSpeedCommand extends Command{
    private KickerSubsystem kickerSubsystem;
    private double speed;

    public KickAtSpeedCommand(KickerSubsystem sub, double speed) {
        this.kickerSubsystem = sub;
        this.speed = speed;
        addRequirements(sub);
    }

    @Override
    public void initialize() {
        kickerSubsystem.fatKickerInnerMotor.setRawSpeed(speed);
        kickerSubsystem.fatKickerOuterMotor.setRawSpeed(speed); // uknown if this sohuld be negated 
    }
    
    public void execute() {
        // No need to set the motor speed again since it's already set in initialize() and this command is meant to run until interrupted
    }

    @Override
    public void end(boolean interrupted) {
        kickerSubsystem.fatKickerInnerMotor.stopTurning();
        kickerSubsystem.fatKickerOuterMotor.stopTurning();
    }

    @Override
    public boolean isFinished() {
        return false; // This command will run until interrupted
    }
}
