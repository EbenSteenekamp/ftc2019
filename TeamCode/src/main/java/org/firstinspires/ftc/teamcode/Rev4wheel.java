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
    DcMotor motorLift;
    DcMotor motorExtend;


    @Override
    public void runOpMode() throws InterruptedException {

        servo1 = hardwareMap.crservo.get("servo1");
        motorExtend = hardwareMap.get(DcMotor.class, "motorExtend");
        motorLift = hardwareMap.get(DcMotor.class, "motorLift");
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
        boolean Colect = false;

        while (opModeIsActive()) {

            //*************************************************************************************
            //                                  GamePad 2 Settings

            //Check if we should stop a Servo that has started, assum only one Servo

            //runServo1();
//            servoClass runServo1 = new servoClass();
//            servoClass runCollector = new servoClass();

            if (gamepad2.dpad_right && !gamepad2.dpad_left && gamepad2.left_bumper) {
                servo1.setPower(1);
                telemetry.addData("Left Button", servo1.getPower());
                sleep(1800);
            }
            if (gamepad2.dpad_left && !gamepad2.dpad_right && gamepad2.left_bumper) {
                servo1.setPower(-1);
                telemetry.addData("Right Button", servo1.getPower());
                sleep(1900);
            }
            if (gamepad2.dpad_right == false && gamepad2.dpad_left == false) {
                //Reset the Clock
                servo1.setPower(0);
                telemetry.addData("Button Center", servo1.getPower());
            }
            if (gamepad2.dpad_up){
                motorLift.setPower(1);//0.7 * gamepad2.right_stick_y);
                telemetry.addData("Lifting Motor Up", motorLift.getPower());
            }
            if (gamepad2.dpad_down){
                motorLift.setPower(-1);//0.7 * gamepad2.right_stick_y);
                telemetry.addData("Lifting Motor Down", motorLift.getPower());
            }
            if (gamepad2.dpad_up ==false && gamepad2.dpad_down ==false){
                motorLift.setPower(0);
                telemetry.addData("Lifting Motor Off", motorLift.getPower());
            }
            if (gamepad2.left_stick_y > 0){
                motorExtend.setPower(0.5 * gamepad2.left_stick_y);
                telemetry.addData("Extending Motor On", motorExtend.getPower());
            }
            if (gamepad2.left_stick_y < 0){
                motorExtend.setPower(0.5 * gamepad2.left_stick_y);
                telemetry.addData("Extending Motor On", motorExtend.getPower());
            }
            if (gamepad2.left_stick_y == 0){
                motorExtend.setPower(0);
                telemetry.addData("Extending Motor On", motorExtend.getPower());
            }
            telemetry.update();
            if (gamepad2.a && Colect == false){
                Colect = true;
            }
            if (gamepad2.a && Colect == true){
                Colect = false;
            }
            if (Colect) {
                motorCollect.setPower(1);
                telemetry.addData("Collection Motor On", motorCollect.getPower());
            } else {
                motorCollect.setPower(0);
                telemetry.addData("Collection Motor Off", motorCollect.getPower());
            }

            //*************************************************************************************
            //                                  GamePad 1 Settings

            if (gamepad1.right_bumper) {
                SpeedControl = 1;
            }
            if (gamepad1.left_bumper) {
                SpeedControl = 0.50;
            }
            //Stick control
            if ((gamepad1.dpad_right && gamepad1.dpad_left) == false) {
                //Single stick control
                if (gamepad1.left_trigger == 0) {
                    if (gamepad1.left_stick_x == 0) {
                        motorLeftFront.setPower(gamepad1.left_stick_y * SpeedControl);
                        motorLeftRear.setPower(gamepad1.left_stick_y * SpeedControl);
                        motorRightFront.setPower(gamepad1.left_stick_y * SpeedControl);
                        motorRightRear.setPower(gamepad1.left_stick_y * SpeedControl);
                    }
                    if (gamepad1.left_stick_x >= 0 && gamepad1.left_stick_y >= 0) {
                        motorRightFront.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) * SpeedControl);
                        motorRightRear.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) * SpeedControl);
                        motorLeftFront.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) * SpeedControl);
                        motorLeftRear.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) * SpeedControl);
                    }
                    if (gamepad1.left_stick_x >= 0 && gamepad1.left_stick_y <= 0) {
                        motorRightFront.setPower((gamepad1.left_stick_y + (gamepad1.left_stick_x * -1)) * SpeedControl);
                        motorRightRear.setPower((gamepad1.left_stick_y + (gamepad1.left_stick_x * -1)) * SpeedControl);
                        motorLeftFront.setPower((gamepad1.left_stick_y - (gamepad1.left_stick_x * -1)) * SpeedControl);
                        motorLeftRear.setPower((gamepad1.left_stick_y - (gamepad1.left_stick_x * -1)) * SpeedControl);
                    }
                    if (gamepad1.left_stick_x <= 0 && gamepad1.left_stick_y >= 0) {
                        motorRightFront.setPower((gamepad1.left_stick_y - (gamepad1.left_stick_x * -1)) * SpeedControl);
                        motorRightRear.setPower((gamepad1.left_stick_y - (gamepad1.left_stick_x * -1)) * SpeedControl);
                        motorLeftFront.setPower((gamepad1.left_stick_y + (gamepad1.left_stick_x * -1)) * SpeedControl);
                        motorLeftRear.setPower((gamepad1.left_stick_y + (gamepad1.left_stick_x * -1)) * SpeedControl);
                    }
                    if (gamepad1.left_stick_x <= 0 && gamepad1.left_stick_y <= 0) {
                        motorRightFront.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) * SpeedControl);
                        motorRightRear.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x) * SpeedControl);
                        motorLeftFront.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) * SpeedControl);
                        motorLeftRear.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) * SpeedControl);
                    }
                    //Strafe Stick
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

                if ((gamepad1.dpad_right && gamepad1.dpad_left) == false) {

                    motorLeftFront.setPower(gamepad1.right_stick_y * SpeedControl);
                    motorLeftRear.setPower(gamepad1.right_stick_y * SpeedControl);
                    motorRightFront.setPower(gamepad1.left_stick_y * SpeedControl);
                    motorRightRear.setPower(gamepad1.left_stick_y * SpeedControl);
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
    }
}
