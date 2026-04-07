package frc.robot.subsystems.gyro;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.SmartKrakenMotor;

public class ShooterSubsystem extends SubsystemBase {
    
    public final SmartKrakenMotor fatKickerInnerMotor;

    public ShooterSubsystem() {
        this.fatKickerInnerMotor = SmartKrakenMotor.Builder.newInstance()
            .port(Constants.Shooter.FAT_KICKER_INNER_MOTOR_PORT)
            .PID(1,0,0) //[TODO] set right values
            .outputRange(-1,-1)//[TODO] set right values
            .mode(SmartKrakenMotor.MotorMode.CONTINUOUS)//[TODO] set right motor mode
            //.gearRatio() [TODO] find gearRatio
            .build();
        
    }

}
