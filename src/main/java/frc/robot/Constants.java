package frc.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public class Constants {

    public static final class Robot {
        public static final double ROBOT_WIDTH = Units.inchesToMeters(27.0);
        public static final double ROBOT_LENGTH = Units.inchesToMeters(27.0);
    }

    public static final class Controller {
        public static final int DRIVER_PORT = 0;
        public static final int OPERATOR_PORT = 1;

        public static final double TRANSLATION_INPUT_DEADBAND = 0.10;
        public static final double ROTATION_INPUT_DEADBAND = 0.12;
    }

    public static final class Swerve {
        
    }

    public static final class Shooter {
        public static final int FAT_KICKER_INNER_MOTOR_PORT = -1;
        public static final int FAT_KICKER_OUTER_MOTOR_PORT = -1;
        public static final int SHOOTER_MOTOR_1_PORT = -1;
        public static final int SHOOTER_MOTOR_2_PORT = -1;
        public static final int HOOD_MOTOR_PORT = -1;
        public static final int BACKSPIN_MOTOR_PORT = -1;
    }

    public static final class Gyro {
        public static final int PIGEON_ID = -1;

        private Gyro() {}
    }

    public static final class Snatcher {
        public static final int SMACKDOWN_MOTOR_PORT = -1;
        public static final int SNATCHER_MOTOR_PORT = -1;
        public static final double MIN_SMACKDOWN_ANGLE = 0;
        public static final double MAX_SMACKDOWN_ANGLE = 0;
        public static final double MOTOR_ROTATIONS_PER_EXTENDER_ROTATION = 125;
    }

    public class Field {
        public static String ALLIANCE_COLOR = "BLUE"; // Type: enum("BLUE", "RED")
        public static String STARTING_POSITION = "CENTER"; // Type: enum("LEFT", "CENTER", "RIGHT")
    }
}
