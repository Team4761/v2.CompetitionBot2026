package frc.robot.subsystems.kicker.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.kicker.KickerSubsystem;

public class KickCommand extends Command{
    private KickerSubsystem kickerSubsystem;

    public KickCommand(KickerSubsystem sub) {
        this.kickerSubsystem = sub;
        addRequirements(sub);
    }

    @Override
    public void initialize() {
        kickerSubsystem.fatKickerInnerMotor.setSpeed(Constants.Kicker.KICKER_SPEED);
        kickerSubsystem.fatKickerOuterMotor.setSpeed(-1 * Constants.Kicker.KICKER_SPEED);
    }
    
    public void execute() {
        // No need to set the motor speed again since it's already set in initialize() and this command is meant to run until interrupted
    }

    @Override
    public void end(boolean interrupted) {
        kickerSubsystem.fatKickerInnerMotor.stopTurning();
        kickerSubsystem.fatKickerInnerMotor.stopTurning();
    }

    @Override
    public boolean isFinished() {
        return false; // This command will run until interrupted
    }
}
