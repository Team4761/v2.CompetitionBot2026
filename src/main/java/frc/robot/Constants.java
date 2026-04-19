package frc.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

import java.util.Map;
import java.util.function.Function;

public final class Constants {
    private Constants() {}
    //#region Robot Constants
    public static final class Robot {
        public static final double ROBOT_WIDTH = Units.inchesToMeters(27.0);
        public static final double ROBOT_LENGTH = Units.inchesToMeters(27.0);
        public static final double ROBOT_HEIGHT = Units.inchesToMeters(21.5); // [TODO] Ask Zach for this value (temp val)

        private Robot() {}
    }
    // #endregion

    public static final class CollectedData {
        public static final Double[] powerBandRanges = {1.36, 1.85, 2.496, 3.118, 3.643, 4.123, 4.562, 4.991, 5.055}; // low < dist <= high
        public static final Double[] anglePoints = {22.0, 22.0, 22.0, 25.03, 26.51, 22.0, 26.11, 27.36, 30.21, 27.08, 28.16, 30.33, 34.31, 36.08, 38.07, 32.0, 35.05, 36.53, 38.01, 38.01, 31.12, 32.32, 33.06, 37.67, 36.13, 36.13, 39.09, 41.0, 35.73, 35.73, 38.07, 42.45, 35.45, 36.82};
        public static final Double[] distPoints = {1.36, 1.49, 1.595, 1.754, 1.85, 1.967, 2.192, 2.338, 2.496, 2.623, 2.738, 2.841, 2.911, 3.009, 3.118, 3.207, 3.296, 3.407, 3.531, 3.643, 3.751, 3.861, 3.959, 4.123, 4.256, 4.386, 4.513, 4.562, 4.641, 4.767, 4.826, 4.978, 4.991, 5.055};
        public static final Double[] timePoints = {};

        private CollectedData() {}
    }

    //#region Controller Constants
    public static final class Controller {
        public static final int DRIVER_PORT = 0;
        public static final int OPERATOR_PORT = 1;

        public static final double TRANSLATION_INPUT_DEADBAND = 0.10; // [TODO] Tune this value
        public static final double ROTATION_INPUT_DEADBAND = 0.12; // [TODO] Tune this value
        public static final double TURRET_INPUT_DEADBAND = 0.10; // [TODO] Tune this value
        public static final double ROTATION_SLEW_RATE_RAD_PER_SEC_SQ = 12.0; // [TODO] Tune this value
        public static final double ROTATION_MULTIPLIER = 2.0; // [TODO] Tune this value

        public static final double TEST_VORTEX_OUTPUT = -1; // [TODO] Tune this value
        public static final double TEST_TALON_OUTPUT = -1;  // [TODO] Tune this value

        private Controller() {}
    }
    // #endregion
    // //#region Dashboard Constants
    public static final class Dashboard {
        public static final String DISTANCE_FROM_HUB_METERS = "Distance From Hub";
        public static final String ELASTIC_SHOOTER_TUNING_ENABLED = "Elastic Shooter Tuning Enabled";
        public static final String ELASTIC_TEST_SHOOTER_RPM = "Elastic Test Shooter RPM";
        public static final String ELASTIC_TEST_HOOD_ANGLE_DEGREES = "Elastic Test Hood Launch Angle";

        public static final double ELASTIC_TEST_SHOOTER_RPM_MIN = 3000.0; // matches Elastic slider min
        public static final double ELASTIC_TEST_SHOOTER_RPM_MAX = 6500.0; // matches Elastic slider max
        public static final double ELASTIC_TEST_HOOD_ANGLE_MIN = Shooter.Hood.MIN_LAUNCH_ANGLE_DEGREES;
        public static final double ELASTIC_TEST_HOOD_ANGLE_MAX = Shooter.Hood.MAX_LAUNCH_ANGLE_DEGREES;

        private Dashboard() {}
    }
    // #endregion

    public static final class Swerve {
        public static final double MAX_DRIVE_SPEED = 0.5; // [TODO] Tune this value

        private Swerve() {}

