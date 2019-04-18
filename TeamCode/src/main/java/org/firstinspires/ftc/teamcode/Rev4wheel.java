package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

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
    Servo latchLockServo;
    Servo collectorStop;


    //Declare values/constants for Static Power settings, Change for Tuning default Values
    double liftmotorUpPowerSetting = 1; //We need all the Power to Lift
    double liftmotorDownPowerSetting = -1; //We can make the motot slower when the are goes down
    double collectorMotorPowerSetting = 0.7 ; //We don't need full Power at the Collector, Run one direction
    double extendingArmPowerSetting = 1; //We only need half power, retracting and extending use same value, only sign change for motor direction

    static final double     COUNTS_PER_MOTOR_REV    = 28 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 40 ;     // This is < 1.0 if geared UP 40:1 reduce to 160 rpm
    static final double     WHEEL_DIAMETER_MM   = 100 ;     // For figuring circumference
    static final double     COUNTS_PER_MM         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_MM * 3.1415);

    private ElapsedTime runtime = new ElapsedTime();

    int extendValue;
    int liftValue;


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
        latchLockServo = hardwareMap.servo.get("latchLockServo");
        collectorStop = hardwareMap.servo.get("CollectorStopServo");
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

        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //Reverse wheels on the one side
        motorRightRear.setDirection(DcMotor.Direction.REVERSE);
        motorRightFront.setDirection(DcMotor.Direction.REVERSE);
        motorExtend.setDirection(DcMotor.Direction.REVERSE);
        dropBeaconServo.setDirection(Servo.Direction.REVERSE);
        motorCollect = hardwareMap.get(DcMotor.class, "motorCollect");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
//Make sure the Lock is Open
        latchLockServo.setPosition(1);
        dropBeaconServo.setPosition(0.5);
        //Make sure the collectorStop is Close
        collectorStop.setPosition(0);
        double speedControlPowerSetting = 0.75;
        double leftFrontPowerSetting = 0;
        double leftRearPowerSetting = 0;
        double rightFrontPowerSetting = 0;
        double rightRearPowerSetting = 0;
        boolean collecting = false;


        while (opModeIsActive()) {

//            //Set max Extender positions
//            if(motorLift.getCurrentPosition()>=11430)
//            {
//                motorLift.setTargetPosition(11425);
//            }
//            if(motorLift.getCurrentPosition()>3600)
//            {
//                motorExtend.setTargetPosition(-5160);
//            }
            //*************************************************************************************
            //                                  GamePad 2 Settings

            //Check if we should stop a Servo that has started, assum only one Servo

            //runServo1();
//            servoClass runServo1 = new servoClass();
//            servoClass runCollector = new servoClass();

            //Collector Stop settings
            //Right Bumper close the Jaws, Left opens it

            if(gamepad2.right_bumper == true) {
                //To Close
                if (collectorStop.getPosition() < 1) {
                    collectorStop.setPosition(0.5);
                    telemetry.addData("Close Collectot Stop", collectorStop.getPosition());
                }
            }
            //To Open
            if (gamepad2.left_bumper == true) {
                if(collectorStop.getPosition()>0) {
                    collectorStop.setPosition(0);
                    telemetry.addData("Open Collector Stop", collectorStop.getPosition());
                }
            }

            //Drop the beacon
            if(gamepad2.right_trigger >0){
                dropBeaconServo.setPosition(0);
                telemetry.addData("Drop the Beacon",dropBeaconServo.getPosition());
            }
            if(gamepad2.left_trigger >0) {
                dropBeaconServo.setPosition(0.5);
                telemetry.addData("Drop the Beacon",dropBeaconServo.getPosition());
            }

            //Open or Close the hitch && gamepad2.left_bumper
            if (gamepad2.dpad_left && !gamepad2.dpad_right && moveServoClose == false) {
                hitchServo.setPosition(1);
                telemetry.addData("Left Button", hitchServo.getPosition());
                moveServoClose = true;
                moveServoOpen = false;
            }
            //&& gamepad2.left_bumper
            if (gamepad2.dpad_right && !gamepad2.dpad_left  && moveServoOpen == false) {
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

            if (gamepad2.dpad_up == true && gamepad1.y == false){

                motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                liftValue = (10500 - motorLift.getCurrentPosition());

                motorLift.setTargetPosition(liftValue);

                runtime.reset();
                motorLift.setPower(1);
                while (opModeIsActive() &&
                        (motorLift.isBusy()) && !gamepad1.y && (runtime.seconds() < 5))
                {

                    // Display it for the Debugging.
                    telemetry.addData("Running to Target", liftValue);
                    telemetry.update();
                }
                // Stop all motion after Path is completed;
                motorLift.setPower(0);
                motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            if (gamepad2.dpad_down == true && gamepad1.y == false){
                motorExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                extendValue = -(2100 - motorExtend.getCurrentPosition());

                motorExtend.setTargetPosition(extendValue);

                runtime.reset();

                motorExtend.setPower(1);
                while (opModeIsActive() &&
                        (motorExtend.isBusy()) && !gamepad1.y && (runtime.seconds() < 5))
                {

                    // Display it for the Debugging.
                    telemetry.addData("Running to Target", extendValue);
                    telemetry.update();
                }
                motorExtend.setPower(0);
                motorExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            //*************************************************************************************
            //                                  GamePad 1 Settings
            //Lock and Collector Stop settings

//            if(gamepad1.left_trigger>0)
//            {
//                collectorStop.setPosition(1);
//                telemetry.addData("Collector Stop Engaged", hitchServo.getPosition());
//            }

            if(gamepad1.b == true) {
                //To Lock
                if (latchLockServo.getPosition() < 1) {
                    latchLockServo.setPosition(1);
                    telemetry.addData("Locking Lift Lock", hitchServo.getPosition());
                }
            }
                //To unlock
            if (gamepad1.x == true) {
                if(latchLockServo.getPosition()>0) {
                    latchLockServo.setPosition(0);
                    telemetry.addData("Un-Locking Lift Lock", hitchServo.getPosition());
                }
            }

//            //Set the Lift and Extender positions for Latching on Lander
//            if (gamepad1.right_trigger>0 ){
//                motorLift.setTargetPosition(11425);
//                motorExtend.setTargetPosition(-2431);
//            }

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
    void setHitchPosition(){}

}


