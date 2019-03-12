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

public class AutoOpRobot extends LinearOpMode {

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

    HardwareMap masterConfig = null;
    private ElapsedTime period = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 28 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 40 ;     // This is < 1.0 if geared UP 40:1 reduce to 160 rpm
    static final double     WHEEL_DIAMETER_MM   = 100 ;     // For figuring circumference
    static final double     COUNTS_PER_MM         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                    (WHEEL_DIAMETER_MM * 3.1415);

    public AutoOpRobot() {

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

        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        dropBeaconServo = masterConfig.servo.get("DropBeaconServo");
        dropBeaconServo.setPosition(0.5);
    }

    public void lower() {

        if (lower == false) {

//            motorExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            motorExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//            double driveDistance2 = 5 * COUNTS_PER_MM;
//            double startPos2 = motorLift.getCurrentPosition();
//            while (motorLift.getCurrentPosition() < driveDistance2 + startPos2) {
//                motorLift.setPower(1);
//            }
//            double driveDistance1 = 50 * COUNTS_PER_MM;
//            double startPos1 = motorExtend.getCurrentPosition();
//            while (motorExtend.getCurrentPosition() < driveDistance1 + startPos1) {
//                motorExtend.setPower(1);
//            }
//            hitchServo.setPower(1);
//            sleep(2000);
//            hitchServo.setPower(0);

            motorLift.setPower(1);
            sleep(3500);
            motorLift.setPower(0);
            motorExtend.setPower(-1);
            sleep(700);
            motorExtend.setPower(0);

            hitchServo.setPower(1);
            sleep(2000);
            hitchServo.setPower(0);
        }
        lower = true;
    }
    public void MoveTillEnd(){
        motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double driveDistance1 = 1200 * COUNTS_PER_MM;
        double startPos1 = motorRightFront.getCurrentPosition();
        while (motorRightFront.getCurrentPosition() < driveDistance1 + startPos1) {
            motorLeftFront.setPower(+1);
            motorLeftRear.setPower(+1);
            motorRightFront.setPower(+1);
            motorRightRear.setPower(+1);
            telemetry.addLine("Forward");
        }

        motorLift.setPower(-1);
        sleep(2000);
        motorLift.setPower(0);
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
            motorLeftRear.setPower(-1);
            motorRightFront.setPower(+1);
            motorRightRear.setPower(-1);
            telemetry.addLine("Move Left");
        }
    }

    public void ResetLeft(){
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
            motorLeftFront.setPower(-1);
            motorLeftRear.setPower(+1);
            motorRightFront.setPower(-1);
            motorRightRear.setPower(+1);
            telemetry.addLine("Move Left");
        }
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
}