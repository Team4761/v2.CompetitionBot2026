package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.SmartTalonMotor;

public class ShooterSubsystem extends SubsystemBase {

    public final SmartTalonMotor hoodMotor;
    public final SmartTalonMotor spitterMotorLeft;
    public final SmartTalonMotor spitterMotorRight;

    //[TODO]Set correct values for PID, output & angle range, gear ratio, motor mode for all motors
    public ShooterSubsystem() {
        this.hoodMotor = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Shooter.HOOD_MOTOR_PORT)
            .PID(1,0,0)
            .outputRange(-1,-1)
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            .mechanismAngleLimits(10, 45)
            //.gearRatio()
            .build();
        this.spitterMotorLeft = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Shooter.LEFT_SPITTER_MOTOR_PORT)
            .PID(0.1,0,0)
            .outputRange(-1,-1)
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            //.gearRatio()
            .build();
        this.spitterMotorRight = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Shooter.RIGHT_SPITTER_MOTOR_PORT)
            .PID(0.1,0,0)
            .outputRange(-1,-1)
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            //.gearRatio()
            .build();
    }

}
