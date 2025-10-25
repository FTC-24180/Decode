package org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Intake;

public class IntakeActions {
    Intake intake;

    public IntakeActions(OpMode opMode) {
        intake = new Intake(opMode);
    }

    public class stopAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            intake.stop();
            return false;
        }
    }
    public Action stop() {
        return new stopAction();
    }

    public class intakeAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            intake.intakeArtifacts();
            return false;
        }
    }
    public Action intake() {
        return new intakeAction();
    }
}
