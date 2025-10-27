package org.firstinspires.ftc.teamcode.BBcode.MechanismControllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Intake {
    OpMode opMode;
    DcMotorEx intake;
    public Intake (OpMode opMode)
    {
        this.opMode = opMode;
        intake = this.opMode.hardwareMap.tryGet(DcMotorEx.class, "intake");
    }
    //-----------------------------------------
    // Constants
    final double INTAKE_VELOCITY = 24; // Motor speed while intaking. Unit RPS
    final double STOPED_VELOCITY = 0; // Motor speed while stoped. Unit RPS
    final double RPS_TO_TPS = 28;
    //-----------------------------------------
    public void intakeArtifacts() {
        setVelocity(INTAKE_VELOCITY);
    }
    public void stop() {
        setVelocity(STOPED_VELOCITY);
    }
    public void setVelocity(double rps)
    {
        if (intake == null)
        {
            opMode.telemetry.addLine("Transfer Motor not found!");
        } else {
            double tps = rps * RPS_TO_TPS;
            intake.setVelocity(tps);
        }
    }
}
