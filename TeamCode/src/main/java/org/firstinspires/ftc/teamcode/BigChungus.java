package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="BigChungus", group="Linear Opmode")

public class BigChungus extends  LinearOpMode{

    static final double INCREMENT   = 0.5;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   50;     // period of each cycle

    // Define class members
    CRServo servo1;
    Servo servo2;
    double  position = 0; // Start at halfway position
    double servoPower = 0.5;

    @Override
    public void runOpMode() {

        // Connect to servo (Assume PushBot Left Hand)
        // Change the text in quotes to match any servo name on your robot.
        servo1 = hardwareMap.get(CRServo.class, "servo1");
        servo2 = hardwareMap.get(Servo.class, "servo2");

        // Wait for the start button
        telemetry.addData(">", "Press Start Now to scan Servo." );
        telemetry.update();
        waitForStart();


        // Scan servo till stop pressed.
        while(opModeIsActive()){

            if (gamepad2.a){
                servo2.setPosition(position + 1);
            }

            // slew the servo, according to the rampUp (direction) variable.
            while (gamepad2.dpad_up ) {
                servo1.setPower(servoPower + 0.5);
                // Keep stepping up until we hit the max value.
                telemetry.addData("Push Up", "%5.2f", position);
                telemetry.update();
            }

            while (gamepad2.dpad_down ){
                servo1.setPower(servoPower - 0.5);
//                // Keep stepping down until we hit the min value.
                telemetry.addData("Push Down", "%5.2f", position);
                telemetry.update();
            }


            // Display the current value
            telemetry.addData("Servo Position", "%5.2f", position);
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();

        }

        // Signal done;
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
