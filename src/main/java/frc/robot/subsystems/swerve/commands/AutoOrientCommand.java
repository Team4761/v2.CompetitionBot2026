package frc.robot.subsystems.swerve.commands;

import java.util.Arrays;
import java.util.Set;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.FieldConstants;
import frc.robot.Constants;
import frc.robot.Constants.Swerve.Auto;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.swerve.CommandSwerveDrivetrain;

public class AutoOrientCommand extends Command {
    private static Double[] powerBandRanges = Constants.CollectedData.powerBandRanges;
    private static Double[] anglePoints = Constants.CollectedData.anglePoints;
    private static Double[] distPoints = Constants.CollectedData.distPoints;
    private static Double[] timePoints = Constants.CollectedData.timePoints;

    private final CommandSwerveDrivetrain m_swerve;

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

    private double getOrientation(Pose2d pose) {
        DriverStation.Alliance alliance = DriverStation.getAlliance().orElse(DriverStation.Alliance.Blue);
        double hubX = alliance == DriverStation.Alliance.Blue ? FieldConstants.Hub.HUB_BLUE_X : FieldConstants.Hub.HUB_RED_X;
        double hubY = alliance == DriverStation.Alliance.Blue ? FieldConstants.Hub.HUB_BLUE_Y : FieldConstants.Hub.HUB_RED_Y;

        double angleToHub = Math.atan2(hubY - pose.getY(), hubX - pose.getX());

        return angleToHub;
    }

    public AutoOrientCommand(CommandSwerveDrivetrain swerve) {
        // Schedule this with .whileTrue() to have it constantly update the robot's orientation to face the hub, even as it moves
        this.m_swerve = swerve;
        addRequirements(swerve);
    }

    public Command getCommand() {
        return Commands.defer(() -> {
            Pose2d futurePose = getFuturePose(getTime(getDistance()));

            return new DriveCommand(this.m_swerve, 0, 0, getOrientation(futurePose));
        }, Set.of(this.m_swerve));
    }
}
