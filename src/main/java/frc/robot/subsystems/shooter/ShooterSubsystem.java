package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.SmartTalonMotor;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

public class ShooterSubsystem extends SubsystemBase {

    public final SmartTalonMotor hoodMotor;
    public final SmartTalonMotor spitterMotor; // Left
    public final SmartTalonMotor spitterMotor2; // Right
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
        this.spitterMotor = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Shooter.SPITTER_MOTOR_LEAD_PORT)
            .PID(0.1,0,0)
            .outputRange(-1,-1)
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            //.gearRatio()
            .build();
        this.spitterMotor2 = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Shooter.SPITTER_MOTOR_FOLLOWER_PORT)
            .PID(0.1,0,0)
            .outputRange(-1,-1)
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            //.gearRatio()
            .build();
        this.backspinMotor = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Shooter.ShootConfig.BACKSPIN_MOTOR_PORT)
            .PID(0.1, 0.0, 0.0) // Temp Values
            .outputRange(-1.0, 1.0) // Duty cycle output limits
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            //.gearRatio()
            .build();
        
        this.spitterMotor2.motor.setControl(
            new Follower(
                this.spitterMotor.motor.getDeviceID(), 
                MotorAlignmentValue.Opposed
            )
        );
    }
}
