package frc.robot.subsystems.leds;

import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;

import java.util.Map;

import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants.LEDs;
import frc.robot.subsystems.leds.LEDSubsystem;
import frc.robot.subsystems.leds.LEDSubsystem.LEDColor;
import frc.robot.subsystems.leds.LEDSubsystem.LEDMode;


/**
 * This stores all* of our LED patterns.
 * <p> *exceptions include: display video
 */
public class RobocketsLEDPatterns {
    public static final LEDPattern OFF = LEDPattern.solid(Color.kBlack);
    public static final LEDPattern TEST = LEDPattern.solid(Color.kRed);

    // This pattern is for debugging. lights up a certain part of the LEDs with a color corresponding with a certain robot function, if said function is running.
    public static LEDPattern debugFunctions = LEDPattern.steps(Map.of(
        LEDSubsystem.isSnatching, new StupidColor(Color.kRed),
        LEDSubsystem.isSliding, new StupidColor(Color.kGreen), 
        LEDSubsystem.isShooting, new StupidColor(Color.kBlue), //currently not implemented
        LEDSubsystem.isDriving, new StupidColor(Color.kYellow) //currently not implemented
    ));

    public static LEDPattern setSingleColor;
    //set the color and mode of the pattern here
    public static LEDColor currentColor = LEDColor.INFO;
    public static LEDMode currentMode = LEDMode.BLINK_SLOW;

    public static LEDPattern bouncePattern;
    public static LEDPattern continuousGradientScroll = LEDPattern.gradient(LEDPattern.GradientType.kContinuous, Color.kRed, StoopidColors.kAlmostBlack).scrollAtRelativeSpeed(Percent.per(Second).of(25));
}
