package org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Stoppers;

public class StoppersActions {
    Stoppers stopper;

    public StoppersActions(OpMode opMode) {
        stopper = new Stoppers(opMode);
    }

    public class transferAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            stopper.transfer();
            return false;
        }
    }
    public Action transfer() {
        return new transferAction();
    }

    public class stopAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            stopper.stop();
            return false;
        }
    }
    public Action stop() {
        return new stopAction();
    }
}
