package org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Stoppers;

public class StoppersAction {
    Stoppers _stopper;

    public StoppersAction(OpMode opMode) {
        _stopper = new Stoppers(opMode);
    }

    public class MoveUpForLaunchAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            _stopper.tranferUpForShot();
            return false;
        }
    }
    public Action MoveArtifactForShot() {
        return new MoveUpForLaunchAction();
    }

    public class Stopped implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            _stopper.stopped();
            return false;
        }
    }
    public Action WaitingForLaunch() {
        return new Stopped();
    }
}
