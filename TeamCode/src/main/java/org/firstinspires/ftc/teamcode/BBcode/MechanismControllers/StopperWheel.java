package org.firstinspires.ftc.teamcode.BBcode.MechanismControllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class StopperWheel {
    OpMode _opMode;
    Servo _stopperWheel;
    public StopperWheel (OpMode opMode)
    {
        _opMode = opMode;
        _stopperWheel = _opMode.hardwareMap.tryGet(Servo.class, "stopperWheel");
    }
    //-----------------------------------------
    //Variable Storage:
    //Needs Tuning
    double stopped = 0;
    double ready = 0;
    double release = 0;

    //-----------------------------------------

    public void StoppingArtifact() {
        StopperWheelCustom(stopped);}
    public void ReadyTheArtifactForShooting() {
        StopperWheelCustom(ready);}
    public void ReleaseTheArtifactForShot() {
        StopperWheelCustom(release);}

    public void StopperWheelCustom(double position)
    {
        if (_stopperWheel == null)
        {
            _opMode.telemetry.addLine("StopperWheel Servo not found!");
        } else {
            _stopperWheel.setPosition(position);
        }
    }
}
