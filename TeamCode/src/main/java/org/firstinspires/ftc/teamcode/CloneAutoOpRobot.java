package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.comp.Lower;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Locale;

import java.lang.annotation.Target;

public class CloneAutoOpRobot extends LinearOpMode {

    public DcMotor motorLeftFront;
    public DcMotor motorLeftRear;
    public DcMotor motorRightFront;
    public DcMotor motorRightRear;
    public DcMotor motorCollect;
    public DcMotor motorLift;
    public DcMotor motorExtend;
    public Servo hitchServo;
    public Servo dropBeaconServo;

    boolean lower = false;
    boolean pit = false;

    HardwareMap masterConfig = null;
    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 28 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 40 ;     // This is < 1.0 if geared UP 40:1 reduce to 160 rpm
    static final double     WHEEL_DIAMETER_MM   = 100 ;     // For figuring circumference
    static final double     COUNTS_PER_MM         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_MM * 3.1415);

    static final double     DRIVE_SPEED             = 1;
    static final double     TURN_SPEED              = 0.5;
    static final double     STRAFE_SPEED            = 0.5;

    private Telemetry robottelemetry;

    public CloneAutoOpRobot() {

    }

    @Override
    public void runOpMode(){}

    public void init(HardwareMap amasterConfig) {
        masterConfig = amasterConfig;

        hitchServo = masterConfig.servo.get("hitchServo");
        motorCollect = masterConfig.get(DcMotor.class, "motorCollect");
        motorExtend = masterConfig.get(DcMotor.class, "motorExtend");
        motorLift = masterConfig.get(DcMotor.class, "motorLift");
        motorLeftFront = masterConfig.get(DcMotor.class, "motorLeftFront");
        motorLeftRear = masterConfig.get(DcMotor.class, "motorLeftRear");
        motorRightFront = masterConfig.get(DcMotor.class, "motorRightFront");
        motorRightRear = masterConfig.get(DcMotor.class, "motorRightRear");

        //Set the Direction of the Motors on the right side AFTER THOROUGH INSPECTION of electrics
        motorLeftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        motorLeftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        //motorRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        //motorRightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        //motorCollect = masterConfig.get(DcMotor.class, "motorCollect");

        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION); // This will reset the Position to Zero !!!!!!
        motorExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        dropBeaconServo = masterConfig.servo.get("DropBeaconServo");
        dropBeaconServo.setPosition(0.5);
    }

    public void lower() {

        while (!lower) {
            //IMPORTANT remove encoder reset etc from lower methods or you will reset poistions every time in the methods and won't be able to keep track
            //as an alternative keep a global variable for positions for each motor

            robottelemetry.addData("Lift Current Position",  "Target :%7d", motorLift.getCurrentPosition());
            encoderMoveLift(10200, 1, 5);
            robottelemetry.update();
            robottelemetry.addData("Extender Position",  "Target :%7d", motorExtend.getCurrentPosition());
            encoderExtender(-2100, 1, 5);
            //Unhtch servo here
            do {
                hitchServo.setPosition(1);
            } while (hitchServo.getPosition() != 1);
            sleep(1000);
            //Move lift to horizontal (make sure position is not Reset, this is a differential from current position (9200) relative to where we started at 0
            encoderMoveLift(-7200,1,5);
            robottelemetry.addData("Lift Current Position",  "Target :%7d", motorLift.getCurrentPosition());
            //encoderExtender(700, 1, 5);
            robottelemetry.addData("Extender Position",  "Target :%7d", motorExtend.getCurrentPosition());
            //Lower for drive
            robottelemetry.update();
            lower = true;
           // encoderTurn(DRIVE_SPEED, 170, 110, 5);

//            motorLift.setPower(1);
//            sleep(3500);
//            motorLift.setPower(0);
//            motorExtend.setPower(-1);
//            sleep(700);
//            motorExtend.setPower(0);
//
//            hitchServo.setPower(1);
//            sleep(2000);
//            hitchServo.setPower(0);
        }
       // lower = true;
    }

    public void MoveTillEnd() {

        //encoderMoveLift(-2000,1,5);
        encoderDriveForwardorBackwards(DRIVE_SPEED, 700, 5);
        //Extend for crater
        encoderExtender(-4000, 1, 5);

//            motorLeftFront.setPower(+0.5);
//            motorLeftRear.setPower(+0.5);
//            motorRightFront.setPower(+0.5);
//            motorRightRear.setPower(+0.5);
//            telemetry.addLine("Forward");
//            sleep(1550);
//            motorLeftFront.setPower(+0);
//            motorLeftRear.setPower(+0);
//            motorRightFront.setPower(+0);
//            motorRightRear.setPower(+0);
//            hitchServo.setPower(-1);
//            sleep(2000);
//            hitchServo.setPower(0);
//            motorLift.setPower(-1);
//            sleep(2050);
//            motorLift.setPower(0);
//            motorExtend.setPower(-1);
//            sleep(750);
//            motorExtend.setPower(0);
    }

    public void CORdrop(){

        encoderDriveForwardorBackwards(DRIVE_SPEED, 950, 7);
        encoderTurn(TURN_SPEED, -600, 600, 5);
        DropBeacon(5);

//        motorLeftFront.setPower(+0.5);
//        motorLeftRear.setPower(+0.5);
//        motorRightFront.setPower(+0.5);
//        motorRightRear.setPower(+0.5);
//        telemetry.addLine("Forward");
//        sleep(2600);
//        motorLeftFront.setPower(+1);
//        motorLeftRear.setPower(+1);
//        motorRightFront.setPower(-1);
//        motorRightRear.setPower(-1);
//        sleep(15);
//        DropBeacon();
//        hitchServo.setPower(-1);
//        sleep(2000);

    }

    public void CORDrive(){
        encoderTurn(TURN_SPEED, -750, 750, 5);
        encoderTurn(DRIVE_SPEED, 1800, 1800, 5);
//        motorLeftFront.setPower(1);
//        motorLeftRear.setPower(1);
//        motorRightFront.setPower(1);
//        motorRightRear.setPower(1);
//        sleep(2300);
//        stop();
        encoderExtender(-4000, 1, 5);
    }

    public void stopRobot(){
        motorRightFront.setPower(0);
        motorRightRear.setPower(0);
        motorLeftFront.setPower(0);
        motorLeftRear.setPower(0);
    }

    public void turnAroundUntilFound() {
        motorRightFront.setPower(0.2);
        motorRightRear.setPower(0.2);
        motorLeftFront.setPower(-0.2);
        motorLeftRear.setPower(-0.2);
    }

    public void MoveL(){
        encoderTurn(TURN_SPEED, 160, 640, 5);
        Sample();
    }

    public void MoveR(){
        encoderTurn(TURN_SPEED, 640, 160, 5);
        Sample();
    }

    public void MoveM(){
        encoderTurn(DRIVE_SPEED, 300, 300, 5);
        Sample();
    }

    public void CorFromL(){
        encoderTurn(DRIVE_SPEED, 800, 150, 5);
        encoderTurn(DRIVE_SPEED, 50, 600, 5);
        DropBeacon(5);
        encoderTurn(TURN_SPEED, -580, 580, 5);
        encoderTurn(DRIVE_SPEED,1500, 1420, 5);
        Sample();
//        encoderTurn(TURN_SPEED, 1500, -1500, 5);
//        encoderTurn(DRIVE_SPEED, 800, 800, 5);
//        encoderTurn(TURN_SPEED, 400, -400, 5);
//        encoderTurn(DRIVE_SPEED, 1500, 1500, 5);
//        encoderExtender(1000, 1, 5);
    }

    public void CorFromR(){
        encoderTurn(DRIVE_SPEED, 150, 800, 5);
        encoderTurn(TURN_SPEED, 600, 50, 5);
        DropBeacon(5);
        encoderTurn(TURN_SPEED, -580, 580, 5);
        encoderTurn(DRIVE_SPEED,1750, 1750, 5);
        Sample();
//        encoderTurn(DRIVE_SPEED, -1000, -1000, 5);
//        encoderTurn(DRIVE_SPEED, -1500, 1500, 5);
//        encoderTurn(DRIVE_SPEED, 500, 500, 5);
//        encoderExtender(1000, 1, 5);
    }

    public void CorFromM(){
        encoderTurn(DRIVE_SPEED, 100, 200, 5);
        encoderTurn(DRIVE_SPEED, 750, 1050, 5);
        DropBeacon(5);
        encoderTurn(TURN_SPEED, -580, 580, 5);
        encoderTurn(DRIVE_SPEED,1750, 1750, 5);
        Sample();
//        encoderTurn(DRIVE_SPEED, -600, -450, 5);
//        encoderTurn(DRIVE_SPEED, 0, -150, 5);
//        encoderTurn(DRIVE_SPEED, -1000, -1000, 5);
//        encoderTurn(DRIVE_SPEED, -1500, 1500, 5);
//        encoderTurn(DRIVE_SPEED, 500, 500, 5);
//        encoderExtender(1000, 1, 5);
    }

    public void CraFromL(){
        encoderTurn(DRIVE_SPEED, 200, 200, 5);
        Sample();
    }

    public void CraFromR(){
        encoderTurn(DRIVE_SPEED, 200, 200, 5);
        Sample();
    }

    public void CraFromM(){
        encoderTurn(DRIVE_SPEED, 100, 100, 5);
        Sample();
    }

    public void Sample(){
        encoderMoveLift(-2450, 1, 5);
        encoderExtender(-2550, 1, 5);
        motorCollect.setPower(1);
        encoderMoveLift(3000, 1, 5);
        motorCollect.setPower(0);
    }

    public void DropBeacon(double timeoutS){

        robottelemetry.addData("Beacon Drop", "Initialized");
        robottelemetry.update();
        runtime.reset();
        //dropBeaconServo.setPosition(0);
        robottelemetry.addData("Reset the Beacon",dropBeaconServo.getPosition());

        dropBeaconServo.setDirection(Servo.Direction.REVERSE);
        dropBeaconServo.setPosition(0.5);

        dropBeaconServo.setPosition(0.90);
        while (opModeIsActive()) {
            //WE don't have servo feedback
            robottelemetry.addData("Drop the Beacon and wait",dropBeaconServo.getPosition());
            sleep(1000);
            robottelemetry.update();
            //if(robot.dropBeaconServo.getPosition()>0.89)
            break;

        }

        dropBeaconServo.setPosition(0.5);
        robottelemetry.addData("Drop the Beacon",dropBeaconServo.getPosition());

        robottelemetry.update();

    }

    public void encoderDriveForwardorBackwards(double speed, double distanceMM, double timeoutS) {
        int newLeftFrontTarget;
        int newLeftRearTarget;
        int newRightFrontTarget;
        int newRightRearTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            //We use Tank Drive in all 4 wheels, so make sure we sent the correct signals to both Left and Right wheels
            // Determine new target position, and pass to motor controller
            newLeftFrontTarget = motorLeftFront.getCurrentPosition() + (int)(distanceMM * COUNTS_PER_MM);
            newLeftRearTarget = motorLeftRear.getCurrentPosition() + (int)(distanceMM * COUNTS_PER_MM);
            newRightFrontTarget = motorRightFront.getCurrentPosition() + (int)(distanceMM * COUNTS_PER_MM);
            newRightRearTarget = motorRightRear.getCurrentPosition() + (int)(distanceMM * COUNTS_PER_MM);

            motorLeftFront.setTargetPosition(newLeftFrontTarget);
            motorLeftRear.setTargetPosition(newLeftRearTarget);
            motorRightFront.setTargetPosition(newRightFrontTarget);
            motorRightRear.setTargetPosition(newRightRearTarget);

//            // Turn On RUN_TO_POSITION
//            motorLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            motorLeftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            motorRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            motorRightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            motorLeftFront.setPower(Math.abs(speed));
            motorRightFront.setPower(Math.abs(speed));

            motorLeftRear.setPower(Math.abs(speed));
            motorRightRear.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits, we should monitor ALL 4
            //but assume for noe only 2 or monitor 2
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH (actually all 4)  motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
//            while (opModeIsActive() &&
//                    (runtime.seconds() < timeoutS) &&
//                    (robot.motorLeftFront.isBusy() && robot.motorRightFront.isBusy()))

            //Only monitor the Back wheels
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motorLeftRear.isBusy() ||  motorRightRear.isBusy()))
//            while (opModeIsActive() &&
//                    (runtime.seconds() < timeoutS) &&
//                    (robot.motorLeftRear.isBusy() && robot.motorRightRear.isBusy()))
            {

                // Display it for the Debugging.
                //telemetry.addData("Path1",  "Running to Target LF,LR, RF, RR %7d :%7d", newLeftFrontTarget,  newRightFrontTarget);
                //telemetry.addData("Path2",  "Running at %7d :%7d",robot.motorLeftFront.getCurrentPosition(),robot.motorRightFront.getCurrentPosition());
                robottelemetry.addData("Path1",  "Running to Target LF,LR, RF, RR %7d :%7d :%7d :%7d", newLeftFrontTarget,  newLeftRearTarget, newRightFrontTarget,newRightRearTarget);
//                telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d",
//                telemetry.addData("Path1",  "Running to Target LR, RR %7d :%7d", newLeftRearTarget, newRightRearTarget);
//                telemetry.addData("Path2",  "Running at %7d :%7d",
//                        robot.motorLeftRear.getCurrentPosition(),robot.motorRightRear.getCurrentPosition());
                robottelemetry.update();
            }

            // Stop all motion after Path is completed;
            motorLeftFront.setPower(0);
            motorLeftRear.setPower(0);

            motorRightFront.setPower(0);
            motorRightRear.setPower(0);
            // Turn off RUN_TO_POSITION
            motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void encoderTurn(double speed, double leftMMdistance, double rightMMdistance, double timeoutS){
        int newLeftFrontTarget;
        int newLeftRearTarget;
        int newRightFrontTarget;
        int newRightRearTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            //We use Tank Drive in all 4 wheels, so make sure we sent the correct signals to both Left and Right wheels
            // Determine new target position, and pass to motor controller
            newLeftFrontTarget = motorLeftFront.getCurrentPosition() + (int)(leftMMdistance * COUNTS_PER_MM);
            newLeftRearTarget = motorLeftRear.getCurrentPosition() + (int)(leftMMdistance * COUNTS_PER_MM);
            newRightFrontTarget = motorRightFront.getCurrentPosition() + (int)(rightMMdistance * COUNTS_PER_MM);
            newRightRearTarget = motorRightRear.getCurrentPosition() + (int)(rightMMdistance * COUNTS_PER_MM);

            motorLeftFront.setTargetPosition(newLeftFrontTarget);
            motorLeftRear.setTargetPosition(newLeftRearTarget);
            motorRightFront.setTargetPosition(newRightFrontTarget);
            motorRightRear.setTargetPosition(newRightRearTarget);

            // Turn On RUN_TO_POSITION
            motorLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorLeftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorRightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            motorLeftFront.setPower(Math.abs(speed));
            motorRightFront.setPower(Math.abs(speed));

            motorLeftRear.setPower(Math.abs(speed));
            motorRightRear.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            //Only monitor the Back wheels
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motorLeftRear.isBusy() ||  motorRightRear.isBusy()))
            {

                // Display it for the Debugging.
                robottelemetry.addData("Path1",  "Running to Target LF,LR, RF, RR %7d :%7d :%7d :%7d", newLeftFrontTarget,  newLeftRearTarget, newRightFrontTarget,newRightRearTarget);
                robottelemetry.update();
            }

            robottelemetry.addData("Power Reset","Power set to 0");
            robottelemetry.update();
            // Stop all motion after Path is completed;
            motorLeftFront.setPower(0);
            motorLeftRear.setPower(0);

            motorRightFront.setPower(0);
            motorRightRear.setPower(0);
            // Turn off RUN_TO_POSITION
            motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    //Use the Rev4wheel Telop to get the Values for various positions
    private void encoderMoveLift(int position, double speed,double timeoutS)
    {
        int newLiftget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLiftget = motorLift.getCurrentPosition() + position;//+ (int)(distanceMM * COUNTS_PER_MM);

            motorLift.setTargetPosition(newLiftget);
            //// Turn On RUN_TO_POSITION
            //motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // reset the timeout time and start motion.
            runtime.reset();
            motorLift.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits, we should monitor ALL 4
            //but assume for noe only 2 or monitor 2
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH (actually all 4)  motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motorLift.isBusy() ))
            {

                // Display it for the Debugging.
                robottelemetry.addData("Lift Path",  "Running to Target :%7d", newLiftget);
                robottelemetry.update();
            }

            // Stop all motion after Path is completed;
            motorLift.setPower(0);
            // Turn off RUN_TO_POSITION
            //motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }

    //Use the Rev4wheel Telop to get the Values for various positions
    private void encoderExtender(int position,double speed, double timeoutS)
    {
        int newExtendget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newExtendget = motorExtend.getCurrentPosition() + position ;//+ (int)(distanceMM * COUNTS_PER_MM);


            motorExtend.setTargetPosition(newExtendget);
            // Turn On RUN_TO_POSITION
//            motorExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // reset the timeout time and start motion.
            runtime.reset();
            motorExtend.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits, we should monitor ALL 4
            //but assume for noe only 2 or monitor 2
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH (actually all 4)  motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motorExtend.isBusy() ))
            {

                // Display it for the Debugging.
                robottelemetry.addData("Extender Path",  "Running to Target :%7d", position);
                robottelemetry.update();
            }

            // Stop all motion after Path is completed;
            motorExtend.setPower(0);
            // Turn off RUN_TO_POSITION
            //motorExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }

    public void encoderStrafe(double speed, double pair1, double pair2, double timeoutS)
    {
        int newLeftFrontTarget;
        int newLeftRearTarget;
        int newRightFrontTarget;
        int newRightRearTarget;

        newLeftFrontTarget = motorLeftFront.getCurrentPosition() + (int)(pair1 * COUNTS_PER_MM);
        newLeftRearTarget = motorLeftRear.getCurrentPosition() + (int)(pair2 * COUNTS_PER_MM);
        newRightFrontTarget = motorRightFront.getCurrentPosition() + (int)(pair2 * COUNTS_PER_MM);
        newRightRearTarget = motorRightRear.getCurrentPosition() + (int)(pair1 * COUNTS_PER_MM);

        motorLeftFront.setTargetPosition(newLeftFrontTarget);
        motorLeftRear.setTargetPosition(newLeftRearTarget);
        motorRightFront.setTargetPosition(newRightFrontTarget);
        motorRightRear.setTargetPosition(newRightRearTarget);

        // Turn On RUN_TO_POSITION
        motorLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        motorLeftFront.setPower(Math.abs(speed));
        motorRightFront.setPower(Math.abs(speed));

        motorLeftRear.setPower(Math.abs(speed));
        motorRightRear.setPower(Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        //Only monitor the Back wheels
        while (opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (motorLeftRear.isBusy() &&  motorRightRear.isBusy()))
        {

            // Display it for the Debugging.
            robottelemetry.addData("Path1",  "Running to Target LF,LR, RF, RR %7d :%7d :%7d :%7d", newLeftFrontTarget,  newLeftRearTarget, newRightFrontTarget,newRightRearTarget);
            robottelemetry.update();
        }

        // Stop all motion after Path is completed;
        motorLeftFront.setPower(0);
        motorLeftRear.setPower(0);

        motorRightFront.setPower(0);
        motorRightRear.setPower(0);
        // Turn off RUN_TO_POSITION
        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public Telemetry getRobottelemetry() {
        return robottelemetry;
    }

    public void setRobottelemetry(Telemetry robottelemetry) {
        this.robottelemetry = robottelemetry;
    }
}
