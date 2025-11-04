package org.firstinspires.ftc.teamcode.BBcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.bluebananas.ftc.roadrunneractions.TrajectoryActionBuilders.BlueSidePose;
import org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders.IntakeActions;
import org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders.LauncherActions;
import org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders.StoppersActions;
import org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders.TransferActions;
import org.firstinspires.ftc.teamcode.BBcode.OpModeType;
import org.firstinspires.ftc.teamcode.BBcode.PoseStorage;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
//@Disabled
@Autonomous(name = "Blue_Side_Far", group = "Autonomous")
public class Blue_Side_Far extends LinearOpMode {

    @Override
    public void runOpMode() {
        //Initialization steps
        PoseStorage.previousOpMode = OpModeType.AUTONOMOUS;

        //Creates instance of MechanismActionBuilders
        IntakeActions _IntakeAction = new IntakeActions(this);
        LauncherActions _LauncherActions = new LauncherActions(this);
        StoppersActions _StoppersActions = new StoppersActions(this);
        TransferActions _TransferActions = new TransferActions(this);

        //Initializes drive
        MecanumDrive drive = new MecanumDrive(hardwareMap, BlueSidePose.init_far);

        telemetry.update();
        FtcDashboard dashboard = FtcDashboard.getInstance();
        DcMotorEx launcherMotor = hardwareMap.get(DcMotorEx.class, "launcher");
        PIDFCoefficients pidCoeffs = launcherMotor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        //----------------------------------------------------------------------------------------------

        if (isStopRequested()) return;

        //TODO ALL Action builders need position data/tuning

        Action driveToFirstLaunch = drive.actionBuilder(new Pose2d(-16,56.5, Math.toRadians(180)))
                .strafeTo(new Vector2d(-20, -14))
                .strafeToLinearHeading(new Vector2d(-32,-20), Math.toRadians(-127))
                .build();

        Action waitForFlyWheelSpinUp = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(0.85)
                .build();

        Action waitForFarLaunch = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(2.5)
                .build();

        Action driveToSpikeMark = drive.actionBuilder(new Pose2d(-32,-20,Math.toRadians(-127)))
                .strafeToLinearHeading(new Vector2d(36,-22),Math.toRadians(-90))
                .build();

        Action driveToIntake = drive.actionBuilder(new Pose2d(36,-22,Math.toRadians(-90)))
                .lineToY(-74)
                .build();

        Action driveToSecondLaunch = drive.actionBuilder(new Pose2d(36,-74,Math.toRadians(-90)))
                .strafeTo(new Vector2d(25, -35))
                .strafeToLinearHeading(new Vector2d(-32,-20), Math.toRadians(-127))
                .build();

        Action waitForSecondLaunch = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(2.5)
                .build();

        Action driveToSpikeMarkPark = drive.actionBuilder(new Pose2d(-32,-20, Math.toRadians(-127)))
                .strafeToLinearHeading(new Vector2d(13.5,-22), Math.toRadians(-90))
                .build();

        Action driveToIntakeSecond = drive.actionBuilder(new Pose2d(13.5,-22, Math.toRadians(-90)))
                .lineToY(-75)
                .build();

        Action driveToThirdLaunch = drive.actionBuilder(new Pose2d(13.5,-75, Math.toRadians(-90)))
                .strafeTo(new Vector2d(14, -35))
                .strafeToLinearHeading(new Vector2d(-32,-20), Math.toRadians(-127))
                .build();

        Action waitForThirdLaunch = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(3)
                .build();

        //----------------------------------------------------------------------------------------------

        Actions.runBlocking(
                new ParallelAction(
                        _LauncherActions.telemetryAction,
                        new SequentialAction(
                                _LauncherActions.mediumLaunch(),
                                _IntakeAction.intake(),
                                driveToFirstLaunch,
                                waitForFlyWheelSpinUp,
                                _StoppersActions.transfer(),
                                _TransferActions.Transfering(),
                                waitForFarLaunch,
                                _StoppersActions.stop(),
                                _TransferActions.NotTransfering(),
                                driveToSpikeMark,
                                driveToIntake,
                                driveToSecondLaunch,
                                _StoppersActions.transfer(),
                                _TransferActions.Transfering(),
                                waitForSecondLaunch,
                                _StoppersActions.stop(),
                                _TransferActions.NotTransfering(),
                                driveToSpikeMarkPark,
                                driveToIntakeSecond,
                                driveToThirdLaunch,
                                _StoppersActions.transfer(),
                                _TransferActions.Transfering(),
                                waitForThirdLaunch

                        )
                )

        );
    }
}
