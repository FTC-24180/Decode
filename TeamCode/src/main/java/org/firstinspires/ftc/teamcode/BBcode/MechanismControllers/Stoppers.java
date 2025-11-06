package org.firstinspires.ftc.teamcode.BBcode.MechanismControllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

public class Stoppers {
    OpMode opMode;
    CRServo rightInserter;
    CRServo leftInserter;
    public Stoppers (OpMode opMode)
    {
        this.opMode = opMode;
        rightInserter = this.opMode.hardwareMap.tryGet(CRServo.class, "rightInserter");
        leftInserter = this.opMode.hardwareMap.tryGet(CRServo.class, "leftInserter");
    }
    //-----------------------------------------
    // Constants
    final double TRANSFER_POWER = 0.385;
    final double STOPED_POWER = 0;

    //-----------------------------------------

    public void transfer() {
        setPower(TRANSFER_POWER);
    }
    public void stop() {
        setPower(STOPED_POWER);
    }
    public void setPower(double power)
    {
        if (rightInserter == null)
        {
            opMode.telemetry.addLine("rightInserter Servo not found!");
        } else if (leftInserter == null) {
            opMode.telemetry.addLine("leftInserter Servo not found!");
        } else {
            rightInserter.setPower(-power);
            leftInserter.setPower(power);
        }
    }
}
