package org.firstinspires.ftc.teamcode.BBcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Intake;
import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Launcher;
import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Stoppers;
import org.firstinspires.ftc.teamcode.BBcode.MechanismControllers.Transfer;

@TeleOp (name = "*Main TeleOp")
public class Decode_Qualifier1_Teleop extends OpMode {
    Intake intake;
    Launcher launcher;
    Stoppers stoppers;
    Transfer transfer;

    enum State {

    }
    @Override
    public void init() {
        intake = new Intake(this);
        launcher = new Launcher(this);
        stoppers = new Stoppers(this);
        transfer = new Transfer(this);
    }

    @Override
    public void loop() {

    }
}
