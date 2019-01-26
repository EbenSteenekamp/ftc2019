package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="DriveServo", group="Linear Opmode")

public class DriveServo extends LinearOpMode {

    static final double INCREMENT   = 0.5;
    static final int    CYCLE_MS    =   50;
    static final double MAX_POS     =  1.0;
    static final double MIN_POS     =  0.0;
    DcMotor motorLeftFront;
    DcMotor motorLeftRear;
    DcMotor motorRightFront;
    DcMotor motorRightRear;
    DcMotor motorCollect;
    Servo servo;
    double  position = (MAX_POS - MIN_POS) / 2;

    @Override
    public void runOpMode() throws InterruptedException {

        servo = hardwareMap.get(Servo.class, "servoClamp");
        motorLeftFront = hardwareMap.get(DcMotor.class, "motorLeftFront");
        motorLeftRear = hardwareMap.get(DcMotor.class, "motorLeftRear");
        motorRightFront = hardwareMap.get(DcMotor.class, "motorRightFront");
        motorRightRear = hardwareMap.get(DcMotor.class, "motorRightRear");
        motorCollect = hardwareMap.get(DcMotor.class, "motorCollect");
        motorLeftFront.setDirection(DcMotor.Direction.REVERSE);
        motorLeftRear.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        double SpeedControl = 0.5;
        double tgtPower1 = 0;
        double tgtPower2 = 0;
        double tgtPower3 = 0;
        double tgtPower4 = 0;
        Boolean tgtPower5 = false;
        Boolean tgtPowerML = false;
        Boolean tgtPowerMR = false;


        while (opModeIsActive()) {

            if (gamepad2.b ) {
                telemetry.addData("Push Up", "%5.2f", position);
                telemetry.update();
                position += INCREMENT ;
                if (position >= MAX_POS ) {
                    position = MAX_POS;

                }
            }

            if (gamepad2.a ){
                telemetry.addData("Push Down", "%5.2f", position);
                telemetry.update();
                position -= INCREMENT ;
                if (position <= MIN_POS ) {
                    position = MIN_POS;

                }
            }

            if(gamepad2.left_bumper ) {
                motorCollect.setPower(+1);
            }
            else{
                motorCollect.setPower(0);
            }

            if (gamepad1.right_bumper) {
                SpeedControl = 1;
            }
            if (gamepad1.left_bumper) {
                SpeedControl = 0.50;
            }

            if ((gamepad1.dpad_right && gamepad1.dpad_left) == false) {

                if (gamepad1.left_trigger >= 0) {
                    motorLeftFront.setPower(gamepad1.right_stick_y * SpeedControl);
                    motorLeftRear.setPower(gamepad1.right_stick_y * SpeedControl);
                    motorRightFront.setPower(gamepad1.left_stick_y * SpeedControl);
                    motorRightRear.setPower(gamepad1.left_stick_y * SpeedControl);
                }

                if (gamepad1.left_trigger == 0) {

                    if (gamepad1.left_stick_x == 0) {

                        motorLeftFront.setPower(gamepad1.left_stick_y * SpeedControl);
                        motorLeftRear.setPower(gamepad1.left_stick_y * SpeedControl);
                        motorRightFront.setPower(gamepad1.left_stick_y * SpeedControl);
                        motorRightRear.setPower(gamepad1.left_stick_y * SpeedControl);
                    }
                    if (gamepad1.left_stick_x >= 0 && gamepad1.left_stick_y >= 0) {

                        motorRightFront.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) * SpeedControl );
                        motorRightRear.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) * SpeedControl );
                        motorLeftFront.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) * SpeedControl);
                        motorLeftRear.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) * SpeedControl);
                    }

                    if (gamepad1.left_stick_x >= 0 && gamepad1.left_stick_y <= 0) {

                        motorRightFront.setPower((gamepad1.left_stick_y + (gamepad1.left_stick_x * -1)) * SpeedControl );
                        motorRightRear.setPower((gamepad1.left_stick_y + (gamepad1.left_stick_x * -1)) * SpeedControl );
                        motorLeftFront.setPower((gamepad1.left_stick_y - (gamepad1.left_stick_x * -1)) * SpeedControl);
                        motorLeftRear.setPower((gamepad1.left_stick_y - (gamepad1.left_stick_x * -1)) * SpeedControl);
                    }

                    if (gamepad1.left_stick_x <= 0 && gamepad1.left_stick_y >= 0) {

                        motorRightFront.setPower((gamepad1.left_stick_y - (gamepad1.left_stick_x * -1))* SpeedControl);
                        motorRightRear.setPower((gamepad1.left_stick_y - (gamepad1.left_stick_x * -1)) * SpeedControl);
                        motorLeftFront.setPower((gamepad1.left_stick_y + (gamepad1.left_stick_x * -1)) * SpeedControl );
                        motorLeftRear.setPower((gamepad1.left_stick_y + (gamepad1.left_stick_x * -1)) * SpeedControl );

                    }

                    if (gamepad1.left_stick_x <= 0 && gamepad1.left_stick_y <= 0) {

                        motorRightFront.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) * SpeedControl);
                        motorRightRear.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) * SpeedControl);
                        motorLeftFront.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) * SpeedControl );
                        motorLeftRear.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) * SpeedControl );

                    }

                    if (gamepad1.right_stick_x >= 0.5) {

                        motorLeftFront.setPower(+1);
                        motorLeftRear.setPower(-1);
                        motorRightFront.setPower(-1);
                        motorRightRear.setPower(+1);
                    }

                    if (gamepad1.right_stick_x <= -0.5) {

                        motorLeftFront.setPower(-1);
                        motorLeftRear.setPower(+1);
                        motorRightFront.setPower(+1);
                        motorRightRear.setPower(-1);
                    }

                }

            }
            while (gamepad1.dpad_left) {

                motorLeftFront.setPower(-1);
                motorLeftRear.setPower(+1);
                motorRightFront.setPower(+1);
                motorRightRear.setPower(-1);
            }
            while (gamepad1.dpad_right) {
                motorLeftFront.setPower(+1);
                motorLeftRear.setPower(-1);
                motorRightFront.setPower(-1);
                motorRightRear.setPower(+1);
            }

            telemetry.addData("LF Target Power", tgtPower1);
            telemetry.addData("LF Motor Power", motorLeftFront.getPower());

            telemetry.addData("LR Target Power", tgtPower2);
            telemetry.addData("LR Motor Power", motorLeftRear.getPower());

            telemetry.addData("RF Target Power", tgtPower3);
            telemetry.addData("RF Motor Power", motorRightFront.getPower());

            telemetry.addData("RR Target Power", tgtPower4);
            telemetry.addData("RR Motor Power", motorRightRear.getPower());

            telemetry.addData("MC Target Power", tgtPower5);
            telemetry.addData("MC Motor Power", motorCollect.getPower());

            telemetry.addData("Status", "Running");
            telemetry.update();

            telemetry.addData(">", "Press Start Now to scan Servo." );
            telemetry.update();
            waitForStart();

            telemetry.addData("Servo Position", "%5.2f", position);
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();
            servo.setPosition(position);

        }
        telemetry.addData(">", "Done");
        telemetry.update();
    }

}



