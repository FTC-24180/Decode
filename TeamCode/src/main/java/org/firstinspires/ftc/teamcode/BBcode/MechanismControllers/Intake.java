package org.firstinspires.ftc.teamcode.BBcode.MechanismControllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.BBcode.TelemetryHelper;

public class Intake {
    OpMode _opMode;
    DcMotorEx _intake;
    ChristmasLight _light;
    TelemetryHelper _telemetryHelper;
    public Intake (OpMode opMode, TelemetryHelper telemetryHelper)
    {
        _opMode = opMode;
        _telemetryHelper = telemetryHelper;
        _intake = _opMode.hardwareMap.tryGet(DcMotorEx.class, "Intake");
        _telemetryHelper.initMotorTelemetry(_intake, "Intake");
        _light = new ChristmasLight(opMode);
    }
    //-------------------------------------------------------------------------
    //Variable Storage
    double IntakeSpinning = 1;
    double IntakeStopped = 0;
    double IntakePushForTransfer = 0.25;
    //-------------------------------------------------------------------------

    public void IntakeSpinning() {IntakeCustom(IntakeSpinning);}
    public void IntakeStopped() {IntakeCustom(IntakeStopped);}
    public void IntakePushForTranfering() {IntakeCustom(IntakePushForTransfer);}

    public void IntakeCustom(double power) {
        if (_intake == null) {
            _opMode.telemetry.addLine("Intake Motor not found!");
        }
        else {
            _intake.setDirection(DcMotor.Direction.FORWARD);
//            _Intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            _intake.setPower(power);
        }
    }

}
