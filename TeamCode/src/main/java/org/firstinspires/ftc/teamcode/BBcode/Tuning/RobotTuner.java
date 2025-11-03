package org.firstinspires.ftc.teamcode.BBcode.Tuning;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = " Robot Tuner" )
public class RobotTuner extends OpMode {
    private FtcDashboard dashboard = FtcDashboard.getInstance();
    CRServo leftInserter;
    CRServo rightInserter;
    DcMotorEx[] motors = new DcMotorEx[3];
    int selectedMotor = 0;
    double requestedVelocity = 0;
    double requestedPower = 0;
    boolean isActive = false;
    final double TPR_435 = 383.6;
    final double TPR_1620 = 103.8;
    final double TPR_6000 = 28;
    PIDFCoefficients origCoeffs;
    // Create new coefficients
    double P = 50.0;   // increase for faster recovery
    double I = 0.05;   // low; avoids long-term drift
    double D = 2.5;    // helps suppress overshoot
    double F = 13.5;   // depends on motor max velocity and voltage

    @Override
    public void init() {
        motors[0] = hardwareMap.tryGet(DcMotorEx.class, "launcher");
        motors[1] = hardwareMap.tryGet(DcMotorEx.class, "transfer");
        motors[2] = hardwareMap.tryGet(DcMotorEx.class, "intake");
        motors[0].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors[0].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        for (DcMotorEx motor: motors) {
//            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        }
        // Set ticks per revolution directly on MotorType
        motors[0].getMotorType().setTicksPerRev(TPR_6000); // launcher
        //motors[1].getMotorType().setTicksPerRev(TPR_435); // transfer
        //motors[2].getMotorType().setTicksPerRev(TPR_6000); // intake

        //motors[1].setDirection(DcMotorSimple.Direction.REVERSE);

        leftInserter = hardwareMap.tryGet(CRServo.class, "leftInserter");
        rightInserter = hardwareMap.tryGet(CRServo.class, "rightInserter");

        DcMotorEx launcher = motors[0];
        // Get the current coefficients (optional)
        origCoeffs = launcher.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);



        PIDFCoefficients customCoeffs = new PIDFCoefficients(P, I, D, F);

 //Apply them
        //launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, customCoeffs);

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
            requestedVelocity = Math.min(requestedVelocity + 1, 100);
            if (gamepad1.right_trigger > 0) {
                requestedVelocity = Math.min(requestedVelocity + 10, 100);
            }
        }
        if (gamepad1.dpadDownWasPressed()) {
            requestedVelocity = Math.max(requestedVelocity - 1, 0);
            if (gamepad1.right_trigger > 0) {
                requestedVelocity = Math.max(requestedVelocity - 10, 0);
            }
        }
        if (gamepad1.dpadRightWasPressed()) {
            requestedPower = Math.min(requestedPower + 0.01, 1);
            if (gamepad1.right_trigger > 0) {
                requestedPower = Math.min(requestedPower + 0.1, 1);
            }
        }
        if (gamepad1.dpadLeftWasPressed()) {
            requestedPower = Math.max(requestedPower - 0.01, 0);
            if (gamepad1.right_trigger > 0) {
                requestedPower = Math.max(requestedPower - 0.1, 0);
            }
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


        if (motors[selectedMotor] != null && !(gamepad1.left_trigger > 1)) {
            if (isActive) {
                motors[selectedMotor].setVelocity(requestedVelocity * motors[selectedMotor].getMotorType().getTicksPerRev());
            } else {
                motors[selectedMotor].setVelocity(0);
            }
        } else if (motors[selectedMotor] != null){
            if (isActive) {
                motors[selectedMotor].setPower(requestedPower);
            } else {
                motors[selectedMotor].setPower(0);
            }
        } else {
            telemetry.addLine("Selected motor can not be found");
        }
        double ticksPerSecond = motors[selectedMotor].getVelocity();
        double ticksPerRev = motors[selectedMotor].getMotorType().getTicksPerRev();
        double rotationsPerSecond = ticksPerSecond / ticksPerRev;


        telemetry.addData("Original Coefficients", "P: %.2f I: %.2f D: %.2f F: %.2f",
                origCoeffs.p, origCoeffs.i, origCoeffs.d, origCoeffs.f);

        telemetry.addData("Current Coefficients", "P: %.2f I: %.2f D: %.2f F: %.2f",
                P, I, D, F);
        telemetry.addData("Current Motor", selectedMotor);
        telemetry.addData("Requested velocity (RPS)", requestedVelocity);
        telemetry.addData("Selected motor velocity (RPS)", rotationsPerSecond);
        telemetry.addData("Selected motor is active", isActive);
        telemetry.addData("Encoder is active", motors[selectedMotor].getCurrentPosition() != 0);

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Launcher RPS", rotationsPerSecond);
        packet.put("Launcher ticksPerRev", ticksPerRev);
        packet.put("Launcher ticksPerSecond", ticksPerSecond);
        dashboard.sendTelemetryPacket(packet);

        telemetry.update();
    }
}