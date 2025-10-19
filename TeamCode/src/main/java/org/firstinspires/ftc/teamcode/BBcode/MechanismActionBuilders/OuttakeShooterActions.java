package org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.OuttakeShooter;

public class OuttakeShooterActions {
    OuttakeShooter _OuttakeShooter;

    public OuttakeShooterActions(OpMode opMode) {
        _OuttakeShooter = new OuttakeShooter(opMode);
    }

    public class StoppedAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            _OuttakeShooter.Stopped();
            return false;
        }
    }
    public Action FlyWheelStopped() {
        return new StoppedAction();
    }

    public class ShortLaunchAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            _OuttakeShooter.ShortShot();
            return false;
        }
    }
    public Action ShortLaunch() {
        return new ShortLaunchAction();
    }

    public class LongLaunchAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            _OuttakeShooter.Stopped();
            return false;
        }
    }
    public Action LongShot() {
        return new LongLaunchAction();
    }
}
