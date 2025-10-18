package org.firstinspires.ftc.teamcode.BBcode.MechanismControllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.BBcode.TelemetryHelper;

public class Transfer {
    OpMode _opMode;
    DcMotorEx _transfer;
    ChristmasLight _light;
    TelemetryHelper _telemetryHelper;
    public Transfer (OpMode opMode, TelemetryHelper telemetryHelper)
    {
        _opMode = opMode;
        _telemetryHelper = telemetryHelper;
        _transfer = _opMode.hardwareMap.tryGet(DcMotorEx.class, "Transfer");
        _telemetryHelper.initMotorTelemetry( _transfer, "Transfer");
        _light = new ChristmasLight(opMode);
    }
    //-------------------------------------------------------------------------
    //Variable Storage
    double movingArtifacts = 0.75;
    //-------------------------------------------------------------------------

    public void MovingTheArtifacts() {TransferCustom(movingArtifacts);}

    public void TransferCustom(double power) {
        if (_transfer == null) {
            _opMode.telemetry.addLine("Intake Motor not found!");
            return;
        }
        else {
            _transfer.setDirection(DcMotor.Direction.FORWARD);
            _transfer.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            _transfer.setPower(power);
        }
    }
}
