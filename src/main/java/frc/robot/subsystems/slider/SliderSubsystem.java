package frc.robot.subsystems.slider;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.SmartTalonMotor;

public class SliderSubsystem extends SubsystemBase{

    public final SmartTalonMotor sliderMotor;
    public final SmartTalonMotor backspinMotor;

    public SliderSubsystem() {
        this.sliderMotor = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Slider.SLIDER_MOTOR_PORT) // [TODO] Set correct port
            .PID(0.1, 0.0, 0.0) // Temp Values
            .outputRange(-1.0, 1.0) // Duty cycle output limits
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            //.gearRatio()
            .build();
        this.backspinMotor = SmartTalonMotor.Builder.newInstance()
            .port(Constants.Slider.BACKSPIN_MOTOR_PORT) // [TODO] Set correct port
            .PID(0.1, 0.0, 0.0) // Temp Values
            .outputRange(-1.0, 1.0) // Duty cycle output limits
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            //.gearRatio()
            .build();
    }
    
}
