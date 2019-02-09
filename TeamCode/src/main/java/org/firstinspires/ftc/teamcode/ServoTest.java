package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

import static java.lang.Thread.sleep;

public class ServoTest extends OpMode implements Runnable {
    CRServo servo1;

    public ServoTest(CRServo servo1){
        this.servo1 =servo1;
    }
    @Override
    public void run() {
        if (gamepad2.dpad_right && !gamepad2.dpad_left) {

               servo1.setPower(1);

                telemetry.addData("Left Button", servo1.getPower());
                try {
                    sleep(1800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (gamepad2.dpad_left && !gamepad2.dpad_right) {


                servo1.setPower(-1);
                telemetry.addData("Right Button", servo1.getPower());
                try {
                    sleep(1800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(gamepad2.dpad_right == false && gamepad2.dpad_left == false)
            {

                servo1.setPower(0);
                telemetry.addData("Button Center", servo1.getPower());
            }
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}
