package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
@TeleOp(name="Hello", group="Linear Opmode")

public class rev extends LinearOpMode {
    CRServo servo1;

    @Override
    public void runOpMode() throws InterruptedException {
        servo1 = hardwareMap.crservo.get("servo2");
        waitForStart();
        servo1.setPower(1);
        sleep(10000);
        }
    }


