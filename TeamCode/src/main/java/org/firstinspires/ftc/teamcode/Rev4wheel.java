package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.CRServo;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Rev4wheel", group="Linear Opmode")

public class Rev4wheel extends LinearOpMode {
    DcMotor motorLeftFront;
    DcMotor motorLeftRear;
    DcMotor motorRightFront;
    DcMotor motorRightRear;
    CRServo hitchServo;
    DcMotor motorCollect;
    DcMotor motorLift;
    DcMotor motorExtend;
    Servo brakeServo;

    //Declare values/constants for Static Power settings, Change for Tuning default Values
    double liftmotorUpPowerSetting = 1; //We need all the Power to Lift
    double liftmotorDownPowerSetting = -1; //We can make the motot slower when the are goes down
    double collectorMotorPowerSetting = 0.7 ; //We don't need full Power at the Collector, Run one direction
    double extendingArmPowerSetting = 0.5; //We only need half power, retracting and extending use same value, only sign change for motor direction

    @Override
    public void runOpMode() throws InterruptedException {

<<<<<<< HEAD
        servoB = hardwareMap.servo.get("servoB");
        servo1 = hardwareMap.crservo.get("servo1");

=======
        hitchServo = hardwareMap.crservo.get("hitchServo");
>>>>>>> 24e38f6b55f87f32715f72c81402845d5d08e17e
        motorExtend = hardwareMap.get(DcMotor.class, "motorExtend");
        motorLift = hardwareMap.get(DcMotor.class, "motorLift");
        motorLeftFront = hardwareMap.get(DcMotor.class, "motorLeftFront");
        motorLeftRear = hardwareMap.get(DcMotor.class, "motorLeftRear");
        motorRightFront = hardwareMap.get(DcMotor.class, "motorRightFront");
        motorRightRear = hardwareMap.get(DcMotor.class, "motorRightRear");
        motorLeftFront.setDirection(DcMotor.Direction.REVERSE);
        motorLeftRear.setDirection(DcMotor.Direction.REVERSE);
<<<<<<< HEAD
=======
        motorRightRear.setDirection(DcMotor.Direction.REVERSE);
        motorRightFront.setDirection(DcMotor.Direction.REVERSE);
>>>>>>> 24e38f6b55f87f32715f72c81402845d5d08e17e
        brakeServo = hardwareMap.servo.get("brakeServo");
        motorCollect = hardwareMap.get(DcMotor.class, "motorCollect");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        double speedControlPowerSetting = 0.5;
        double leftFrontPowerSetting = 0;
        double leftRearPowerSetting = 0;
        double rightFrontPowerSetting = 0;
        double rightRearPowerSetting = 0;
        boolean collecting = false;


        while (opModeIsActive()) {

            //*************************************************************************************
            //                                  GamePad 2 Settings

            //Check if we should stop a Servo that has started, assum only one Servo

            //runServo1();
//            servoClass runServo1 = new servoClass();
//            servoClass runCollector = new servoClass();

            if (gamepad2.dpad_right && !gamepad2.dpad_left && gamepad2.left_bumper) {
<<<<<<< HEAD

                servoB.setPosition(1);
                servo1.setPower(1);
                telemetry.addData("Left Button", servo1.getPower());
=======
                hitchServo.setPower(1);
                telemetry.addData("Left Button", hitchServo.getPower());
>>>>>>> 24e38f6b55f87f32715f72c81402845d5d08e17e
                sleep(1800);
            }
            if (gamepad2.dpad_left && !gamepad2.dpad_right && gamepad2.left_bumper) {
                hitchServo.setPower(-1);
                telemetry.addData("Right Button", hitchServo.getPower());
                sleep(1900);
            }
            if (gamepad2.dpad_right == false && gamepad2.dpad_left == false) {
<<<<<<< HEAD
                //Reset the Clock
=======
>>>>>>> 24e38f6b55f87f32715f72c81402845d5d08e17e
                hitchServo.setPower(0);
                telemetry.addData("Button Center", hitchServo.getPower());
            }
            if (gamepad2.left_stick_y <0 ){
                motorLift.setPower(liftmotorUpPowerSetting);
                telemetry.addData("Lifting Motor Up", motorLift.getPower());
            }
            if (gamepad2.left_stick_y >0){
                motorLift.setPower(liftmotorDownPowerSetting);
                telemetry.addData("Lifting Motor Down", motorLift.getPower());
            }
            if (gamepad2.left_stick_y == 0){
                motorLift.setPower(0);
                telemetry.addData("Lifting Motor Off", motorLift.getPower());
            }
<<<<<<< HEAD
            if (gamepad2.left_stick_y < 0){
                motorExtend.setPower(extendingArmPowerSetting * gamepad2.left_stick_y);
                telemetry.addData("Extending Motor, extending Arm", motorExtend.getPower());
            }
            if (gamepad2.left_stick_y > 0){
                motorExtend.setPower(-extendingArmPowerSetting * gamepad2.left_stick_y);
=======
            if (gamepad2.right_stick_y >0){
                motorExtend.setPower(extendingArmPowerSetting * gamepad2.right_stick_y);
                telemetry.addData("Extending Motor, extending Arm", motorExtend.getPower());
            }
            if (gamepad2.right_stick_y <0){
                motorExtend.setPower(extendingArmPowerSetting * gamepad2.right_stick_y);
>>>>>>> 24e38f6b55f87f32715f72c81402845d5d08e17e
                telemetry.addData("Extending Motor, retracting Arm", motorExtend.getPower());
            }
            if (gamepad2.right_stick_y == 0){
                motorExtend.setPower(0);
                telemetry.addData("Extending Motor On", motorExtend.getPower());
            }
            telemetry.update();
//            if (gamepad2.a && collecting == false){
//                collecting = false;
//            }
//            if (gamepad2.a && collecting == true){
//                collecting = true;
//            }
            //motorCollect.setPower(0);
            if(gamepad2.a == true) {
<<<<<<< HEAD
                motorCollect.setPower(1);
                telemetry.addData("Collection Motor On", motorCollect.getPower());
            }
            if(gamepad2.b == true){
                        motorCollect.setPower(-1);
=======
                motorCollect.setPower(0.6);
                telemetry.addData("Collection Motor On", motorCollect.getPower());
            }
            if(gamepad2.b == true){
                        motorCollect.setPower(-0.6);
>>>>>>> 24e38f6b55f87f32715f72c81402845d5d08e17e
                        telemetry.addData("Collection Motor On", motorCollect.getPower());

            }
            if(gamepad2.b == false && gamepad2.a == false){
                motorCollect.setPower(0);
                telemetry.addData("Collection Motor On", motorCollect.getPower());

            }

            //*************************************************************************************
            //                                  GamePad 1 Settings

<<<<<<< HEAD
            if(gamepad1.a ){
                    brakeServo.setPosition(15);
                    telemetry.addData("Brake Set On", brakeServo.getPosition());

            }
=======
//            if(gamepad1.a ){
//                    brakeServo.setPosition(15);
//                    telemetry.addData("Brake Set On", brakeServo.getPosition());
//
//            }
>>>>>>> 24e38f6b55f87f32715f72c81402845d5d08e17e
            if (gamepad1.right_bumper) {
                speedControlPowerSetting = 1;
            }
            if (gamepad1.left_bumper) {
                speedControlPowerSetting = 0.50;
            }
                if ((gamepad1.dpad_right == false && gamepad1.dpad_left == false)){

                    motorLeftFront.setPower(gamepad1.left_stick_y * speedControlPowerSetting);
                    motorLeftRear.setPower(gamepad1.left_stick_y * speedControlPowerSetting);
                    motorRightFront.setPower(gamepad1.right_stick_y * speedControlPowerSetting);
                    motorRightRear.setPower(gamepad1.right_stick_y * speedControlPowerSetting);
                    telemetry.addData("LF Target Power", leftFrontPowerSetting);
                    telemetry.addData("LF Motor Power", motorLeftFront.getPower());

                    telemetry.addData("LR Target Power", leftRearPowerSetting);
                    telemetry.addData("LR Motor Power", motorLeftRear.getPower());

                    telemetry.addData("RF Target Power", rightFrontPowerSetting);
                    telemetry.addData("RF Motor Power", motorRightFront.getPower());

                    telemetry.addData("RR Target Power", rightRearPowerSetting);
                    telemetry.addData("RR Motor Power", motorRightRear.getPower());


                    telemetry.addData("Status", "Running");
//                telemetry.update();

                }

                while (gamepad1.dpad_right) {

                    motorLeftFront.setPower(-1);
                    motorLeftRear.setPower(+1);
                    motorRightFront.setPower(-1);
                    motorRightRear.setPower(+1);

                    telemetry.addData("LF Target Power", leftFrontPowerSetting);
                    telemetry.addData("LF Motor Power", motorLeftFront.getPower());

                    telemetry.addData("LR Target Power", leftRearPowerSetting);
                    telemetry.addData("LR Motor Power", motorLeftRear.getPower());

                    telemetry.addData("RF Target Power", rightFrontPowerSetting);
                    telemetry.addData("RF Motor Power", motorRightFront.getPower());

                    telemetry.addData("RR Target Power", rightRearPowerSetting);
                    telemetry.addData("RR Motor Power", motorRightRear.getPower());


                    telemetry.addData("Status", "Running");
                    //telemetry.update();
                }

                while (gamepad1.dpad_left) {
                    motorLeftFront.setPower(+1);
                    motorLeftRear.setPower(-1);
                    motorRightFront.setPower(+1);
                    motorRightRear.setPower(-1);

                    telemetry.addData("LF Target Power", leftFrontPowerSetting);
                    telemetry.addData("LF Motor Power", motorLeftFront.getPower());

                    telemetry.addData("LR Target Power", leftRearPowerSetting);
                    telemetry.addData("LR Motor Power", motorLeftRear.getPower());

                    telemetry.addData("RF Target Power", rightFrontPowerSetting);
                    telemetry.addData("RF Motor Power", motorRightFront.getPower());

                    telemetry.addData("RR Target Power", rightRearPowerSetting);
                    telemetry.addData("RR Motor Power", motorRightRear.getPower());


                    telemetry.addData("Status", "Running");
                    //telemetry.update();
                }
                telemetry.update();
            }
        }
    }


