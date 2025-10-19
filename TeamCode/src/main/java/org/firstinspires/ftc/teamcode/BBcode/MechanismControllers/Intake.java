package org.firstinspires.ftc.teamcode.BBcode.MechanismControllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Intake {
    OpMode _opMode;
    DcMotorEx _intake;
    public Intake (OpMode opMode)
    {
        _opMode = opMode;
        _intake = _opMode.hardwareMap.tryGet(DcMotorEx.class, "intake");
    }
    //-----------------------------------------
    //Variable Storage:
    double intaking = 0.25;
    double stopped = 0;

    //-----------------------------------------

    public void IntakingArtifacts() {
        IntakeCustom(intaking);
    }
    public void Stopped() {
        IntakeCustom(stopped);
    }
    public void IntakeCustom(double power)
    {
        if (_intake == null)
        {
            _opMode.telemetry.addLine("Transfer Motor not found!");
        } else {
            _intake.setPower(power);
        }
    }
}
