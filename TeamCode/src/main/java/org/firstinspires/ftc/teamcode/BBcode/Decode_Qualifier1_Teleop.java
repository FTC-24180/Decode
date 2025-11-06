package org.firstinspires.ftc.teamcode.BBcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Intake;
import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Launcher;
import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Stoppers;
import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Transfer;

@TeleOp (name = "AA Main TeleOp")
public class Decode_Qualifier1_Teleop extends OpMode {
    Intake intake;
    Launcher launcher;
    Stoppers stoppers;
    Transfer transfer;
    MecanumDrivetrain drivetrain;

    double manualLaunchDistance = 52;
    double distanceOffset = 0;

    enum State {
        LAUNCHING_DYNAMIC,
        LAUNCHING,
        AIMING,
        INTAKING,
        SAFE
    }
    State state = State.INTAKING;
    @Override
    public void init() {
        intake = new Intake(this);
        launcher = new Launcher(this);
        stoppers = new Stoppers(this);
        transfer = new Transfer(this);
        drivetrain = new MecanumDrivetrain(this);
    }

    @Override
    public void loop() {
        switch (state) {
            case INTAKING:
                intake.intakeArtifacts();
                launcher.setVelocity(manualLaunchDistance);
                stoppers.stop();
                transfer.stop();
                drivetrain.Drive();

                if (gamepad1.right_trigger > 0) {
                    state = State.LAUNCHING;
                }
                if (gamepad1.left_bumper) {
                    state = State.AIMING;
                }
                if (gamepad1.backWasPressed()) {
                    state = State.SAFE;
                }
                break;
            case LAUNCHING:
                intake.intakeArtifacts();
                launcher.setVelocity(manualLaunchDistance);
                if (launcher.launcher.getVelocity() / launcher.launcher.getMotorType().getTicksPerRev() > manualLaunchDistance - 2) {
                    stoppers.transfer();
                }
                transfer.transfer();
                drivetrain.Drive();

                if (gamepad1.right_trigger == 0) {
                    state = State.INTAKING;
                }
                if (gamepad1.backWasPressed()) {
                    state = State.SAFE;
                }
                break;
            case AIMING:
                intake.intakeArtifacts();
                launcher.launch(drivetrain.getDistanceFromGoal());
                stoppers.stop();
                transfer.stop();
                drivetrain.Drive();

                if (gamepad1.rightBumperWasPressed()) {
                    state = State.LAUNCHING_DYNAMIC;
                }
                if (!gamepad1.left_bumper) {
                    state = State.INTAKING;
                }
                if (gamepad1.backWasPressed()) {
                    state = State.SAFE;
                }
                break;
            case LAUNCHING_DYNAMIC:
                intake.intakeArtifacts();
                launcher.launch(drivetrain.getDistanceFromGoal() + distanceOffset);
                if (launcher.launcher.getVelocity() / launcher.launcher.getMotorType().getTicksPerRev() > launcher.calcLaunchSpeed(drivetrain.getDistanceFromGoal()) - 2) {
                    stoppers.transfer();
                }
                transfer.transfer();
                drivetrain.Drive();

                if (!gamepad1.right_bumper) {
                    state = State.AIMING;
                }
                if (gamepad1.backWasPressed()) {
                    state = State.SAFE;
                }
                break;
            case SAFE:
                intake.stop();
                launcher.stop();
                stoppers.stop();
                transfer.stop();
                drivetrain.Drive();

                if (gamepad1.backWasPressed()) {
                    state = State.INTAKING;
                }
                break;
        }

        if (gamepad2.yWasPressed()) {
            manualLaunchDistance = 49;
        }
        if (gamepad2.xWasPressed()) {
            manualLaunchDistance = 47;
        }
        if (gamepad2.aWasPressed()) {
            manualLaunchDistance = 42;
        }

        if (gamepad2.dpadUpWasPressed()) {
            distanceOffset += 1;
        }
        if (gamepad2.dpadDownWasPressed()) {
            distanceOffset -= 1;
        }
    }
}
