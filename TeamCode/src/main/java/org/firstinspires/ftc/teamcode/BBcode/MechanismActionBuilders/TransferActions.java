package org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Transfer;

public class TransferActions {
    Transfer _Transfer;

    public TransferActions(OpMode opMode) {
        _Transfer = new Transfer(opMode);
    }

    public class TranferingAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            _Transfer.transfer();
            return false;
        }
    }
    public Action Transfering() {
        return new TranferingAction();
    }

    public class NotTranferingAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            _Transfer.stop();
            return false;
        }
    }
    public Action NotTransfering() {
        return new NotTranferingAction();
    }
}
