package org.firstinspires.ftc.teamcode.BBcode;


import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.bluebananas.ftc.roadrunneractions.TrajectoryActionBuilders.RedBasketPose;
import org.firstinspires.ftc.teamcode.Localizer;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PinpointLocalizer;

import java.util.Locale;

import java.util.Timer;

public class MecanumDrivetrain {
    OpMode _opMode;
    TelemetryHelper _telemetryHelper;
    DcMotorEx _leftFront;
    DcMotorEx _leftBack;
    DcMotorEx _rightFront;
    DcMotorEx _rightBack;

    private static Pose2d previousPose = new Pose2d(0, 0, 0);
    //TODO drop and target pose needs to be set based on start location red vs blue
    private static final Pose2d dropPose = RedBasketPose.drop;
    private static final Pose2d basketDropTargetPose = new Pose2d(dropPose.position.x+1.5, dropPose.position.y+1.5, dropPose.heading.toDouble());
    private static final Pose2d specimenGrabTargetPose = new Pose2d(38,-60, Math.toRadians(0));
    private static Pose2d specimenClipTargetPose = new Pose2d(0, -36, Math.toRadians(-90));
    // TODO adjust proportional control gains for tele-auto
    private static final double kpTranslation = 0.07;
    private static final double kdTranslation = 0.01;
    private static final double kpRotation = .7;
    private static final double kdRotation = 0.1;
    private static final double angleToleranceDeg = 1;
    private static final double distanceToleranceInch = .25;
    private boolean isDpad_LeftPressed = false;
    private boolean isDpad_RightPressed = false;
    public final Localizer localizer;
    ElapsedTime derivativeTimer;

    double lastHeadingError;
    double lastRobotRelativeX;
    double lastRobotRelativeY;

    // Constructor
    public MecanumDrivetrain(OpMode opMode) {
        _opMode = opMode;
        _telemetryHelper = new TelemetryHelper(opMode);
        localizer = new PinpointLocalizer(_opMode.hardwareMap, MecanumDrive.PARAMS.inPerTick, previousPose);
        // Initialize the motors
        _leftFront = _opMode.hardwareMap.tryGet(DcMotorEx.class, "leftFront");
        _leftBack = _opMode.hardwareMap.tryGet(DcMotorEx.class, "leftBack");
        _rightFront = _opMode.hardwareMap.tryGet(DcMotorEx.class, "rightFront");
        _rightBack = _opMode.hardwareMap.tryGet(DcMotorEx.class, "rightBack");

        double[] motorPowers = new double[]{0, 0, 0, 0};
        //For right now, just add a telemetry message but the code will still fail when it's accessed in code so gracefully handle the null case
        //This could be to exit the OpMode or to continue with the OpMode but not use the device. The latter requires checking for null in the code
        if (_leftFront == null || _leftBack == null || _rightFront == null || _rightBack == null)
        {
            _opMode.telemetry.addLine("One or more motors not found!");
        } else {
            // Reverse the right side motors
            _rightFront.setDirection(DcMotor.Direction.REVERSE);
            _rightBack.setDirection(DcMotor.Direction.REVERSE);
        }
        //init derivative timer
        derivativeTimer = new ElapsedTime();

        _telemetryHelper.initMotorTelemetry( _leftFront, "LF");
        _telemetryHelper.initMotorTelemetry( _leftBack, "LB");
        _telemetryHelper.initMotorTelemetry( _rightFront, "RF");
        _telemetryHelper.initMotorTelemetry( _rightBack, "RB");
        opMode.telemetry.addData("Localizer Position", () -> String.format(Locale.US, "{X: %.2f, Y: %.2f, H: %.2f}", localizer.getPose().position.x, localizer.getPose().position.y, Math.toDegrees(localizer.getPose().heading.toDouble())));
    }

