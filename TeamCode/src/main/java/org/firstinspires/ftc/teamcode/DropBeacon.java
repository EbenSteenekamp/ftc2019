package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="DropBeacon", group="Linear Opmode")
public class DropBeacon extends LinearOpMode {

    Servo dropBeaconServo;

    double servoAngle = 70; // The Angle (To be tested to drop the beacon)
    @Override
    public void runOpMode() throws InterruptedException {

        dropBeaconServo = hardwareMap.servo.get("DropBeaconServo");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            if(gamepad2.left_bumper){
                dropBeaconServo.setPosition(servoAngle);
                telemetry.addData("Drop the Becaon",dropBeaconServo.getPosition());
            }

        }
    }
}
