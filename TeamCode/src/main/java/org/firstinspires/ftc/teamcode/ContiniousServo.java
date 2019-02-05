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
        while (opModeIsActive()) {
            if (gamepad2.dpad_right && !gamepad2.dpad_left) {
                servo1.setPower(1);
                telemetry.addData("Left Button", servo1.getPower());
            }
            if (gamepad2.dpad_left && !gamepad2.dpad_right) {
                servo1.setPower(-1);
                telemetry.addData("Right Button", servo1.getPower());
            }
            if(gamepad2.dpad_right == false && gamepad2.dpad_left == false)
            {
                servo1.setPower(0);
                telemetry.addData("Button Center", servo1.getPower());
            }
            telemetry.update();
        }
    }
}


