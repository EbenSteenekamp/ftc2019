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
    public CRServo hitchServo;
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

    static final double     DRIVE_SPEED             = 0.5;
    static final double     TURN_SPEED              = 0.5;
    static final double     STRAFE_SPEED            = 0.5;

    public CloneAutoOpRobot() {

    }

    @Override
    public void runOpMode(){

    }

    public void init(HardwareMap amasterConfig) {
        masterConfig = amasterConfig;

        hitchServo = masterConfig.crservo.get("hitchServo");
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

        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        dropBeaconServo = masterConfig.servo.get("DropBeaconServo");
        dropBeaconServo.setPosition(0.5);
    }

    public void lower() {

        if (lower == false) {

            encoderExtend(0.5, 7000, 5);
            encoderLift(0.5, 1000, 5);
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
        lower = true;
    }
    public void MoveTillEnd() {
//        motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        double driveDistance1 = 200 * COUNTS_PER_MM;
//        double startPos1 = motorRightFront.getCurrentPosition();
//        while (motorRightFront.getCurrentPosition() < driveDistance1 + startPos1) {
//            motorLeftFront.setPower(+0.5);
//            motorLeftRear.setPower(+0.5);
//            motorRightFront.setPower(+0.5);
//            motorRightRear.setPower(+0.5);
//            telemetry.addLine("Forward");
//        }

            motorLeftFront.setPower(+0.5);
            motorLeftRear.setPower(+0.5);
            motorRightFront.setPower(+0.5);
            motorRightRear.setPower(+0.5);
            telemetry.addLine("Forward");
            sleep(1550);
            motorLeftFront.setPower(+0);
            motorLeftRear.setPower(+0);
            motorRightFront.setPower(+0);
            motorRightRear.setPower(+0);
            hitchServo.setPower(-1);
            sleep(2000);
            hitchServo.setPower(0);
            motorLift.setPower(-1);
            sleep(2050);
            motorLift.setPower(0);
            motorExtend.setPower(-1);
            sleep(750);
            motorExtend.setPower(0);
    }

    public void FVDrop(){
        motorLeftFront.setPower(+0.5);
        motorLeftRear.setPower(+0.5);
        motorRightFront.setPower(+0.5);
        motorRightRear.setPower(+0.5);
        telemetry.addLine("Forward");
        sleep(2600);
        motorLeftFront.setPower(+1);
        motorLeftRear.setPower(+1);
        motorRightFront.setPower(-1);
        motorRightRear.setPower(-1);
        sleep(15);
        DropBeacon();
        hitchServo.setPower(-1);
        sleep(2000);

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
        encoderDriveForwardorBackwards(DRIVE_SPEED, 100, 5);
//        telemetry.addData("Status", "Resetting Encoders");    //
//        telemetry.update();
//        motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        double driveDistance = 100 * COUNTS_PER_MM;
//        double startPos = motorRightFront.getCurrentPosition();
//        while (motorRightFront.getCurrentPosition() < driveDistance + startPos) {
//            motorLeftFront.setPower(+1);
//            motorLeftRear.setPower(-1);
//            motorRightFront.setPower(+1);
//            motorRightRear.setPower(-1);
//            telemetry.addLine("Move Left");
//        }
    }

    public void ResetLeft(){
        encoderDriveForwardorBackwards(DRIVE_SPEED, 100, 5);
//        motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        double driveDistance1 = 100 * COUNTS_PER_MM;
//        double startPos1 = motorRightFront.getCurrentPosition();
//        while (motorRightFront.getCurrentPosition() < driveDistance1 + startPos1) {
//            motorLeftFront.setPower(-1);
//            motorLeftRear.setPower(-1);
//            motorRightFront.setPower(-1);
//            motorRightRear.setPower(-1);
//            telemetry.addLine("Repo");
//        }
//
//        double driveDistance2 = 100 * COUNTS_PER_MM;
//        double startPos2 = motorRightFront.getCurrentPosition();
//        while (motorRightFront.getCurrentPosition() < driveDistance2 + startPos2) {
//            motorLeftFront.setPower(-1);
//            motorLeftRear.setPower(+1);
//            motorRightFront.setPower(-1);
//            motorRightRear.setPower(+1);
//            telemetry.addLine("Move Left");
//        }
    }

    public void MoveR(){
        motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double driveDistance = 100 * COUNTS_PER_MM;
        double startPos = motorRightFront.getCurrentPosition();
        while (motorRightFront.getCurrentPosition() < driveDistance + startPos) {
            motorLeftFront.setPower(-1);
            motorLeftRear.setPower(+1);
            motorRightFront.setPower(-1);
            motorRightRear.setPower(+1);
            telemetry.addLine("Move Right");
        }
    }

    public void ResetRight(){
        motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double driveDistance1 = 100 * COUNTS_PER_MM;
        double startPos1 = motorRightFront.getCurrentPosition();
        while (motorRightFront.getCurrentPosition() < driveDistance1 + startPos1) {
            motorLeftFront.setPower(-1);
            motorLeftRear.setPower(-1);
            motorRightFront.setPower(-1);
            motorRightRear.setPower(-1);
            telemetry.addLine("Repo");
        }

        double driveDistance2 = 100 * COUNTS_PER_MM;
        double startPos2 = motorRightFront.getCurrentPosition();
        while (motorRightFront.getCurrentPosition() < driveDistance2 + startPos2) {
            motorLeftFront.setPower(+1);
            motorLeftRear.setPower(-1);
            motorRightFront.setPower(+1);
            motorRightRear.setPower(-1);
            telemetry.addLine("Move Right");
        }
    }

    public void Sample(){
        motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double driveDistance = 100 * COUNTS_PER_MM;
        double startPos = motorRightFront.getCurrentPosition();
        while (motorRightFront.getCurrentPosition() < driveDistance + startPos) {
            motorLeftFront.setPower(+1);
            motorLeftRear.setPower(+1);
            motorRightFront.setPower(+1);
            motorRightRear.setPower(+1);
            telemetry.addLine("Sample");
        }
    }

    public void ResetMid(){
        motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double driveDistance = 100 * COUNTS_PER_MM;
        double startPos = motorRightFront.getCurrentPosition();
        while (motorRightFront.getCurrentPosition() < driveDistance + startPos) {
            motorLeftFront.setPower(-1);
            motorLeftRear.setPower(-1);
            motorRightFront.setPower(-1);
            motorRightRear.setPower(1);
            telemetry.addLine("Reset");
        }
    }

    public void MoveFromCrator(){
        motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double driveDistance1 = 1400 * COUNTS_PER_MM;
        double startPos1 = motorRightFront.getCurrentPosition();
        while (motorRightFront.getCurrentPosition() < driveDistance1 + startPos1) {
            motorLeftFront.setPower(+1);
            motorLeftRear.setPower(+1);
            motorRightFront.setPower(+1);
            motorRightRear.setPower(+1);
            telemetry.addLine("Forward");
        }

        double driveDistance2 = 100 * COUNTS_PER_MM;
        double startPos2 = motorRightFront.getCurrentPosition();
        while (motorRightFront.getCurrentPosition() < driveDistance2 + startPos2) {
            motorLeftFront.setPower(-1);
            motorLeftRear.setPower(-1);
            motorRightFront.setPower(+1);
            motorRightRear.setPower(+1);
            telemetry.addLine("Turn");
        }

        double driveDistance3 = 1200 * COUNTS_PER_MM;
        double startPos3 = motorRightFront.getCurrentPosition();
        while (motorRightFront.getCurrentPosition() < driveDistance3 + startPos3) {
            motorLeftFront.setPower(+1);
            motorLeftRear.setPower(+1);
            motorRightFront.setPower(+1);
            motorRightRear.setPower(+1);
            telemetry.addLine("Forward");
        }

        double driveDistance4 = 100 * COUNTS_PER_MM;
        double startPos4 = motorRightFront.getCurrentPosition();
        while (motorRightFront.getCurrentPosition() < driveDistance4 + startPos4) {
            motorLeftFront.setPower(+1);
            motorLeftRear.setPower(+1);
            motorRightFront.setPower(-1);
            motorRightRear.setPower(-1);
            telemetry.addLine("Turn");
        }

        DropBeacon();

        double driveDistance5 = 1880 * COUNTS_PER_MM;
        double startPos5 = motorRightFront.getCurrentPosition();
        while (motorRightFront.getCurrentPosition() < driveDistance5 + startPos5) {
            motorLeftFront.setPower(+1);
            motorLeftRear.setPower(+1);
            motorRightFront.setPower(+1);
            motorRightRear.setPower(+1);
            telemetry.addLine("Turn");
        }
    }

    public void MoveFromCorner(){
        motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double driveDistance3 = 1400 * COUNTS_PER_MM;
        double startPos3 = motorRightFront.getCurrentPosition();
        while (motorRightFront.getCurrentPosition() < driveDistance3 + startPos3) {
            motorLeftFront.setPower(+1);
            motorLeftRear.setPower(+1);
            motorRightFront.setPower(+1);
            motorRightRear.setPower(+1);
            telemetry.addLine("Forward");
        }

        double driveDistance4 = 100 * COUNTS_PER_MM;
        double startPos4 = motorRightFront.getCurrentPosition();
        while (motorRightFront.getCurrentPosition() < driveDistance4 + startPos4) {
            motorLeftFront.setPower(+1);
            motorLeftRear.setPower(+1);
            motorRightFront.setPower(-1);
            motorRightRear.setPower(-1);
            telemetry.addLine("Turn");
        }

        DropBeacon();

        double driveDistance5 = 1880 * COUNTS_PER_MM;
        double startPos5 = motorRightFront.getCurrentPosition();
        while (motorRightFront.getCurrentPosition() < driveDistance5 + startPos5) {
            motorLeftFront.setPower(+1);
            motorLeftRear.setPower(+1);
            motorRightFront.setPower(+1);
            motorRightRear.setPower(+1);
            telemetry.addLine("Turn");
        }
    }

    public void DropBeacon(){
        dropBeaconServo.setPosition(0.90);
        telemetry.addData("Drop the Beacon",dropBeaconServo.getPosition());

        dropBeaconServo.setPosition(0.5);
        telemetry.addData("Drop the Beacon",dropBeaconServo.getPosition());
    }

    public void encoderDriveForwardorBackwards(double speed,
                                               double distanceMM,
                                               double timeoutS) {
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
                    (motorLeftRear.isBusy() &&  motorRightRear.isBusy()))
//            while (opModeIsActive() &&
//                    (runtime.seconds() < timeoutS) &&
//                    (robot.motorLeftRear.isBusy() && robot.motorRightRear.isBusy()))
            {

                // Display it for the Debugging.
                //telemetry.addData("Path1",  "Running to Target LF,LR, RF, RR %7d :%7d", newLeftFrontTarget,  newRightFrontTarget);
                //telemetry.addData("Path2",  "Running at %7d :%7d",robot.motorLeftFront.getCurrentPosition(),robot.motorRightFront.getCurrentPosition());
                telemetry.addData("Path1",  "Running to Target LF,LR, RF, RR %7d :%7d :%7d :%7d", newLeftFrontTarget,  newLeftRearTarget, newRightFrontTarget,newRightRearTarget);
//                telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d",
//                telemetry.addData("Path1",  "Running to Target LR, RR %7d :%7d", newLeftRearTarget, newRightRearTarget);
//                telemetry.addData("Path2",  "Running at %7d :%7d",
//                        robot.motorLeftRear.getCurrentPosition(),robot.motorRightRear.getCurrentPosition());
                telemetry.update();
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
                    (motorLeftRear.isBusy() &&  motorRightRear.isBusy()))
            {

                // Display it for the Debugging.
                telemetry.addData("Path1",  "Running to Target LF,LR, RF, RR %7d :%7d :%7d :%7d", newLeftFrontTarget,  newLeftRearTarget, newRightFrontTarget,newRightRearTarget);
                telemetry.update();
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

    public void encoderStrafe(double speed, double pair1, double pair2, double timeoutS){
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
            telemetry.addData("Path1",  "Running to Target LF,LR, RF, RR %7d :%7d :%7d :%7d", newLeftFrontTarget,  newLeftRearTarget, newRightFrontTarget,newRightRearTarget);
            telemetry.update();
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

    public void encoderLift(double speed, double LiftMM, double timeoutS){
        int newLiftTarget;

        newLiftTarget = motorLift.getCurrentPosition() + (int)(LiftMM * COUNTS_PER_MM);
        motorLift.setTargetPosition(newLiftTarget);

        // Turn On RUN_TO_POSITION
        motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        motorLift.setPower(Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        //Only monitor the Back wheels
        while (opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (motorLift.isBusy()))
        {

            // Display it for the Debugging.
            telemetry.addData("Path1",  "Running to Target LI, :%7d", newLiftTarget);
            telemetry.update();
        }

        motorLift.setPower(0);

        // Turn off RUN_TO_POSITION
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void encoderExtend(double speed, double ExtendMM, double timeoutS){
        int newExtendTarget;

        newExtendTarget = motorExtend.getCurrentPosition() + (int)(ExtendMM * COUNTS_PER_MM);
        motorExtend.setTargetPosition(newExtendTarget);

        // Turn On RUN_TO_POSITION
        motorExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        motorExtend.setPower(Math.abs(speed));

        while (opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (motorExtend.isBusy()))
        {

            // Display it for the Debugging.
            telemetry.addData("Path1",  "Running to Target EX, %7d ", newExtendTarget);
            telemetry.update();
        }

        // Stop all motion after Path is completed;
        motorExtend.setPower(0);

        // Turn off RUN_TO_POSITION
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
