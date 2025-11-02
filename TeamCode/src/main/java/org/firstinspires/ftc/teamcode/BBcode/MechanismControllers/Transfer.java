package org.firstinspires.ftc.teamcode.BBcode.MechanismControllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Transfer {
    OpMode opMode;
    DcMotorEx transfer;
    public Transfer (OpMode opMode)
    {
        this.opMode = opMode;
        transfer = this.opMode.hardwareMap.tryGet(DcMotorEx.class, "transfer");
    }
    //-----------------------------------------
    // Constants
    final double TRANSFER_VELOCITY = 8;
    final double STOPPED_VELOCITY = 0;
    final double RPS_TO_TPS = 384.5;

    //-----------------------------------------

    public void transfer() {
        setVelocity(TRANSFER_VELOCITY);
    }
    public void stop() {
        setVelocity(STOPPED_VELOCITY);
    }
    public void setVelocity(double rps)
    {
        if (transfer == null)
        {
            opMode.telemetry.addLine("Transfer Motor not found!");
        } else {
            double tps = rps * RPS_TO_TPS;
            transfer.setDirection(DcMotorSimple.Direction.REVERSE);
            transfer.setVelocity(tps);
        }
    }
}
