package org.firstinspires.ftc.teamcode.BBcode.Tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = " Robot Tuner" )
public class RobotTuner extends OpMode {
    CRServo leftInserter;
    CRServo rightInserter;
    DcMotorEx[] motors = new DcMotorEx[3];
    int selectedMotor = 0;
    double requestedPower = 0;
    boolean isActive = false;

    @Override
    public void init() {
        motors[0] = hardwareMap.get(DcMotorEx.class, "launcher");
        motors[1] = hardwareMap.get(DcMotorEx.class, "transfer");
        motors[2] = hardwareMap.get(DcMotorEx.class, "intake");
        for (DcMotorEx motor: motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        leftInserter = hardwareMap.get(CRServo.class, "leftInserter");
        rightInserter = hardwareMap.get(CRServo.class, "rightInserter");
    }

    @Override
    public void loop() {
        if (gamepad1.leftBumperWasPressed()) {
            selectedMotor = Math.max(selectedMotor - 1, 0);
        }
        if (gamepad1.rightBumperWasPressed()) {
            selectedMotor = Math.min(selectedMotor + 1, 2);
        }
        if (gamepad1.dpadUpWasPressed()) {
            requestedPower = Math.min(requestedPower + 0.01, 1);
        }
        if (gamepad1.dpadDownWasPressed()) {
            requestedPower = Math.max(requestedPower - 0.01, -1);
        }
        if (gamepad1.aWasPressed()) {
            isActive = false;
        }
        if (gamepad1.yWasPressed()) {
            isActive = true;
        }
        if (gamepad1.xWasPressed()) {
            leftInserter.setPower(requestedPower);
            rightInserter.setPower(-requestedPower);
        }
        if (gamepad1.bWasPressed()) {
            leftInserter.setPower(0);
            rightInserter.setPower(0);
        }


        if (isActive) {
            motors[selectedMotor].setPower(requestedPower);
        } else {
            motors[selectedMotor].setPower(0);
        }
        telemetry.addData("Selected motor power", motors[selectedMotor].getPower());
        telemetry.update();
    }
}