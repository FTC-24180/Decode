package org.firstinspires.ftc.teamcode.BBcode.MechanismControllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.BBcode.TelemetryHelper;

public class OuttakeShooter {
    OpMode _opMode;
    DcMotorEx _OuttakeShooter;
    ChristmasLight _light;
    TelemetryHelper _telemetryHelper;
    public OuttakeShooter (OpMode opMode, TelemetryHelper telemetryHelper)
    {
        _opMode = opMode;
        _telemetryHelper = telemetryHelper;
        _OuttakeShooter = _opMode.hardwareMap.tryGet(DcMotorEx.class, "Outtake");
        _telemetryHelper.initMotorTelemetry( _OuttakeShooter, "Outtake");
        _light = new ChristmasLight(opMode);
    }
    //-------------------------------------------------------------------------
    //Variable Storage
    double OuttakeSpinningForShooting = 1;
    double OuttakeStopped = 0;

    public OuttakeShooter(OpMode opMode) {
    }
    //-------------------------------------------------------------------------

    public void OuttakeSpinningForShooting() {OuttakeCustom(OuttakeSpinningForShooting);}
    public void OuttakeStopped() {OuttakeCustom(OuttakeStopped);}

    public void OuttakeCustom(double power) {
        if (_OuttakeShooter == null) {
            _opMode.telemetry.addLine("Outtake Motor not found!");
        }
        else {
            _OuttakeShooter.setDirection(DcMotor.Direction.FORWARD);
            _OuttakeShooter.setPower(power);
        }
    }
}
