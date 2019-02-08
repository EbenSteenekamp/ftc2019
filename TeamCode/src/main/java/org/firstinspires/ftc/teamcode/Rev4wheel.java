package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Rev4wheel", group="Linear Opmode")

public class Rev4wheel extends LinearOpMode {
    DcMotor motorLeftFront;
    DcMotor motorLeftRear;
    DcMotor motorRightFront;
    DcMotor motorRightRear;
    CRServo servo1;
    DcMotor motorCollect;

    double servostarted = 0;
    boolean servoclockstarted =false;

    @Override
    public void runOpMode() throws InterruptedException {

        servo1 = hardwareMap.crservo.get("servo1");
        motorLeftFront = hardwareMap.get(DcMotor.class, "motorLeftFront");
        motorLeftRear = hardwareMap.get(DcMotor.class, "motorLeftRear");
        motorRightFront = hardwareMap.get(DcMotor.class, "motorRightFront");
        motorRightRear = hardwareMap.get(DcMotor.class, "motorRightRear");
        motorLeftFront.setDirection(DcMotor.Direction.REVERSE);
        motorLeftRear.setDirection(DcMotor.Direction.REVERSE);
        motorCollect = hardwareMap.get(DcMotor.class, "motorCollect");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        double SpeedControl = 0.5;
        double tgtPower1 = 0;
        double tgtPower2 = 0;
        double tgtPower3 = 0;
        double tgtPower4 = 0;

        while (opModeIsActive()) {

            //*************************************************************************************
            //                                  GamePad 2 Settings

            //Check if we should stop a Servo that has started, assum only one Servo

            runServo1();

            if(gamepad2.a)
            {
                motorCollect.setPower(1);
                telemetry.addData("Collection Motor On", motorCollect.getPower());
            }
            else
            {
                motorCollect.setPower(0);
                telemetry.addData("Collection Motor Off", motorCollect.getPower());
            }

//            if (gamepad2.dpad_right && !gamepad2.dpad_left) {
//
//                servoclockstarted = true;
//                servo1.setPower(1);
//
//                telemetry.addData("Left Button", servo1.getPower());
//            }
//            if (gamepad2.dpad_left && !gamepad2.dpad_right) {
//
//                servoclockstarted = true;
//                servo1.setPower(-1);
//                telemetry.addData("Right Button", servo1.getPower());
//            }
//            if(gamepad2.dpad_right == false && gamepad2.dpad_left == false)
//            {
//                //Reset the Clock
//                servoclockstarted = false;
//                servo1.setPower(0);
//                telemetry.addData("Button Center", servo1.getPower());
//            }
            telemetry.update();

            //*************************************************************************************
            //                                  GamePad 1 Settings

            if (gamepad1.right_bumper) {
                SpeedControl = 1;
            }
            if (gamepad1.left_bumper) {
                SpeedControl = 0.50;
            }

            if ((gamepad1.dpad_right && gamepad1.dpad_left) == false) {

                motorLeftFront.setPower(gamepad1.right_stick_y*SpeedControl);
                motorLeftRear.setPower(gamepad1.right_stick_y*SpeedControl);
                motorRightFront.setPower(gamepad1.left_stick_y*SpeedControl);
                motorRightRear.setPower(gamepad1.left_stick_y*SpeedControl);
                telemetry.addData("LF Target Power", tgtPower1);
                telemetry.addData("LF Motor Power", motorLeftFront.getPower());

                telemetry.addData("LR Target Power", tgtPower2);
                telemetry.addData("LR Motor Power", motorLeftRear.getPower());

                telemetry.addData("RF Target Power", tgtPower3);
                telemetry.addData("RF Motor Power", motorRightFront.getPower());

                telemetry.addData("RR Target Power", tgtPower4);
                telemetry.addData("RR Motor Power", motorRightRear.getPower());

                telemetry.addData("Status", "Running");
//                telemetry.update();

            }
            while (gamepad1.dpad_left) {

                motorLeftFront.setPower(-1);
                motorLeftRear.setPower(+1);
                motorRightFront.setPower(+1);
                motorRightRear.setPower(-1);
                telemetry.addData("LF Target Power", tgtPower1);
                telemetry.addData("LF Motor Power", motorLeftFront.getPower());

                telemetry.addData("LR Target Power", tgtPower2);
                telemetry.addData("LR Motor Power", motorLeftRear.getPower());

                telemetry.addData("RF Target Power", tgtPower3);
                telemetry.addData("RF Motor Power", motorRightFront.getPower());

                telemetry.addData("RR Target Power", tgtPower4);
                telemetry.addData("RR Motor Power", motorRightRear.getPower());

                telemetry.addData("Status", "Running");
                //telemetry.update();
            }
            while (gamepad1.dpad_right) {
                motorLeftFront.setPower(+1);
                motorLeftRear.setPower(-1);
                motorRightFront.setPower(-1);
                motorRightRear.setPower(+1);
                telemetry.addData("LF Target Power", tgtPower1);
                telemetry.addData("LF Motor Power", motorLeftFront.getPower());

                telemetry.addData("LR Target Power", tgtPower2);
                telemetry.addData("LR Motor Power", motorLeftRear.getPower());

                telemetry.addData("RF Target Power", tgtPower3);
                telemetry.addData("RF Motor Power", motorRightFront.getPower());

                telemetry.addData("RR Target Power", tgtPower4);
                telemetry.addData("RR Motor Power", motorRightRear.getPower());

                telemetry.addData("Status", "Running");
                //telemetry.update();
            }
            telemetry.update();
        }
    }

    private void runServo1()
    {
        if(this.getRuntime()- servostarted > 10)
        {
            servo1.setPower(0);
        }
        else
        {
            if (gamepad2.dpad_right && !gamepad2.dpad_left) {

                servostarted = servostarted + getRuntime();
                servo1.setPower(1);

                telemetry.addData("Left Button", servo1.getPower());
            }
            if (gamepad2.dpad_left && !gamepad2.dpad_right) {

                servostarted = servostarted + getRuntime();
                servo1.setPower(-1);
                telemetry.addData("Right Button", servo1.getPower());
            }
            if(gamepad2.dpad_right == false && gamepad2.dpad_left == false)
            {
                //Reset the Clock
                servostarted = 0;
                servo1.setPower(0);
                telemetry.addData("Button Center", servo1.getPower());
            }
        }
    }

}
