package org.firstinspires.ftc.teamcode.BBcode.MechanismControllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class WristClaw {
    OpMode _opMode;
    Servo _wrist;
    Servo _claw;
    ChristmasLight _light;
    public WristClaw (OpMode opMode)
    {
        _opMode = opMode;
        _wrist = _opMode.hardwareMap.tryGet(Servo.class, "wrist");
        _claw = _opMode.hardwareMap.tryGet(Servo.class, "claw");
        _light = new ChristmasLight(opMode);
    }
    //-----------------------------------------
    //Variable Storage:
    double openPosition = 0.73;
    double closePosition = 0.39;
    double clampPosition = 0.36;
    double upPosition = 0.31;
    double midPosition = 0.47;
    double clipPosition = 0.47;
    double specimenPickupPosition = 0.7;
    double downPosition = 0.8;
    double dumpPosition = 0.36;
    double autodumpPosition = 0.26;
    double wristInit = 0.755;
    //-----------------------------------------

    public void OpenClaw() {
        ClawCustom(openPosition);
        _light.blue();
    }
    public void CloseClaw() {
        ClawCustom(closePosition);
        _light.green();}
    public void ClampClaw() {ClawCustom(clampPosition);}
    public void WristUp() {WristCustom(upPosition);}
    public void WristMid() {WristCustom(midPosition);}
    public void WristClip() {WristCustom(clipPosition);}
    public void WristSpecimenPickup() {WristCustom(specimenPickupPosition);}
    public void WristDown() {WristCustom(downPosition);}
    public void WristDump() {WristCustom(dumpPosition);}
    public void WristAutoDump() {WristCustom(autodumpPosition);}
    public void WristInit() {WristCustom(wristInit);}
    public void WristCustom(double position)
    {
        if (_wrist == null)
        {
            _opMode.telemetry.addLine("Wrist servo not found!");
        } else {
            _wrist.setPosition(position);
        }

    }
    public void ClawCustom(double position)
    {
        if (_claw == null)
        {
            _opMode.telemetry.addLine("Claw Servo not found!");
        } else {
            _claw.setPosition(position);
        }
    }
}
