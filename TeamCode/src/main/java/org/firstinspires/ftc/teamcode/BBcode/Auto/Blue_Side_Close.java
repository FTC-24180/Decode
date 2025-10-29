package org.firstinspires.ftc.teamcode.BBcode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
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
@Disabled
@Autonomous(name = "Blue_Side_No_Spike_Marks", group = "Autonomous")
public class Blue_Side_Close extends LinearOpMode {

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
        MecanumDrive drive = new MecanumDrive(hardwareMap, BlueSidePose.init_close);

        telemetry.update();
        waitForStart();
        //----------------------------------------------------------------------------------------------

        if (isStopRequested()) return;

        //TODO ALL Action builders need position data/tuning

        Action driveToFirstLaunch = drive.actionBuilder(drive.localizer.getPose())
                .strafeToLinearHeading(new Vector2d(0,0), Math.toRadians(0))
                .build();

        Action waitForFirstLaunch = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(5)
                .build();

        Action driveToSpikeMark = drive.actionBuilder(drive.localizer.getPose())
                .strafeToLinearHeading(new Vector2d(0,0),Math.toRadians(0))
                .build();

        Action driveToIntake = drive.actionBuilder(drive.localizer.getPose())
                .lineToX(0)
                .build();

        Action driveToSecondLaunch = drive.actionBuilder(drive.localizer.getPose())
                .strafeToLinearHeading(new Vector2d(0,0), Math.toRadians(0))
                .build();

        Action waitForSecondLaunch = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(5)
                .build();

        Action driveToSpikeMarkPark = drive.actionBuilder(drive.localizer.getPose())
                .strafeToLinearHeading(new Vector2d(0,0), Math.toRadians(0))
                .build();

        //----------------------------------------------------------------------------------------------

        Actions.runBlocking(
                new SequentialAction(
                        _LauncherActions.shortLaunch(),
                        _IntakeAction.intake(),
                        driveToFirstLaunch,
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
                        driveToSpikeMarkPark
                )
        );
    }
}