    public void Drive() {
        double drive;
        double turn;
        double strafe;
        double fLeftPow, fRightPow, bLeftPow, bRightPow;
        //TODO Adjust teleop speed multipliers
        double speedMultiplier = 0.75;
        double turnSpeedMultiplier = 0.5;
        double turnEasingExponent = 3, turnEasingYIntercept = 0.05;

        localizer.update();
        Gamepad gamepad1 = _opMode.gamepad1;
        previousPose = localizer.getPose();
        Pose2d targetPose = null;
        if (PoseStorage.hasFieldCentricDrive) {

            if (gamepad1.left_bumper) {
                targetPose = basketDropTargetPose;
            }
            if(gamepad1.right_bumper) {
                targetPose = specimenGrabTargetPose;
            }
            if(gamepad1.right_trigger > 0) {
                targetPose = specimenClipTargetPose;
            }
            if(gamepad1.dpad_left) {
                if (!isDpad_LeftPressed) {
                    isDpad_LeftPressed = true;
                    double newX = specimenClipTargetPose.position.x;
                    newX -= 1;
                    specimenClipTargetPose = new Pose2d(newX, specimenClipTargetPose.position.y, specimenClipTargetPose.heading.toDouble());
                }
            }
                else {
                isDpad_LeftPressed = false;
            }
            if(gamepad1.dpad_right) {
                if (!isDpad_RightPressed) {
                    isDpad_RightPressed = true;
                    double newX = specimenClipTargetPose.position.x;
                    newX += 1;
                    specimenClipTargetPose = new Pose2d(newX, specimenClipTargetPose.position.y, specimenClipTargetPose.heading.toDouble());
                }
            }
            else {
                isDpad_RightPressed = false;
            }
        }
        if (targetPose == null){
            //manual teleop drive
            drive = gamepad1.left_stick_y;
            turn = turnSpeedMultiplier * (Math.pow((gamepad1.right_stick_x * -1), turnEasingExponent) + (Math.signum(gamepad1.right_stick_x * -1) * turnEasingYIntercept));
            strafe = gamepad1.left_stick_x * -1;
            if (gamepad1.left_trigger > 0) {
                speedMultiplier = 0.25;
            }
            fLeftPow = Range.clip((drive + turn + strafe) * speedMultiplier, -1, 1);
            bLeftPow = Range.clip((drive + turn - strafe) * speedMultiplier, -1, 1);
            fRightPow = Range.clip((drive - turn - strafe) * speedMultiplier, -1, 1);
            bRightPow = Range.clip((drive - turn + strafe) * speedMultiplier, -1, 1);

            if (_leftFront != null) {
                _leftFront.setPower(fLeftPow);
            }
            if (_leftBack != null) {
                _leftBack.setPower(bLeftPow);
            }
            if (_rightFront != null) {
                _rightFront.setPower(fRightPow);
            }
            if (_rightBack != null) {
                _rightBack.setPower(bRightPow);
            }
        } else { //automatic teleop drive
            double[] motorPowers = calMotorPowers(previousPose, targetPose);
            setMotorPowers(motorPowers);
        }
    }
    /**
     * Move robot according to desired axes motions
     * <p>
     * Positive X is forward
     * <p>
     * Positive Y is strafe left
     * <p>
     * Positive Yaw is counter-clockwise
     */

    private void setMotorPowers(double[] powers) {


        // Send powers to the wheels.
        _leftFront.setPower(powers[0]);
        _rightFront.setPower(powers[1]);
        _leftBack.setPower(powers[2]);
        _rightBack.setPower(powers[3]);
    }
    private double[] calMotorPowers(Pose2d currentPose, Pose2d targetPose) {
        //get error from pinpoint stuff
        double errorX = targetPose.position.x - currentPose.position.x;
        double errorY = targetPose.position.y - currentPose.position.y;
        double distanceError = Math.hypot(errorX, errorY);
        double maxDrive = 1;
        /*
         * For a mecanum drive, we want to translate the field-centric error vector into
         * robot–centric coordinates. To do this, rotate the error vector by the negative
         * of the robot’s current heading.
         */
        double robotHeading = currentPose.heading.toDouble();
        double robotRelativeX = errorX * Math.cos(robotHeading) + errorY * Math.sin(robotHeading);
        double robotRelativeY = -errorX * Math.sin(robotHeading) + errorY * Math.cos(robotHeading);
        robotRelativeY = -robotRelativeY;
        double errorYaw = targetPose.heading.toDouble() - robotHeading;
        //calc derivative
        double turnDerivative = (errorYaw - lastHeadingError) / derivativeTimer.seconds();
        lastHeadingError = errorYaw;
        double driveDerivative = (robotRelativeX - lastRobotRelativeX) / derivativeTimer.seconds();
        lastRobotRelativeX = robotRelativeX;
        double strafeDerivative = (robotRelativeY - lastRobotRelativeY) / derivativeTimer.seconds();
        lastRobotRelativeY = robotRelativeY;
        derivativeTimer.reset();

        if (distanceError < distanceToleranceInch && Math.abs(Math.toDegrees(errorYaw)) < angleToleranceDeg) {
            return new double[]{0, 0, 0, 0};
        } else {

            double drive = (driveDerivative * kdTranslation) + (robotRelativeX * kpTranslation);
            double strafe = (strafeDerivative * kdTranslation) + (robotRelativeY * kpTranslation);
            double turn = (turnDerivative * kdRotation) + (errorYaw * kpRotation);

            // Calculate individual motor powers for mecanum drive
            double leftFrontPower = strafe + drive - turn;
            double rightFrontPower = strafe - drive - turn;
            double leftBackPower = strafe - drive + turn;
            double rightBackPower = strafe + drive + turn;

            leftFrontPower = -leftFrontPower;
            rightBackPower = -rightBackPower;
            // Normalize wheel powers to be less than 1.0
            double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > maxDrive) {
                leftFrontPower = maxDrive * leftFrontPower / max;
                rightFrontPower = maxDrive * rightFrontPower / max;
                leftBackPower = maxDrive * leftBackPower / max;
                rightBackPower = maxDrive * rightBackPower / max;
            }


            // Return the motor powers.
            return new double[]{leftFrontPower, rightFrontPower, leftBackPower, rightBackPower};
        }
    }
}
