package org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Launcher;

public class LauncherActions {
    Launcher launcher;

    public LauncherActions(OpMode opMode) {
        launcher = new Launcher(opMode);
    }

    public class stopAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            launcher.stop();
            return false;
        }
    }

    public Action stop() {
        return new stopAction();
    }

    public class shortLaunchAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            launcher.shortLaunch();
            return false;
        }
    }
    public Action shortLaunch() {return new shortLaunchAction();}

    public class mediumLaunchAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            launcher.mediumLaunch();
            return false;
        }
    }
    public Action mediumLaunch() {return new mediumLaunchAction();}

    public class longLaunchAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            launcher.longLaunch();
            return false;
        }
    }
    public Action longLaunch() {return new longLaunchAction();}

    // Telemetry action: runs while autonomous sequence is active
    public Action telemetryAction = new Action() {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            double velocity = launcher.launcher.getVelocity();
            double ticksPerRev = launcher.launcher.getMotorType().getTicksPerRev();
            double rps = velocity / ticksPerRev;

            packet.put("Launcher Velocity", velocity);
            packet.put("Launcher RPS", rps);
            //packet.put("PID Coefficients", String.format("P: %.2f I: %.2f D: %.2f F: %.2f", pidCoeffs.p, pidCoeffs.i, pidCoeffs.d, pidCoeffs.f));

            return true;
        }
    };
//
//    public class launchAction implements Action {
//        @Override
//        public boolean run(@NonNull TelemetryPacket packet) {
//            launcher.launch();
//            return false;
//        }
//    }
//
//    public Action launch() {
//        return new launchAction();
//   }
}