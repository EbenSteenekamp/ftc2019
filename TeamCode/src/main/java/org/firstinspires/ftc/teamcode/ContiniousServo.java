package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
@TeleOp(name="ContiniousServo", group="Linear Opmode")

public class ContiniousServo extends LinearOpMode {
    CRServo servo1;

    @Override
    public void runOpMode() throws InterruptedException {
        servo1 = hardwareMap.crservo.get("servo1");
        waitForStart();
        servo1.setPower(1);
        sleep(10000);


        }
    }


