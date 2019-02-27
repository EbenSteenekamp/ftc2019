package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="DropBeacon", group="Linear Opmode")
public class DropBeacon extends LinearOpMode {

    Servo dropBeaconServo;

    //double servoAngle = 0.12; // The Angle (To be tested to drop the beacon)
    @Override
    public void runOpMode() throws InterruptedException {

        dropBeaconServo = hardwareMap.servo.get("DropBeaconServo");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //dropBeaconServo.setPosition(0);
        telemetry.addData("Reset the Becaon",dropBeaconServo.getPosition());
        waitForStart();

        dropBeaconServo.setDirection(Servo.Direction.REVERSE);
            dropBeaconServo.setPosition(0.5);

            while (opModeIsActive()) {

             if(gamepad2.left_bumper == true){
                    dropBeaconServo.setPosition(0.90);
                    telemetry.addData("Drop the Beacon",dropBeaconServo.getPosition());
                }
                if(gamepad2.left_bumper == false) {
                    dropBeaconServo.setPosition(0.5);
                    telemetry.addData("Drop the Beacon",dropBeaconServo.getPosition());
                }
                    telemetry.update();
        }
    }
}
