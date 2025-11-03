package org.firstinspires.ftc.teamcode.BBcode.Tuning;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp (name = "PlEASE WORKK")
public class PLEASEWORK extends OpMode {
    DcMotorEx motor;
    @Override
    public void init() {
        motor = hardwareMap.get(DcMotorEx.class, "launcher");
    }

    @Override
    public void loop() {
        motor.setPower(1);
    }
}
