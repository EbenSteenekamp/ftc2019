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
    //public DcMotor motorCollect;
    //public DcMotor motorLift;
    //public DcMotor motorExtend;
    //public CRServo hitchServo;
    public Servo dropBeaconServo;

    boolean lower = false;

    HardwareMap masterConfig = null;
    private ElapsedTime period = new ElapsedTime();

    public AutoOpRobot() {

    }

    @Override
    public void runOpMode(){

    }

    public void init(HardwareMap amasterConfig) {
        masterConfig = amasterConfig;

        //hitchServo = masterConfig.crservo.get("hitchServo");
        //motorExtend = masterConfig.get(DcMotor.class, "motorExtend");
        //motorLift = masterConfig.get(DcMotor.class, "motorLift");
        motorLeftFront = masterConfig.get(DcMotor.class, "motorLeftFront");
        motorLeftRear = masterConfig.get(DcMotor.class, "motorLeftRear");
        motorRightFront = masterConfig.get(DcMotor.class, "motorRightFront");
        motorRightRear = masterConfig.get(DcMotor.class, "motorRightRear");

        //Set the Direction of the Motors on the right side AFTER THOROUGH INSPECTION of electrics
        motorRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        motorRightRear.setDirection(DcMotorSimple.Direction.REVERSE);
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
           // motorLift.setPower(1);
            sleep(1100);
            //motorExtend.setPower(-1);
            sleep(1350);

            //hitchServo.setPower(1);
            sleep(2100);
            //hitchServo.setPower(0);
        }
        lower = true;
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
        motorLeftFront.setTargetPosition(+1);
        motorLeftRear.setTargetPosition(-1);
        motorRightFront.setTargetPosition(+1);
        motorRightRear.setTargetPosition(-1);
        telemetry.addLine("Move Left");
    }

    public void ResetLeft(){
        motorLeftFront.setTargetPosition(-1);
        motorLeftRear.setTargetPosition(-1);
        motorRightFront.setTargetPosition(-1);
        motorRightRear.setTargetPosition(-1);
        telemetry.addLine("Repo");

        motorLeftFront.setTargetPosition(-1);
        motorLeftRear.setTargetPosition(+1);
        motorRightFront.setTargetPosition(-1);
        motorRightRear.setTargetPosition(+1);
        telemetry.addLine("Move Left");
    }

    public void MoveR(){
        motorLeftFront.setTargetPosition(-1);
        motorLeftRear.setTargetPosition(+1);
        motorRightFront.setTargetPosition(-1);
        motorRightRear.setTargetPosition(+1);
        telemetry.addLine("Move Right");
    }

    public void ResetRight(){
        motorLeftFront.setTargetPosition(-1);
        motorLeftRear.setTargetPosition(-1);
        motorRightFront.setTargetPosition(-1);
        motorRightRear.setTargetPosition(-1);
        telemetry.addLine("Repo");

        motorLeftFront.setTargetPosition(+1);
        motorLeftRear.setTargetPosition(-1);
        motorRightFront.setTargetPosition(+1);
        motorRightRear.setTargetPosition(-1);
        telemetry.addLine("Move Right");
    }

    public void Sample(){
       // motorLift.setPower(-1);
        sleep(500);
        motorLeftFront.setTargetPosition(-1);
        motorLeftRear.setTargetPosition(-1);
        motorRightFront.setTargetPosition(-1);
        motorRightRear.setTargetPosition(-1);
        telemetry.addLine("Sample");
        telemetry.update();
        sleep(50);
        //motorCollect.setPower(1);
        //sleep(500);
        //motorLift.setPower(1);
        //sleep(500);
    }

    public void ResetMid(){
        motorLeftFront.setTargetPosition(-1);
        motorLeftRear.setTargetPosition(-1);
        motorRightFront.setTargetPosition(-1);
        motorRightRear.setTargetPosition(-1);
        telemetry.addLine("Repo");
        sleep(50);
    }

    public void MoveFromCrator(){
        motorLeftFront.setPower(0.5);
        motorLeftFront.setTargetPosition(5);
        motorLeftRear.setPower(0.5);
        motorLeftRear.setTargetPosition(5);
        motorRightFront.setPower(0.5);
        motorRightFront.setTargetPosition(5);
        motorRightRear.setPower(0.5);
        motorRightRear.setTargetPosition(5);
        telemetry.addLine("Move forward");

       /* motorLeftFront.setTargetPosition(2);
        motorLeftRear.setTargetPosition(2);
        motorRightFront.setTargetPosition(-2);
        motorRightRear.setTargetPosition(-2);
        telemetry.addLine("Turn");

        motorLeftFront.setTargetPosition(10);
        motorLeftRear.setTargetPosition(10);
        motorRightFront.setTargetPosition(10);
        motorRightRear.setTargetPosition(10);
        telemetry.addLine("Move forward");

        //drop beacon
        motorLeftFront.setTargetPosition(2);
        motorLeftRear.setTargetPosition(2);
        motorRightFront.setTargetPosition(-2);
        motorRightRear.setTargetPosition(-2);
        telemetry.addLine("Turn");

       DropBeacon();

        motorLeftFront.setTargetPosition(20);
        motorLeftRear.setTargetPosition(20);
        motorRightFront.setTargetPosition(20);
        motorRightRear.setTargetPosition(20);
        telemetry.addLine("Move forward");
        */
    }

    public void MoveFromCorner(){
        motorLeftFront.setTargetPosition(10);
        motorLeftRear.setTargetPosition(10);
        motorRightFront.setTargetPosition(10);
        motorRightRear.setTargetPosition(10);
        telemetry.addLine("Move forward");

        motorLeftFront.setTargetPosition(2);
        motorLeftRear.setTargetPosition(2);
        motorRightFront.setTargetPosition(-2);
        motorRightRear.setTargetPosition(-2);
        telemetry.addLine("Turn");

        DropBeacon();

        motorLeftFront.setTargetPosition(20);
        motorLeftRear.setTargetPosition(20);
        motorRightFront.setTargetPosition(20);
        motorRightRear.setTargetPosition(20);
        telemetry.addLine("Move forward");
    }

    public void DropBeacon(){
        dropBeaconServo.setPosition(0.90);
        telemetry.addData("Drop the Beacon",dropBeaconServo.getPosition());

        dropBeaconServo.setPosition(0.5);
        telemetry.addData("Drop the Beacon",dropBeaconServo.getPosition());
    }
}