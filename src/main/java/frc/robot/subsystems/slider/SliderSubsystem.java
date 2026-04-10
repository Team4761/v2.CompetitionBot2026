package frc.robot.subsystems.slider;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.SmartTalonMotor;

public class SliderSubsystem extends SubsystemBase{
    public SmartTalonMotor sliderMotor;

    public SliderSubsystem() {
        this.sliderMotor = SmartTalonMotor.Builder.newInstance()
            .port(-1) // [TODO] Set correct port
            .PID(0.1, 0.0, 0.0) // Temp Values
            .outputRange(-1.0, 1.0) // Duty cycle output limits
            .mode(SmartTalonMotor.MotorMode.CONTINUOUS)
            //.gearRatio()
            .build();
    }

    
    public void setSliderMotorSpeed(double rpm) {
        this.sliderMotor.setSpeed(rpm);
    }
    
}
