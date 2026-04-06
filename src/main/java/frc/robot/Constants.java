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
}
