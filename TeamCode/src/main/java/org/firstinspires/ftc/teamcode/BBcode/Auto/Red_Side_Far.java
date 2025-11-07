package org.firstinspires.ftc.teamcode.BBcode.Auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.bluebananas.ftc.roadrunneractions.TrajectoryActionBuilders.RedSidePose;
import org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders.IntakeActions;
import org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders.LauncherActions;
import org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders.StoppersActions;
import org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders.TransferActions;
import org.firstinspires.ftc.teamcode.BBcode.OpModeType;
import org.firstinspires.ftc.teamcode.BBcode.PoseStorage;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
//@Disabled
@Autonomous(name = "Red_Side_Far", group = "Autonomous")
public class Red_Side_Far extends LinearOpMode {

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
        MecanumDrive drive = new MecanumDrive(hardwareMap, RedSidePose.init_far);

        telemetry.update();
        waitForStart();
        //----------------------------------------------------------------------------------------------

        if (isStopRequested()) return;

        //TODO ALL Action builders need position data/tuning

        Action driveToFirstLaunch = drive.actionBuilder(new Pose2d(63.75,16, Math.toRadians(180)))
                .strafeToLinearHeading(new Vector2d(50,15), Math.toRadians(158))
                .build();

        Action waitForFlyWheelSpinUp = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(2.25)
                .build();

        Action waitForFirstLaunch = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(3)
                .build();

        Action driveToSpikeMark = drive.actionBuilder(new Pose2d(50,15,Math.toRadians(158)))
                .strafeToLinearHeading(new Vector2d(36,32),Math.toRadians(90))
                .build();

        Action driveToIntake = drive.actionBuilder(new Pose2d(36,32,Math.toRadians(90)))
                .lineToY(74)
                .build();

        Action driveToSecondLaunch = drive.actionBuilder(new Pose2d(36,74,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(50,15), Math.toRadians(158))
                .build();

        Action waitForSecondLaunch = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(3)
                .build();

        Action driveToSpikeMarkPark = drive.actionBuilder(new Pose2d(50,15, Math.toRadians(158)))
                .strafeToLinearHeading(new Vector2d(13.5,30), Math.toRadians(90))
                .build();

        Action driveToIntakeSecond = drive.actionBuilder(new Pose2d(13.5,30, Math.toRadians(90)))
                .lineToY(74)
                .build();

        Action driveToThirdLaunch = drive.actionBuilder(new Pose2d(13.5,74, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(10,25), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(-20,9), Math.toRadians(135))
                .build();

        Action waitForThirdLaunch = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(3)
                .build();

        Action sendDataToPoseStorage = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                PoseStorage.alliance = PoseStorage.Alliance.RED;
                PoseStorage.currentPose = drive.localizer.getPose();
                PoseStorage.hasFieldCentricDrive = true;
                return false;
            }
        };

        //----------------------------------------------------------------------------------------------

        Actions.runBlocking(
                new ParallelAction(
                        _LauncherActions.telemetryAction,
                        new SequentialAction(
                                _LauncherActions.longLaunch(),
                                _IntakeAction.intake(),
                                driveToFirstLaunch,
                                waitForFlyWheelSpinUp,
                                _StoppersActions.transfer(),
                                _TransferActions.Transfering(),
                                waitForFirstLaunch,
                                _StoppersActions.stop(),
                                _TransferActions.NotTransfering(),
                                driveToSpikeMark,
                                driveToIntake,
                                _LauncherActions.mediumLaunch(),
                                driveToSecondLaunch,
                                _StoppersActions.transfer(),
                                _TransferActions.Transfering(),
                                waitForSecondLaunch,
                                _StoppersActions.stop(),
                                _TransferActions.NotTransfering(),
                                _LauncherActions.mediumLaunch(),
                                driveToSpikeMarkPark,
                                driveToIntakeSecond,
                                driveToThirdLaunch,
                                _StoppersActions.transfer(),
                                _TransferActions.Transfering(),
                                waitForThirdLaunch,
                                sendDataToPoseStorage
                        )
                )
        );
    }
}
