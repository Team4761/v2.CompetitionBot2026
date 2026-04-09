package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.SmartTalonMotor;

public class ShooterSubsystem extends SubsystemBase {
    
    public final SmartTalonMotor fatKickerInnerMotor;
    public final SmartTalonMotor fatKickerOuterMotor;

    //[TODO]Set correct values for PID, output & angle range, gear ratio, motor mode for all motors
    public ShooterSubsystem() {
        this.fatKickerInnerMotor = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Shooter.FAT_KICKER_INNER_MOTOR_PORT)
            .PID(1,0,0)
            .outputRange(-1,-1)
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            //.gearRatio()
            .build();
        this.fatKickerOuterMotor = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Shooter.FAT_KICKER_OUTER_MOTOR_PORT)
            .PID(1,0,0)
            .outputRange(-1,-1)
            //.gearRatio()
            .build();
        
        
    }

}
