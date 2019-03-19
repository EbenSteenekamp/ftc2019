package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "AOcorLE", group = "AutoOp")
public class AOcorLE extends LinearOpMode {

    CloneAutoOpRobot robot = new CloneAutoOpRobot();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();

        robot.setRobottelemetry(telemetry);
        robot.start();
        //unhitchRobotMoveToCorner();
        moveToCrater();
    }
    public void unhitchRobotMoveToCorner()
    {
        //Take xtra care of encoder settings, like RUN_TO_POSITION, if you need to keep track of positions in every step
        robot.motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.motorLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorLeftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorRightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //Lift and extend
        telemetry.addLine("Start Lowering the Robot");
        telemetry.update();
        robot.lower(); // See the changes to keep track of position
        telemetry.update();
        //Hitch servo code here
        // code
        //Drive to Corner
        telemetry.addLine("Drive the Robot to the Corner");
        robot.CORdrop();
        telemetry.update();

//        telemetry.addLine("Drive to crator");
//        robot.CORDrive();
//        telemetry.update();
    }
    public void moveToCrater()
    {
        //Take xtra care of encoder settings, like RUN_TO_POSITION, if you need to keep track of positions in every step
        robot.motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.motorLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorLeftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorRightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addLine("Drive to crator");
        robot.CORDrive();
        telemetry.update();
    }
}
