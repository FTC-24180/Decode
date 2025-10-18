package org.firstinspires.ftc.teamcode.BBcode.MechanismActionBuilders;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Intake;
import org.firstinspires.ftc.teamcode.BBcode.TelemetryHelper;

public class IntakeActions {
    //WristClaw must be global to allow use in the action classes
    Intake _Intake;
    TelemetryHelper telemetryHelper;
    //constructor
    public IntakeActions(OpMode opMode) {
        _Intake = new Intake(opMode, telemetryHelper);
    }
    //--------------------------------------------------------------------------

    //Generates Actions
    public class IntakeSpinningAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            _Intake.IntakeSpinning();
            return false;
        }
    }
    public Action IntakeSpinning() {return new IntakeSpinningAction();}

}
