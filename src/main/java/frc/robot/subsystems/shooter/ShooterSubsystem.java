package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.SmartTalonMotor;

public class ShooterSubsystem extends SubsystemBase {

    public final SmartTalonMotor hoodMotor;
    public final SmartTalonMotor spitterMotorLeft;
    public final SmartTalonMotor spitterMotorRight;
    public final SmartTalonMotor backspinMotor;

    //[TODO]Set correct values for PID, output & angle limits, gear ratio, motor mode for all motors
    public ShooterSubsystem() {
        this.hoodMotor = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Shooter.HOOD_MOTOR_PORT)
            .PID(1,0,0)
            .outputRange(-1,-1)
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            .mechanismAngleLimits(
                Constants.Shooter.Hood.MIN_HOOD_ANGLE_DEGREES, 
                Constants.Shooter.Hood.MAX_HOOD_ANGLE_DEGREES
                )
            .gearRatio(Constants.Shooter.Hood.HOOD_ROTATIONS_PER_MOTOR_ROTATION)
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
        this.backspinMotor = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Shooter.ShootConfig.BACKSPIN_MOTOR_PORT) // 
            .PID(0.1, 0.0, 0.0) // Temp Values
            .outputRange(-1.0, 1.0) // Duty cycle output limits
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            //.gearRatio()
            .build();
        
    }

    
    

}
