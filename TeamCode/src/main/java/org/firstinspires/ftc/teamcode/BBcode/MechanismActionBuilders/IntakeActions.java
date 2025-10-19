package org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Intake;

public class IntakeActions {
    Intake _Intake;

    public IntakeActions(OpMode opMode) {
        _Intake = new Intake(opMode);
    }

    public class StoppedAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            _Intake.Stopped();
            return false;
        }
    }
    public Action IntakeStopped() {
        return new StoppedAction();
    }

    public class SpinningAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            _Intake.IntakingArtifacts();
            return false;
        }
    }
    public Action IntakeSpinning() {
        return new SpinningAction();
    }
}
