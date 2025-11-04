package org.firstinspires.ftc.teamcode.BBcode.MechanismControllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Launcher {
    OpMode opMode;
    final double TPR_6000 = 28;
    final public DcMotorEx launcher;
    public final PIDFCoefficients customCoeffs = new PIDFCoefficients(50.0, 0.05, 2.5, 13.5);
    public Launcher(OpMode opMode)
    {
        this.opMode = opMode;
        launcher = this.opMode.hardwareMap.tryGet(DcMotorEx.class, "launcher");
        launcher.getMotorType().setTicksPerRev(TPR_6000);
        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, customCoeffs);
    }
    //-----------------------------------------
    // Constants
    final double IDLE_VELOCITY = 30;
    final double STOPPED_VELOCITY = 0;
    final double Short_Launch_Velocity = 63;
    final double Medium_Launch_Velocity = 63;
    final double Long_Launch_Velocity = 72;
    final double RPS_TO_TPS = 28;




    //-----------------------------------------

    public void launch(double distanceFromGoal) {
        double rps = (0.000027 * Math.pow(distanceFromGoal, 3)) + (-0.00827 * Math.pow(distanceFromGoal, 2)) + (0.9043 * distanceFromGoal) + 23.35;
        setVelocity(rps);
    }
    public void shortLaunch() {setVelocity(Short_Launch_Velocity);}
    public void mediumLaunch() {setVelocity(Medium_Launch_Velocity);}
    public void longLaunch() {setVelocity(Long_Launch_Velocity);}
    public void idle() {
        setVelocity(IDLE_VELOCITY);
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
