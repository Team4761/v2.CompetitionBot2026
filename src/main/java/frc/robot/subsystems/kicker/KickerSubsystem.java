package frc.robot.subsystems.kicker;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.SmartTalonMotor;

public class KickerSubsystem extends SubsystemBase {

    public final SmartTalonMotor fatKickerInnerMotor;
    public final SmartTalonMotor fatKickerOuterMotor;

    //[TODO]Set correct values for PID, output & angle range, gear ratio, motor mode for all motors
    public KickerSubsystem() {
        this.fatKickerInnerMotor = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Kicker.FAT_KICKER_INNER_MOTOR_PORT)
            .PID(1,0,0)
            .outputRange(-1,-1)
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            //.gearRatio()
            .build();
        this.fatKickerOuterMotor = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Kicker.FAT_KICKER_OUTER_MOTOR_PORT)
            .PID(1,0,0)
            .outputRange(-1,-1)
            //.gearRatio()
            .build();

    }

}
