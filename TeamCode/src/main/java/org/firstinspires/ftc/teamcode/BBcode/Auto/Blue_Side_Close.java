package org.firstinspires.ftc.teamcode.BBcode.Auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.RaceAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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
@Autonomous(name = "Blue_Side_Close", group = "Autonomous")
@SuppressWarnings("unused")
public class Blue_Side_Close extends LinearOpMode {

    @Override
    public void runOpMode() {
        //Initialization steps
        FtcDashboard dashboard = FtcDashboard.getInstance();
        PoseStorage.previousOpMode = OpModeType.AUTONOMOUS;

        //Creates instance of MechanismActionBuilders
        IntakeActions _IntakeAction = new IntakeActions(this);
        LauncherActions _LauncherActions = new LauncherActions(this);
        StoppersActions _StoppersActions = new StoppersActions(this);
        TransferActions _TransferActions = new TransferActions(this);

        //Initializes drive
        MecanumDrive drive = new MecanumDrive(hardwareMap, BlueSidePose.init_close);

        // Create a MultipleTelemetry object, combining the default telemetry and the dashboard telemetry
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        telemetry.update();
        waitForStart();
        //----------------------------------------------------------------------------------------------

        if (isStopRequested()) return;

        //TODO ALL Action builders need position data/tuning

        Action driveToFirstLaunch = drive.actionBuilder(new Pose2d(-61.75, -39, Math.toRadians(-90)))
                .strafeToLinearHeading(new Vector2d(-30,-15), Math.toRadians(-125))
                .build();

        Action waitForFlyWheelSpinUp = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(2)
                .build();

        Action waitForFirstLaunch = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(3.25)
                .build();

        Action driveToSpikeMark = drive.actionBuilder(new Pose2d(-30,-15,Math.toRadians(-125)))
                .strafeToLinearHeading(new Vector2d(-14,-22),Math.toRadians(-90))
                .build();

        Action driveToIntake = drive.actionBuilder(new Pose2d(-14,-22,Math.toRadians(-90)))
                .strafeToLinearHeading(new Vector2d(-14,-55),Math.toRadians(-90))
                .build();

        Action driveToSecondLaunch = drive.actionBuilder(new Pose2d(-14,-55,Math.toRadians(-90)))
                .strafeToLinearHeading(new Vector2d(-30,-15), Math.toRadians(-126))
                .build();

        Action waitForSecondLaunch = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(3)
                .build();

        Action driveToSpikeMarkPark = drive.actionBuilder(new Pose2d(-30,-15,Math.toRadians(-126)))
                .strafeToLinearHeading(new Vector2d(12,-22), Math.toRadians(-90))
                .build();

        Action driveToIntakeSecond = drive.actionBuilder(new Pose2d(12,-22, Math.toRadians(-90)))
                .strafeToLinearHeading(new Vector2d(12.5,-62),Math.toRadians(-90))
                .build();

        Action driveToThirdLaunch = drive.actionBuilder(new Pose2d(12,-62, Math.toRadians(-90)))
                .strafeTo(new Vector2d(10, -35))
                .strafeToLinearHeading(new Vector2d(-32,-15), Math.toRadians(-127))
                .build();

        Action waitForThirdLaunch = drive.actionBuilder(drive.localizer.getPose())
                .waitSeconds(3.5)
                .build();

        Action sendDataToPoseStorage = telemetryPacket -> {
            PoseStorage.alliance = PoseStorage.Alliance.BLUE;
            PoseStorage.currentPose = drive.localizer.getPose();
            PoseStorage.hasFieldCentricDrive = true;
            return false;
        };

        //----------------------------------------------------------------------------------------------

        Actions.runBlocking(
                new RaceAction( //RaceAction to run telemetry in parallel with main sequence but end when main sequence ends
                        new ParallelAction( // Telemetry actions need to run in parallel with the main sequence
                                // Add continuous telemetry actions from mechanisms first
                                _LauncherActions.telemetryAction,

                                // This MUST be the last action so that update gets called correctly
                                new Action() {
                                    @Override
                                    public boolean run(@NonNull TelemetryPacket packet) {
                                        telemetry.addData("Alliance", PoseStorage.alliance);
                                        telemetry.addData("Mode", "Red Side Close");
                                        //TODO any other "standard" telemetry
                                        telemetry.update();
                                        return true; // keep running until sequential part finishes
                                    }
                                }
                        ),
                        new SequentialAction(
                                _LauncherActions.shortLaunch(),
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
                                waitForThirdLaunch,
                                sendDataToPoseStorage
                        )
                )
        );
    }
}
