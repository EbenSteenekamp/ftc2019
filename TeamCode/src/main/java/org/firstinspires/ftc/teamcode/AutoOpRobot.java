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

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

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

    boolean lower = false;

    HardwareMap masterConfig = null;
    private ElapsedTime period = new ElapsedTime();

    public AutoOpRobot() {

    }

    @Override
    public void runOpMode() throws InterruptedException {

    }

    public void init(HardwareMap amasterConfig) {
        masterConfig = amasterConfig;

        hitchServo = masterConfig.crservo.get("hitchServo");
        motorExtend = masterConfig.get(DcMotor.class, "motorExtend");
        motorLift = masterConfig.get(DcMotor.class, "motorLift");
        motorLeftFront = masterConfig.get(DcMotor.class, "motorLeftFront");
        motorLeftRear = masterConfig.get(DcMotor.class, "motorLeftRear");
        motorLeftFront.setDirection(DcMotor.Direction.FORWARD);
        motorLeftRear.setDirection(DcMotor.Direction.FORWARD);
        motorRightFront = masterConfig.get(DcMotor.class, "motorRightFront");
        motorRightRear = masterConfig.get(DcMotor.class, "motorRightRear");
        motorLeftFront.setDirection(DcMotor.Direction.REVERSE);
        motorLeftRear.setDirection(DcMotor.Direction.REVERSE);
        motorCollect = masterConfig.get(DcMotor.class, "motorCollect");
    }

    public void lower() {

        if (lower == false) {
            motorLift.setPower(1);
            motorLift.setPower(0.75);
            sleep(1300);

            motorExtend.setPower(0.5);
            sleep(70);
            motorExtend.setPower(0);

            hitchServo.setPower(1);
            sleep(1900);
            hitchServo.setPower(0);

            telemetry.addLine("Lower");
        }
        lower = true;
    }

  /*  public void Stop() {
        motorLeftRear.setPower(0);
        motorLeftFront.setPower(0);
        motorRightRear.setPower(0);
        motorRightFront.setPower(0);
        telemetry.addLine("Stop");
    }
*/
    public void turnAroundUntilFound() {
        motorRightFront.setPower(0.1);
        motorRightRear.setPower(0.1);
        motorLeftFront.setPower(-0.1);
        motorLeftRear.setPower(-0.1);
        telemetry.addLine("Turn Around");
    }

    public void MoveL(){
        motorLeftFront.setPower(-0.1);
        motorLeftRear.setPower(+0.1);
        motorRightFront.setPower(+0.1);
        motorRightRear.setPower(-0.1);
        telemetry.addLine("Move Left");
        sleep(500);
    }

    public void MoveR(){
        motorLeftFront.setPower(+0.1);
        motorLeftRear.setPower(-0.1);
        motorRightFront.setPower(-0.1);
        motorRightRear.setPower(+0.1);
        telemetry.addLine("Move Right");
        sleep(500);
    }

    public void Sample(){
        motorLeftFront.setPower(+0.1);
        motorLeftRear.setPower(+0.1);
        motorRightFront.setPower(+0.1);
        motorRightRear.setPower(+0.1);
        telemetry.addLine("Sample");
        sleep(500);
    }

    public void MoveFromCrator(){
        motorLeftFront.setPower(1);
        motorLeftRear.setPower(1);
        motorRightRear.setPower(1);
        motorRightFront.setPower(1);
        sleep(1000);

        motorRightFront.setPower(0.1);
        motorRightRear.setPower(0.1);
        motorLeftFront.setPower(-0.1);
        motorLeftRear.setPower(-0.1);
        sleep(700);

        motorLeftFront.setPower(1);
        motorLeftRear.setPower(1);
        motorRightRear.setPower(1);
        motorRightFront.setPower(1);
        sleep(2000);

        //drop beacon
        motorRightFront.setPower(0.5);
        motorRightRear.setPower(0.5);
        motorLeftFront.setPower(-0.5);
        motorLeftRear.setPower(-0.5);


        motorLeftFront.setPower(1);
        motorLeftRear.setPower(1);
        motorRightRear.setPower(1);
        motorRightFront.setPower(1);
        sleep(2000);
    }

    public void MoveFromCorner(){
        motorLeftFront.setPower(1);
        motorLeftRear.setPower(1);
        motorRightRear.setPower(1);
        motorRightFront.setPower(1);
        sleep(1000);

        motorRightFront.setPower(0.1);
        motorRightRear.setPower(0.1);
        motorLeftFront.setPower(-0.1);
        motorLeftRear.setPower(-0.1);

        motorLeftFront.setPower(1);
        motorLeftRear.setPower(1);
        motorRightRear.setPower(1);
        motorRightFront.setPower(1);
        sleep(1000);
    }
}