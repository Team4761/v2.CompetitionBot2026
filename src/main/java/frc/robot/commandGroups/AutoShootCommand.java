package frc.robot.commandGroups;

import java.util.Arrays;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.FieldConstants;
import frc.robot.subsystems.kicker.KickerSubsystem;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.shooter.commands.hood.SetHoodAngleCommand;
import frc.robot.subsystems.slider.SliderSubsystem;
import frc.robot.subsystems.swerve.CommandSwerveDrivetrain;

public class AutoShootCommand extends SequentialCommandGroup {
    private static Double[] powerBandRanges = Constants.CollectedData.powerBandRanges;
    private static Double[] anglePoints = Constants.CollectedData.anglePoints;
    private static Double[] distPoints = Constants.CollectedData.distPoints;
    private static Double[] timePoints = Constants.CollectedData.timePoints;

    private final CommandSwerveDrivetrain m_swerve;
    private final ShooterSubsystem m_shooter;
    private final SliderSubsystem m_slider;
    private final KickerSubsystem m_kicker;

    private double capturedPower;
    private double capturedAngle;

    private static double getTime(double dist) {
        if (dist <= timePoints[0]) {
            return timePoints[0];
        } else if (dist >= timePoints[timePoints.length - 1]) {
            return timePoints[timePoints.length - 1];
        }

        int index = Arrays.binarySearch(timePoints, dist);
        if (index >= 0) {
            return timePoints[index];
        } else {
            index = -index - 1; // Get the insertion point

            Double leftDist = distPoints[index - 1];
            Double rightDist = distPoints[index];

            Double leftTime = timePoints[index - 1];
            Double rightTime = timePoints[index];
            
            return leftTime + (rightTime - leftTime) * (dist - leftDist) / (rightDist - leftDist);
        }
    }

    private static double getPower(double dist) {
        if (dist <= powerBandRanges[0]) {
            return 3750;
        } else if (dist >= powerBandRanges[powerBandRanges.length - 1]) {
            return 3750 + powerBandRanges.length * 250;
        }

        int index = Arrays.binarySearch(powerBandRanges, dist);
        if (index < 0) {
            index = -index - 1; // insertion point
        }
        return 3750 + (index - 1) * 250;
    }

    private static double getLaunchAngle(double dist) {
        if (dist <= distPoints[0]) {
            return anglePoints[0];
        } else if (dist >= distPoints[distPoints.length - 1]) {
            return anglePoints[anglePoints.length - 1];
        }

        int index = Arrays.binarySearch(distPoints, dist);
        if (index >= 0) {
            return anglePoints[index];
        } else {
            index = -index - 1; // Get the insertion point

            Double leftDist = distPoints[index - 1];
            Double rightDist = distPoints[index];

            Double leftAngle = anglePoints[index - 1];
            Double rightAngle = anglePoints[index];
            
            return leftAngle + (rightAngle - leftAngle) * (dist - leftDist) / (rightDist - leftDist);
        }
    }

    private Pose2d getFuturePose(double time) {
        ChassisSpeeds speeds = this.m_swerve.getState().Speeds;
        Transform2d velocity = new Transform2d(
            speeds.vxMetersPerSecond,
            speeds.vyMetersPerSecond,
            Rotation2d.fromRadians(speeds.omegaRadiansPerSecond)
        );
        velocity = velocity.times(time);

        Pose2d futurePose = this.m_swerve.getState().Pose.plus(velocity);

        return futurePose;
    }

    private double getDistance() {
        Pose2d currentPose = this.m_swerve.getState().Pose;
        DriverStation.Alliance alliance = DriverStation.getAlliance().orElse(DriverStation.Alliance.Blue);
        double hubX = alliance == DriverStation.Alliance.Blue ? FieldConstants.Hub.HUB_BLUE_X : FieldConstants.Hub.HUB_RED_X;
        double hubY = alliance == DriverStation.Alliance.Blue ? FieldConstants.Hub.HUB_BLUE_Y : FieldConstants.Hub.HUB_RED_Y;

        double distanceToHub = Math.hypot(hubY - currentPose.getY(), hubX - currentPose.getX());

        return distanceToHub;
    }

    private double getDistance(Pose2d pose) {
        DriverStation.Alliance alliance = DriverStation.getAlliance().orElse(DriverStation.Alliance.Blue);
        double hubX = alliance == DriverStation.Alliance.Blue ? FieldConstants.Hub.HUB_BLUE_X : FieldConstants.Hub.HUB_RED_X;
        double hubY = alliance == DriverStation.Alliance.Blue ? FieldConstants.Hub.HUB_BLUE_Y : FieldConstants.Hub.HUB_RED_Y;

        double distanceToHub = Math.hypot(hubY - pose.getY(), hubX - pose.getX());

        return distanceToHub;
    }

    public AutoShootCommand(CommandSwerveDrivetrain swerve, ShooterSubsystem shooter, SliderSubsystem slider, KickerSubsystem kicker) {
        this.m_swerve = swerve;
        this.m_shooter = shooter;
        this.m_slider = slider;
        this.m_kicker = kicker;

        addCommands(
            new InstantCommand(() -> {
                Pose2d futurePoseInner = getFuturePose(getTime(getDistance()));
                double distanceToHubInner = getDistance(futurePoseInner);
                double powerInner = getPower(distanceToHubInner);
                double angleInner = getLaunchAngle(distanceToHubInner);

                this.capturedPower = powerInner;
                this.capturedAngle = angleInner;
            }),
            new FixShootWithPowerCommand(shooter, slider, kicker, () -> this.capturedPower, () -> this.capturedAngle) 
        );
    }
}
