// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveModule.SteerRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.kicker.KickerSubsystem;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.slider.SliderSubsystem;
import frc.robot.subsystems.slider.commands.SpinSliderCommand;
import frc.robot.subsystems.snatcher.SnatcherSubsystem;
import frc.robot.subsystems.snatcher.commands.SnatchCommand;
import frc.robot.subsystems.swerve.CommandSwerveDrivetrain;
import frc.robot.subsystems.vision.VisionSubsystem;
import frc.robot.subsystems.whirligig.WhirligigSubsystem;

public class RobotContainer {
    public final SnatcherSubsystem snatcher = new SnatcherSubsystem();
    private final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
    private final VisionSubsystem vision = new VisionSubsystem(drivetrain);
    public final ShooterSubsystem shooter = new ShooterSubsystem();
    public final KickerSubsystem kicker = new KickerSubsystem();
    public final SliderSubsystem slider = new SliderSubsystem();
    public final WhirligigSubsystem whirligig = new WhirligigSubsystem();

    private final double MaxSpeed = 0.55 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // practice-safe top speed cap
    private final double MaxAngularRate = RotationsPerSecond.of(0.5).in(RadiansPerSecond); // reduced max angular velocity
    
    /* Setting up bindings for necessary control of the swerve drive platform 8 */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1)
            .withDriveRequestType(DriveRequestType.Velocity)
            .withSteerRequestType(SteerRequestType.MotionMagicExpo);
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final CommandXboxController controller_drive =
        new CommandXboxController(Constants.Controller.DRIVER_PORT);
    private final CommandXboxController controller_operator =
        new CommandXboxController(Constants.Controller.OPERATOR_PORT);
    private final SlewRateLimiter rotationLimiter =
        new SlewRateLimiter(Constants.Controller.ROTATION_SLEW_RATE_RAD_PER_SEC_SQ);


    private final SendableChooser<Command> autoChooser = new SendableChooser<>();
    public RobotContainer() {
        configureBindings();
        configurePathPlannerAutos();
    }
    private void configurePathPlannerAutos() {
        NamedCommands.registerCommand("Do Nothing", Commands.none());
    }

    private void configureBindings() {
      configureDefaultDrive();
    }
    
    private void configureDefaultDrive() {
        drivetrain.setDefaultCommand(
            drivetrain.applyRequest(() -> {
                double xInput = -1 * applyDeadband(
                    controller_drive.getLeftY(),
                    Constants.Controller.TRANSLATION_INPUT_DEADBAND
                );
                double yInput = -1 * applyDeadband(
                    controller_drive.getLeftX(),
                    Constants.Controller.TRANSLATION_INPUT_DEADBAND
                );
                double turnInput = shapeTurnInput(
                    -1 * applyDeadband(
                        controller_drive.getRightX(),
                        Constants.Controller.ROTATION_INPUT_DEADBAND
                    )
                );

                return drive.withVelocityX(xInput * MaxSpeed)
                    .withVelocityY(yInput * MaxSpeed)
                    .withRotationalRate(
                        rotationLimiter.calculate(
                            turnInput * Constants.Controller.ROTATION_MULTIPLIER * MaxAngularRate
                        )
                    );
            })
        );
    }
    private void configureDisabledBehavior() {
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );
        RobotModeTriggers.disabled().onTrue(
            drivetrain.runOnce(() -> rotationLimiter.reset(0.0)).ignoringDisable(true)
        );
    }
    private void configureDriveBindings() {
        controller_drive.leftBumper().whileTrue(new SnatchCommand(snatcher));
    }
    private void configureOperatorBindings() {
        controller_operator.a().whileTrue(new SpinSliderCommand(slider));
    }

    private double shapeTurnInput(double rawTurn) {
        double deadbanded = MathUtil.applyDeadband(rawTurn, Constants.Controller.ROTATION_INPUT_DEADBAND);
        return Math.copySign(deadbanded * deadbanded, deadbanded);
    }

    private double applyDeadband(double rawInput, double deadband) {
        return MathUtil.applyDeadband(rawInput, deadband);
    }

    private DoubleSupplier getElasticShootRpmSupplier(double defaultRpm) {
        return () -> isElasticShooterTuningEnabled() ? getElasticShooterTuningRpm() : defaultRpm;
    }

    private boolean isElasticShooterTuningEnabled() {
        return SmartDashboard.getBoolean(Constants.Dashboard.ELASTIC_SHOOTER_TUNING_ENABLED, false);
    }

    private double getElasticShooterTuningRpm() {
        return MathUtil.clamp(
            SmartDashboard.getNumber(
                Constants.Dashboard.ELASTIC_TEST_SHOOTER_RPM,
                Constants.Shooter.ShootConfig.MEDIUM_SPITTER_SPEED
            ),
            Constants.Dashboard.ELASTIC_TEST_SHOOTER_RPM_MIN,
            Constants.Dashboard.ELASTIC_TEST_SHOOTER_RPM_MAX
        );
    }

    public Command getAutonomousCommand() {
        Command selectedAuto = autoChooser.getSelected();
        return selectedAuto != null ? selectedAuto : Commands.none();
    }

}
