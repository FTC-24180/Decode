package org.firstinspires.ftc.teamcode.BBcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
        waitForStart();
        //----------------------------------------------------------------------------------------------

        if (isStopRequested()) return;

        //TODO ALL Action builders need position data/tuning

        Action driveToFarLaunch = drive.actionBuilder(drive.localizer.getPose())
                .strafeToLinearHeading(new Vector2d(59,-15), Math.toRadians(-160))
                .build();

        Action waitForFarLaunch = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(5)
                .build();

        Action driveToSpikeMark = drive.actionBuilder(new Pose2d(59,-15,Math.toRadians(-160)))
                .strafeToLinearHeading(new Vector2d(36,-30),Math.toRadians(-90))
                .build();

        Action driveToIntake = drive.actionBuilder(new Pose2d(36,-30,Math.toRadians(-90)))
                .lineToY(-60)
                .build();

        Action driveToMiddleLaunch = drive.actionBuilder(new Pose2d(36,-60,Math.toRadians(-90)))
                .strafeToLinearHeading(new Vector2d(-12,-12), Math.toRadians(-135))
                .build();

        Action waitForMiddleLaunch = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(5)
                .build();

        Action driveToSpikeMarkPark = drive.actionBuilder(new Pose2d(-12,-12, Math.toRadians(-135)))
                .strafeToLinearHeading(new Vector2d(12,-30), Math.toRadians(-90))
                .build();

        //----------------------------------------------------------------------------------------------

        Actions.runBlocking(
                new SequentialAction(
                        _LauncherActions.longLaunch(),
                        _IntakeAction.intake(),
                        driveToFarLaunch,
                        _StoppersActions.transfer(),
                        _TransferActions.Transfering(),
                        waitForFarLaunch,
                        _StoppersActions.stop(),
                        _TransferActions.NotTransfering(),
                        driveToSpikeMark,
                        driveToIntake,
                        _LauncherActions.mediumLaunch(),
                        driveToMiddleLaunch,
                        _StoppersActions.transfer(),
                        _TransferActions.Transfering(),
                        waitForMiddleLaunch,
                        driveToSpikeMarkPark
                )
        );
    }
}