        public static final class Auto {
            public static final double TRANSLATION_KP = -1.0; // [TODO] Tune this value
            public static final double ROTATION_KP = -1.0; // [TODO] Tune this value

            public static final double MAX_TRANSLATION_SPEED_MPS = 2.0; // [TODO] Tune this value
            public static final double MAX_ROTATION_SPEED_RAD_PER_SEC =
                Units.degreesToRadians(180.0); // [TODO] Tune this value

            public static final double POSITION_TOLERANCE_METERS = 0.03; // [TODO] Tune this value
            public static final double ANGLE_TOLERANCE_DEGREES = 2.0; // [TODO] Tune this value

            private Auto() {}
        }
    }

    public static final class Gyro {
        public static final int PIGEON_ID = 0; // Pigeon 2 is always 0

        private Gyro() {}
    }

    public static final class Vision {
        public static final String NORTH_CAMERA_NAME = "cam1"; // [TODO] Set this to the correct camera name
        public static final String SOUTH_CAMERA_NAME = "cam2"; // [TODO] Set this to the correct camera name
        public static final String LEFT_CAMERA_NAME = "Back Right Cam"; // [TODO] Set this to the correct camera name
        public static final String RIGHT_CAMERA_NAME = "Back Left Cam"; // [TODO] Set this to the correct camera name

        // [TODO] Set these to the correct translations and rotations for all 4 cameras.
        public static final Translation3d NORTH_CAMERA_TRANSLATION =
            new Translation3d(
                Units.inchesToMeters(0),
                Units.inchesToMeters(0),
                Units.inchesToMeters(0)
            );
        public static final Translation3d SOUTH_CAMERA_TRANSLATION =
            new Translation3d(
                Units.inchesToMeters(0),
                Units.inchesToMeters(0),
                Units.inchesToMeters(0)
            );
        public static final Translation3d LEFT_CAMERA_TRANSLATION =
            new Translation3d(
                Units.inchesToMeters(-3.7),
                Units.inchesToMeters(13.5),
                Units.inchesToMeters(7.5)
            );
        public static final Translation3d RIGHT_CAMERA_TRANSLATION =
            new Translation3d(
                Units.inchesToMeters(-3.7),
                Units.inchesToMeters(-13.5),
                Units.inchesToMeters(7.5)
            );
        
        public static final Rotation3d NORTH_CAMERA_ROTATION =
            new Rotation3d(0.0, 0.0, Math.toRadians(135.0));
        public static final Rotation3d SOUTH_CAMERA_ROTATION =
            new Rotation3d(0.0, 0.0, Math.toRadians(135.0));
        public static final Rotation3d LEFT_CAMERA_ROTATION =
            new Rotation3d(0.0, 0.0, Math.toRadians(90.0));
        public static final Rotation3d RIGHT_CAMERA_ROTATION =
            new Rotation3d(0.0, 0.0, Math.toRadians(-90.0));
        
        public static final Transform3d NORTH_CAMERA_TRANSFORM =
            new Transform3d(NORTH_CAMERA_TRANSLATION, NORTH_CAMERA_ROTATION);
        public static final Transform3d SOUTH_CAMERA_TRANSFORM =
            new Transform3d(SOUTH_CAMERA_TRANSLATION, SOUTH_CAMERA_ROTATION);
        public static final Transform3d LEFT_CAMERA_TRANSFORM =
            new Transform3d(LEFT_CAMERA_TRANSLATION, LEFT_CAMERA_ROTATION);
        public static final Transform3d RIGHT_CAMERA_TRANSFORM =
            new Transform3d(RIGHT_CAMERA_TRANSLATION, RIGHT_CAMERA_ROTATION);

        private Vision() {}
    }

    public static final class Kicker {
        public static final int FAT_KICKER_INNER_MOTOR_PORT = 37;
        public static final int FAT_KICKER_OUTER_MOTOR_PORT = 36;
        public static final int KICKER_SPEED = 3000;

        private Kicker() {}
    }

    public static final class LEDs{
        public static final int LEDS_PORT = 0;
        public static final int NUMBER_OF_LEDS = 16;
    }

