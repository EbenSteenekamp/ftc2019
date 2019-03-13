package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "AutoOpDrive", group = "AutoOp")
public class AutoOpDrive extends LinearOpMode {

    boolean lower = false;
    private ElapsedTime runtime = new ElapsedTime();
    CloneAutoOpRobot robot = new CloneAutoOpRobot();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.motorExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        //**************************************************************************************
        //Start the Robot to set the OpMode !!!!!!!!!!!!!!!!
        robot.start();
        //**************************************************************************************
        unhitchRobotMoveToCrater();
//        while (opModeIsActive()) {

        //}
    }

    //One of many Auto Options, create as per Strategy
    public void unhitchRobotMoveToCrater()
    {
        //Take xtra care of encoder settings, like RUN_TO_POSITION, if you need to keep track of positions in every step
        //Lift and extend
        robot.lower();
        //Hitch servo code here
        // code
        //Drive to Crater
        robot.MoveTillEnd();
        telemetry.update();
    }
}
