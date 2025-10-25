package org.firstinspires.ftc.teamcode.BBcode.MechanismControllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Launcher {
    OpMode opMode;
    DcMotorEx launcher;
    public Launcher(OpMode opMode)
    {
        this.opMode = opMode;
        launcher = this.opMode.hardwareMap.tryGet(DcMotorEx.class, "launcher");
    }
    //-----------------------------------------
    // Constants
    final double STOPPED_VELOCITY = 0;

    final double RPS_TO_TPS = 28;

    //-----------------------------------------

    public void launch() {
        double rps = 45; // TODO: This value should be calculated based on the range to the goal.
        setVelocity(rps);
    }
    public void stop() {
        setVelocity(STOPPED_VELOCITY);
    }
    public void setVelocity(double rps)
    {
        if (launcher == null)
        {
            opMode.telemetry.addLine("Transfer Motor not found!");
        } else {
            double tps = rps * RPS_TO_TPS;
            launcher.setVelocity(tps);
        }
    }
}