    public static final class Shooter {
        public static final int HOOD_MOTOR_PORT = 38;
        public static final int SPITTER_MOTOR_LEAD_PORT = 31;
        public static final int SPITTER_MOTOR_FOLLOWER_PORT = 35;

        public static final class ShootConfig {
            public static final double INNER_KICKER_SPEED = -1.0; // [TODO] Tune this value
            public static final double OUTER_KICKER_SPEED = -1.0; // [TODO] Tune this value

            public static final double SHORT_SPITTER_SPEED = 4000.0; // [TODO] Tune this value (in RPM)
            public static final double MEDIUM_SPITTER_SPEED = 5000.0; // [TODO] Tune this value (in RPM)
            public static final double LONG_SPITTER_SPEED = 6000.0; // [TODO] Tune this value (in RPM)

            public static final double SNATCHER_SPEED = -1.0; // [TODO] Tune this value
            public static final double SNATCHER_SPEED_RPM = -1.0; // [TODO] Tune this value (in RPM)

            public static final int BACKSPIN_MOTOR_PORT = 34; //[TODO] Find this value
            public static final double BACKSPIN_RPM = 6000.0; // [TODO] Tune this value (in RPM)
            public static final double BACKSPIN_MOTOR_MAX_SPEED = 1.0;

            public static final double KICKER_INIT_DELAY = 0.5; // [TODO] Tune this value (in seconds)
            public static final double KICKER_MOTOR_ROTATIONS_PER_ROTATION = 3.0; // [TODO] Tune this value

            public static final double LONG_SHOOT_RPM = 15000.0;
            public static final double MEDIUM_SHOOT_RPM = 9000.0;
            public static final double SHORT_SHOOT_RPM = 6000.0;
            
            public static final double TURRET_VERTICAL_MULTIPLIER = 1.95;

            private ShootConfig() {}
        }

        public static final class Hood {
            public static final double MOTOR_ROTATIONS_PER_HOOD_ROTATION = 325.0 / 18.0; // [TODO] Find this value
            public static final double HOOD_ROTATIONS_PER_MOTOR_ROTATION = 18.0 / 325.0; // [TODO] Find this value

            public static final double MIN_HOOD_ANGLE_DEGREES = 0.0; // [TODO] Find this value
            public static final double MAX_HOOD_ANGLE_DEGREES = 12.5; // [TODO] Find this value

             
             

            public static final double MIN_LAUNCH_ANGLE_DEGREES = 0.0; // [TODO] Find this value
            public static final double MAX_LAUNCH_ANGLE_DEGREES =
                MIN_LAUNCH_ANGLE_DEGREES + (MAX_HOOD_ANGLE_DEGREES - MIN_HOOD_ANGLE_DEGREES); // [TODO] Find this value

            private Hood() {}
        }

        public static final class Offset {
            public static final double X = 0.0; // [TODO] Find this value (in meters)
            public static final double Y = -6.5; // [TODO] Find this value (in meters)
            public static final double Z = 0.0; // [TODO] Find this value (in meters)

            private Offset() {}
        }

        private Shooter() {}
    }

    public static final class Slider {
        public static final int SLIDER_MOTOR_PORT = 33;//
        public static final double SLIDER_RPM = 2000; // [TODO] Tune this value (in RPM)
       
    }

    public static final class Snatcher {
        public static final int SMACKDOWN_MOTOR_PORT = 32;
        public static final int SNATCHER_MOTOR_PORT = 39;
        public static final double SNATCHER_SPEED_PERCENT = 0.4;
        public static final double MIN_SMACKDOWN_ANGLE = 0;
        public static final double MAX_SMACKDOWN_ANGLE = 92;//[TODO]might need to be negative
        public static final double MOTOR_ROTATIONS_PER_EXTENDER_ROTATION = (((125.0 / 1.0) * (25.0/24.0)));//its a 1:125 intop a 24:25 gear ratio[TODO] zack said might change slightly
    }

    public class Field {
        public static String STARTING_POSITION = "CENTER"; // Type: enum("LEFT", "CENTER", "RIGHT")

    }
}

