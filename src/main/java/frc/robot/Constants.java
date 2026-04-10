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
        public static final double ROBOT_HEIGHT_WITHOUT_TURRET = -1; // [TODO] Ask Zach for this value

        public static final double ROBOT_HEIGHT_WITH_TURRET = -1; // [TODO] Ask Zach for this value

        private Robot() {}
    }
    // #endregion

    //#region Controller Constants
    public static final class Controller {
        public static final int DRIVER_PORT = 0;
        public static final int OPERATOR_PORT = 1;

        public static final double TRANSLATION_INPUT_DEADBAND = 0.10; // [TODO] Tune this value
        public static final double ROTATION_INPUT_DEADBAND = 0.12; // [TODO] Tune this value
        public static final double TURRET_INPUT_DEADBAND = 0.10; // [TODO] Tune this value
        public static final double ROTATION_SLEW_RATE_RAD_PER_SEC_SQ = -1; // [TODO] Tune this value
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

        public static final double ELASTIC_TEST_SHOOTER_RPM_MIN = -1.0; // [TODO] Tune this value
        public static final double ELASTIC_TEST_SHOOTER_RPM_MAX = -1.0; // [TODO] Tune this value
        public static final double ELASTIC_TEST_HOOD_ANGLE_MIN = Shooter.Vertical.MIN_LAUNCH_ANGLE_DEGREES;
        public static final double ELASTIC_TEST_HOOD_ANGLE_MAX = Shooter.Vertical.MAX_LAUNCH_ANGLE_DEGREES;

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
        public static final int PIGEON_ID = 0; // [TODO] Set this to the correct ID

        private Gyro() {}
    }

    public static final class Vision {
        public static final String CAMERA_1_NAME = "cam1"; // [TODO] Set this to the correct camera name
        public static final String CAMERA_2_NAME = "cam2"; // [TODO] Set this to the correct camera name
        public static final String CAMERA_3_NAME = "cam3"; // [TODO] Set this to the correct camera name
        public static final String CAMERA_4_NAME = "cam4"; // [TODO] Set this to the correct camera name
        // [TODO] Set these to the correct translations and rotations for all 4 cameras.
        public static final Translation3d CAM_1_TRANSLATION =
            new Translation3d(
                Units.inchesToMeters(0),
                Units.inchesToMeters(0),
                Units.inchesToMeters(0)
            );
        
        public static final Rotation3d CAM_1_ROTATION =
            new Rotation3d(0.0, 0.0, Math.toRadians(135.0));

        public static final Transform3d CAM_1_TRANSFORM =
            new Transform3d(CAM_1_TRANSLATION, CAM_1_ROTATION);

        public static final double ANGLE_DEADBAND = 2.0; // [TODO] Tune this value

        private Vision() {}
    }

    public static final class Shooter {
        public static final double MAX_SPEED_MEASURED_MpS = 13.5; // 

        public static final int SPITTER_MOTOR_PORT = -1;
        public static final int FAT_KICKER_INNER_MOTOR_PORT = -1;
        public static final int FAT_KICKER_OUTER_MOTOR_PORT = -1;
        public static final int VERTICAL_MOTOR_PORT = 45;

        private Shooter() {}

        public static final class ShootConfig {
            public static final double SPINDEXER_SPEED = 0.8;
            public static final double INNER_KICKER_SPEED = -1.0; // [TODO] Tune this value
            public static final double OUTER_KICKER_SPEED = -1.0; // [TODO] Tune this value

            public static final double SPITTER_SPEED = 4000.0; // [TODO] Tune this value (in RPM)

            public static final double SNATCHER_SPEED = -1.0; // [TODO] Tune this value
            public static final double SNATCHER_SPEED_RPM = -1.0; // [TODO] Tune this value (in RPM)

            public static final double KICKER_INIT_DELAY = 0.5; // [TODO] Tune this value (in seconds)
            public static final double KICKER_MOTOR_ROTATIONS_PER_ROTATION = 3.0; // [TODO] Tune this value

            public static final double TURRET_VERTICAL_MULTIPLIER = 1.95;

            private ShootConfig() {}
        }

        public static final class Vertical {
            public static final double MOTOR_ROTATIONS_PER_HOOD_ROTATION = 325.0 / 18.0; // [TODO] Find this value
            public static final double HOOD_ROTATIONS_PER_MOTOR_ROTATION = 18.0 / 325.0; // [TODO] Find this value

            public static final double MIN_HOOD_ANGLE_DEGREES = -31.0; // [TODO] Find this value
            public static final double MAX_HOOD_ANGLE_DEGREES = 0.0; // [TODO] Find this value

            public static final double MIN_LAUNCH_ANGLE_DEGREES = 22.0; // [TODO] Find this value
            public static final double MAX_LAUNCH_ANGLE_DEGREES =
                MIN_LAUNCH_ANGLE_DEGREES + (MAX_HOOD_ANGLE_DEGREES - MIN_HOOD_ANGLE_DEGREES); // [TODO] Find this value

            private Vertical() {}
        }

        public static final class Offset {
            public static final double X = 0.0; // [TODO] Find this value (in meters)
            public static final double Y = -6.5; // [TODO] Find this value (in meters)

            private Offset() {}
        }
    }


    public static final class Snatcher {
        public static final int SMACKDOWN_MOTOR_PORT = -1;
        public static final int SNATCHER_MOTOR_PORT = -1;
        public static final double MIN_SMACKDOWN_ANGLE = 0;
        public static final double MAX_SMACKDOWN_ANGLE = 0;
        public static final double MOTOR_ROTATIONS_PER_EXTENDER_ROTATION = 125;
    }

    public class Field {
        public static String STARTING_POSITION = "CENTER"; // Type: enum("LEFT", "CENTER", "RIGHT")

    }
}

