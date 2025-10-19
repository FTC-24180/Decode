package org.firstinspires.ftc.teamcode.BBcode.Tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

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
        motors[0] = hardwareMap.tryGet(DcMotorEx.class, "launcher");
        motors[1] = hardwareMap.tryGet(DcMotorEx.class, "transfer");
        motors[2] = hardwareMap.tryGet(DcMotorEx.class, "intake");
        for (DcMotorEx motor: motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        motors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        leftInserter = hardwareMap.tryGet(CRServo.class, "leftInserter");
        rightInserter = hardwareMap.tryGet(CRServo.class, "rightInserter");
    }

    @Override
    public void loop() {
        if (gamepad1.leftBumperWasPressed()) {
            selectedMotor = Math.max(selectedMotor - 1, 0);
            isActive = false;
        }
        if (gamepad1.rightBumperWasPressed()) {
            selectedMotor = Math.min(selectedMotor + 1, 2);
            isActive = false;
        }
        if (gamepad1.dpadUpWasPressed()) {
            requestedPower = Math.min(requestedPower + 0.1, 1);
        }
        if (gamepad1.dpadDownWasPressed()) {
            requestedPower = Math.max(requestedPower - 0.1, 0);
        }
        if (gamepad1.aWasPressed()) {
            isActive = false;
        }
        if (gamepad1.yWasPressed()) {
            isActive = true;
        }
        if (leftInserter != null && rightInserter != null) {
            if (gamepad1.xWasPressed()) {
                leftInserter.setPower(1);
                rightInserter.setPower(-1);
            }
            if (gamepad1.bWasPressed()) {
                leftInserter.setPower(0);
                rightInserter.setPower(0);
            }
        }


        if (motors[selectedMotor] != null) {
            if (isActive) {
                motors[selectedMotor].setPower(requestedPower);
            } else {
                motors[selectedMotor].setPower(0);
            }
        } else {
            telemetry.addLine("Selected motor can not be found");
        }
        telemetry.addData("Current Motor", selectedMotor);
        telemetry.addData("Requested power", requestedPower);
        telemetry.addData("Selected motor power", motors[selectedMotor].getPower());
        telemetry.addData("Selected motor is active", isActive);
        telemetry.update();
    }
}