package frc.robot.subsystems.gyro;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.SmartKrakenMotor;

public class ShooterSubsystem extends SubsystemBase {
    
    public final SmartKrakenMotor fatKickerInnerMotor;
    public final SmartKrakenMotor fatKickerOuterMotor;

    //[TODO]Set correct values for PID, output & angle range, gear ratio, motor mode for all motors
    public ShooterSubsystem() {
        this.fatKickerInnerMotor = SmartKrakenMotor.Builder.newInstance()
            .port(Constants.Shooter.FAT_KICKER_INNER_MOTOR_PORT)
            .PID(1,0,0)
            .outputRange(-1,-1)
            .mode(SmartKrakenMotor.MotorMode.CONTINUOUS)
            //.gearRatio()
            .build();
        this.fatKickerOuterMotor = SmartKrakenMotor.Builder.newInstance()
            .port(Constants.Shooter.FAT_KICKER_OUTER_PORT)
            .PID(1,0,0)
            .outputRange(-1,-1)
            //.gearRatio()
            .build()
        
        
    }

}
