package frc.robot.subsystems.leds;
import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;

import java.util.Map;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.LEDPattern.GradientType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.leds.StupidColor;
import frc.robot.subsystems.leds.StoopidColors;


@SuppressWarnings("unused")
public class LEDSubsystem extends SubsystemBase {
    public static AddressableLED leds;
    public static AddressableLEDBuffer buffer;

    //no, the type "double" is not a mistake
    //note: must make each false condition set it to a UNIQUE out of bounds spot
    
    public static double isSnatching = 1.0;
    public static double isSliding = 1.1;
    public static double isShooting = 1.2;
    public static double isDriving = 1.3;

    private LEDPattern previousPattern;
    public static LEDPattern currentPattern;

    public enum LEDMode {
        SOLID,
        BLINK_SLOW,
        BLINK_FAST
    }

    //In a simulation, the R and G values will be switched, as that is how we correctly display it on the robot
    //example: Color = ERROR -> displays green instead of red
    public enum LEDColor{
        ERROR,
        WARNING,
        INFO,
        SUCCESS,
        NONE
    }

        
    // We will have a 16x1 strip

    // IMPORTANT: If the LEDs are in GRB instead of RGB again, please infom me (Alex Maniscalco). I already have a fix for it
    // also ask/tell me about any other problems or conflicts this subsystem makes

    /** Available LED patterns:
     * <p> LEDs that have a certain part light up a certain color when one or more functions are being performed.
     * <p> LEDs that either blink or stay solid for a certain color, which is explicitly defined in the code (for now)
     * <p> lights that move across the strip, and change to a random color when bounce off the edge (finished, but not yet migrated to this repository)
     * <p> LED patterns that aren't finished:
     * <p> Rickroll (any dimensions)
     */
    
    public LEDSubsystem() {
        // Comment out patterns that aren't being used
        leds = new AddressableLED(Constants.LEDs.LEDS_PORT);
        buffer = new AddressableLEDBuffer(Constants.LEDs.NUMBER_OF_LEDS); // 16 LEDs in a straight line
        leds.setLength(Constants.LEDs.NUMBER_OF_LEDS);
        leds.start();
        
        currentPattern = RobocketsLEDPatterns.OFF;
        previousPattern = RobocketsLEDPatterns.OFF;

    }


    /*
       !!!!IMPORTANT!!!!
       DO NOT SET ANY COLOR ABOVE 127. IT USES A SHIT TON OF POWER. AND STROUT WILL FUCKIONG KILL YOU.
       !!!!IMPORTANT!!!!
    */



    public static StupidColor setPatternColor(LEDColor color) {
        if(color == LEDColor.ERROR){
            return new StupidColor(Color.kRed);
        }
        else if(color == LEDColor.WARNING){
            return new StupidColor(Color.kYellow);
        }
        else if(color == LEDColor.INFO){
            return new StupidColor(Color.kBlue);
        }
        else if(color == LEDColor.SUCCESS){
            return new StupidColor(Color.kGreen);
        }
        else{
            return new StupidColor(Color.kBlanchedAlmond);
        }
    } 

    public static LEDPattern setPatternMode(LEDMode mode){
        RobocketsLEDPatterns.setSingleColor = LEDPattern.solid(setPatternColor(RobocketsLEDPatterns.currentColor));
        if(mode == LEDMode.SOLID){
            return RobocketsLEDPatterns.setSingleColor = LEDPattern.solid(setPatternColor(RobocketsLEDPatterns.currentColor));
        }
        else if(mode == LEDMode.BLINK_SLOW){
            return RobocketsLEDPatterns.setSingleColor = RobocketsLEDPatterns.setSingleColor.blink(Seconds.of(1));
        }
        else{
            return RobocketsLEDPatterns.setSingleColor = RobocketsLEDPatterns.setSingleColor.blink(Seconds.of(0.15));
        }
    }
    
    //int[][][][] testVideoAsCompressedArrayComingToTheatersMarch32_2111 = compressVideo(loadVideoAsMultipleImages("C:/Users/alex/Documents/CODINF/RickrollFragments/frame.png", 1, 100, true),32,8);
    //public static double freme = 0;
    public void periodic(){
        setPatternMode(RobocketsLEDPatterns.currentMode);
        setPattern(RobocketsLEDPatterns.setSingleColor);
        // This pattern emulates a Rickroll, with the height and width of it being adjustable
        // this will unfourtunately not be run during matches due to power concerns, and the fact that it is way too slow to load
        /**
        for (int y = 0; y < testVideoAsCompressedArrayComingToTheatersMarch32_2111[(int) freme].length; y++)
        {
            for (int x = 0; x < testVideoAsCompressedArrayComingToTheatersMarch32_2111[(int) freme][y].length; x++)
            {
                buffer.setRGB(x + (y * Constants.LEDS_WIDTH), testVideoAsCompressedArrayComingToTheatersMarch32_2111[(int) freme][y][x][0], testVideoAsCompressedArrayComingToTheatersMarch32_2111[(int) freme][y][x][1], testVideoAsCompressedArrayComingToTheatersMarch32_2111[(int) freme][y][x][2]);
            }
        }
        if(freme < testVideoAsCompressedArrayComingToTheatersMarch32_2111.length-1){
            freme += 0.5;
        }
        */
        currentPattern.applyTo(buffer);
        leds.setData(buffer);
    }


    public void setPattern(LEDPattern pattern) {
        previousPattern = currentPattern;
        currentPattern = pattern;
        pattern.applyTo(buffer);
        leds.setData(buffer);
    }


    /**
     * This gets the last pattern that was used. This starts out as RobocketsLEDPatterns.OFF
     * @return The previously used LED Pattern
     */
    public LEDPattern getPreviousPattern() {
        return previousPattern;
    }


    /**
     * This sets the LEDs to the "black" color (which turns them off)
     */
    public void stopLEDs() {
        LEDPattern off = LEDPattern.solid(Color.kBlack);
        off.applyTo(buffer);
        leds.setData(buffer);
    }
        
    // the command that aplies the pattern to the LEDs
    public Command runPattern(LEDPattern pattern) {
        return run(() -> setPattern(pattern));
    }
}