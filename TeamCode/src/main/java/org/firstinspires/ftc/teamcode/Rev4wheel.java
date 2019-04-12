package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Rev4wheel", group="Linear Opmode")

public class Rev4wheel extends LinearOpMode {
    DcMotor motorLeftFront;
    DcMotor motorLeftRear;
    DcMotor motorRightFront;
    DcMotor motorRightRear;
    DcMotor motorCollect;
    DcMotor motorLift;
    DcMotor motorExtend;
    Servo dropBeaconServo;
    Servo hitchServo;

    //Declare values/constants for Static Power settings, Change for Tuning default Values
    double liftmotorUpPowerSetting = 1; //We need all the Power to Lift
    double liftmotorDownPowerSetting = -1; //We can make the motot slower when the are goes down
    double collectorMotorPowerSetting = 0.7 ; //We don't need full Power at the Collector, Run one direction
    double extendingArmPowerSetting = 1; //We only need half power, retracting and extending use same value, only sign change for motor direction

    boolean moveServoOpen = false;
    boolean moveServoClose = false;

    @Override
    public void runOpMode() throws InterruptedException {

        //Declare all hardware
        hitchServo = hardwareMap.servo.get("hitchServo");
        motorExtend = hardwareMap.get(DcMotor.class, "motorExtend");
        motorExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dropBeaconServo = hardwareMap.servo.get("DropBeaconServo");
        motorLift = hardwareMap.get(DcMotor.class, "motorLift");
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //Front wheels
        motorLeftFront = hardwareMap.get(DcMotor.class, "motorLeftFront");
        motorRightFront = hardwareMap.get(DcMotor.class, "motorRightFront");
        //Set Encoder Stuff
        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //Set Position to Zero
        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Rear wheels
        motorLeftRear = hardwareMap.get(DcMotor.class, "motorLeftRear");
        motorRightRear = hardwareMap.get(DcMotor.class, "motorRightRear");

        motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Reverse wheels on the one side
        motorRightRear.setDirection(DcMotor.Direction.REVERSE);
        motorRightFront.setDirection(DcMotor.Direction.REVERSE);
        motorExtend.setDirection(DcMotor.Direction.REVERSE);
        dropBeaconServo.setDirection(Servo.Direction.REVERSE);
        motorCollect = hardwareMap.get(DcMotor.class, "motorCollect");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        dropBeaconServo.setPosition(0.5);
        double speedControlPowerSetting = 0.75;
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

            //Drop the beacon
            if(gamepad2.right_trigger >0){
                dropBeaconServo.setPosition(0);
                telemetry.addData("Drop the Beacon",dropBeaconServo.getPosition());
            }
            if(gamepad2.left_trigger >0) {
                dropBeaconServo.setPosition(0.5);
                telemetry.addData("Drop the Beacon",dropBeaconServo.getPosition());
            }

            //Open or Close the hitch
            if (gamepad2.dpad_left && !gamepad2.dpad_right && gamepad2.left_bumper && moveServoClose == false) {
                hitchServo.setPosition(1);
                telemetry.addData("Left Button", hitchServo.getPosition());
                moveServoClose = true;
                moveServoOpen = false;
            }
            if (gamepad2.dpad_right && !gamepad2.dpad_left && gamepad2.left_bumper && moveServoOpen == false) {
                hitchServo.setPosition(0);
                telemetry.addData("Right Button", hitchServo.getPosition());
                moveServoClose = false;
                moveServoOpen = true;
            }

            //Lift the arm
            if (gamepad2.left_stick_y <0 ){
                motorLift.setPower(liftmotorUpPowerSetting);
                //telemetry.addData("Lifting Motor Up", motorLift.getPower());
                //Add Telemetry
                telemetry.addData("Lift Up position", motorLift.getCurrentPosition());
            }
            if (gamepad2.left_stick_y >0){
                motorLift.setPower(liftmotorDownPowerSetting);
                //telemetry.addData("Lifting Motor Down", motorLift.getPower());
                telemetry.addData("Lift Down position", motorLift.getCurrentPosition());
            }
            if (gamepad2.left_stick_y == 0){
                motorLift.setPower(0);
                telemetry.addData("Lift Down position", motorLift.getCurrentPosition());
                //telemetry.addData("Lifting Motor Off", motorLift.getPower());
            }

            //Extend the arm
            if (gamepad2.right_stick_y >0){
                motorExtend.setPower(extendingArmPowerSetting * gamepad2.right_stick_y);
                //telemetry.addData("Extending Motor, extending Arm", motorExtend.getPower());
                telemetry.addData("Extending Motor position,extending", motorExtend.getCurrentPosition());
            }
            if (gamepad2.right_stick_y <0){
                motorExtend.setPower(extendingArmPowerSetting * gamepad2.right_stick_y);
                //telemetry.addData("Extending Motor, retracting Arm", motorExtend.getPower());
                telemetry.addData("Extending Motor position,retracting", motorExtend.getCurrentPosition());
            }
            if (gamepad2.right_stick_y == 0){
                motorExtend.setPower(0);
                //telemetry.addData("Extending Motor On", motorExtend.getPower());
                telemetry.addData("Extending Motor position,retracting", motorExtend.getCurrentPosition());
            }
            telemetry.update();
//            if (gamepad2.a && collecting == false){
//                collecting = false;
//            }
//            if (gamepad2.a && collecting == true){
//                collecting = true;
//            }
            //motorCollect.setPower(0);

            //Turn collector on
            if(gamepad2.a == true) {
                motorCollect.setPower(0.6);
                //telemetry.addData("Collection Motor On", motorCollect.getPower());
            }
            if(gamepad2.b == true){
                motorCollect.setPower(-0.6);
                //telemetry.addData("Collection Motor On", motorCollect.getPower());

            }
            if(gamepad2.b == false && gamepad2.a == false){
                motorCollect.setPower(0);
                //telemetry.addData("Collection Motor On", motorCollect.getPower());

            }

            //*************************************************************************************
            //                                  GamePad 1 Settings

            //Set speed control
            if (gamepad1.right_bumper) {
                speedControlPowerSetting = 1;
            }
            if (gamepad1.left_bumper) {
                speedControlPowerSetting = 0.75;
            }

            //Set motor speeds for driving
            if ((gamepad1.dpad_right == false && gamepad1.dpad_left == false)){

                motorLeftFront.setPower(gamepad1.left_stick_y * speedControlPowerSetting);
                motorLeftRear.setPower(gamepad1.left_stick_y * speedControlPowerSetting);
                motorRightFront.setPower(gamepad1.right_stick_y * speedControlPowerSetting);
                motorRightRear.setPower(gamepad1.right_stick_y * speedControlPowerSetting);
//                telemetry.addData("LF Motor position", motorLeftFront.getCurrentPosition());

//                telemetry.addData("LF Target Power", leftFrontPowerSetting);
//                telemetry.addData("LF Motor Power", motorLeftFront.getPower());
//
//                telemetry.addData("LR Target Power", leftRearPowerSetting);
//                telemetry.addData("LR Motor Power", motorLeftRear.getPower());
//
//                telemetry.addData("RF Target Power", rightFrontPowerSetting);
//                telemetry.addData("RF Motor Power", motorRightFront.getPower());
//
//                telemetry.addData("RR Target Power", rightRearPowerSetting);
//                telemetry.addData("RR Motor Power", motorRightRear.getPower());


                //telemetry.addData("Status", "Running");
                telemetry.addData("Path1",  "Running to Target LF,LR, RF, RR %7d :%7d :%7d :%7d",
                        motorLeftFront.getCurrentPosition(),
                        motorLeftRear.getCurrentPosition(),
                        motorRightFront.getCurrentPosition(),
                        motorRightRear.getCurrentPosition());
                telemetry.update();

            }

            //Strafe
            while (gamepad1.right_trigger >0) {

                motorLeftFront.setPower(-speedControlPowerSetting);
                motorLeftRear.setPower(+speedControlPowerSetting);
                motorRightFront.setPower(+speedControlPowerSetting);
                motorRightRear.setPower(-speedControlPowerSetting);

//                telemetry.addData("LF Target Power", leftFrontPowerSetting);
//                telemetry.addData("LF Motor Power", motorLeftFront.getPower());
//
//                telemetry.addData("LR Target Power", leftRearPowerSetting);
//                telemetry.addData("LR Motor Power", motorLeftRear.getPower());
//
//                telemetry.addData("RF Target Power", rightFrontPowerSetting);
//                telemetry.addData("RF Motor Power", motorRightFront.getPower());
//
//                telemetry.addData("RR Target Power", rightRearPowerSetting);
 //               telemetry.addData("RR Motor Power", motorRightRear.getPower());


//                telemetry.addData("Status", "Running");
                telemetry.addData("Path1",  "Running to Target LF,LR, RF, RR %7d :%7d :%7d :%7d",
                        motorLeftFront.getCurrentPosition(),
                        motorLeftRear.getCurrentPosition(),
                        motorRightFront.getCurrentPosition(),
                        motorRightRear.getCurrentPosition());
                telemetry.update();
            }

            while (gamepad1.left_trigger >0) {
                motorLeftFront.setPower(+speedControlPowerSetting);
                motorLeftRear.setPower(-speedControlPowerSetting);
                motorRightFront.setPower(-speedControlPowerSetting);
                motorRightRear.setPower(+speedControlPowerSetting);

//                telemetry.addData("LF Target Power", leftFrontPowerSetting);
//                telemetry.addData("LF Motor Power", motorLeftFront.getPower());
//
//                telemetry.addData("LR Target Power", leftRearPowerSetting);
//                telemetry.addData("LR Motor Power", motorLeftRear.getPower());
//
//                telemetry.addData("RF Target Power", rightFrontPowerSetting);
//                telemetry.addData("RF Motor Power", motorRightFront.getPower());
//
//                telemetry.addData("RR Target Power", rightRearPowerSetting);
//                telemetry.addData("RR Motor Power", motorRightRear.getPower());


//                telemetry.addData("Status", "Running");
                telemetry.addData("Path1",  "Running to Target LF,LR, RF, RR %7d :%7d :%7d :%7d",
                        motorLeftFront.getCurrentPosition(),
                        motorLeftRear.getCurrentPosition(),
                        motorRightFront.getCurrentPosition(),
                        motorRightRear.getCurrentPosition());
                telemetry.update();
            }
            telemetry.update();
        }
    }
}


