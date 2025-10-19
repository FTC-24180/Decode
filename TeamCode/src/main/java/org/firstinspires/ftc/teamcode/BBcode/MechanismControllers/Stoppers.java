package org.firstinspires.ftc.teamcode.BBcode.MechanismControllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

public class Stoppers {
    OpMode _opMode;
    CRServo _rightInserter;
    CRServo _leftInserter;
    public Stoppers (OpMode opMode)
    {
        _opMode = opMode;
        _rightInserter = _opMode.hardwareMap.tryGet(CRServo.class, "rightInserter");
        _leftInserter = _opMode.hardwareMap.tryGet(CRServo.class, "leftInserter");
    }
    //-----------------------------------------
    //Variable Storage:
    double transferUp = 1;
    double stopped = 0;

    //-----------------------------------------

    public void tranferUpForShot() {
        rightStopperCustom(transferUp);
        leftStopperCustom(transferUp);
    }
    public void stopped() {
        rightStopperCustom(stopped);
        leftStopperCustom(stopped);
    }
    public void rightStopperCustom(double power)
    {
        if (_rightInserter == null)
        {
            _opMode.telemetry.addLine("rightInserter Servo not found!");
        } else {
            _rightInserter.setPower(power);
        }
    }
    public void leftStopperCustom(double power)
    {
        if (_leftInserter == null)
        {
            _opMode.telemetry.addLine("leftInserter Servo not found!");
        } else {
            _leftInserter.setPower(-power);
        }
    }
}
