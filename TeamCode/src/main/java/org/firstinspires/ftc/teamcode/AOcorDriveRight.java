package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

@Autonomous(name = "AOcorDriveRight", group = "AutoOp")
public class AOcorDriveRight extends LinearOpMode{
    CloneAutoOpRobot robot = new CloneAutoOpRobot();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();
        robot.setRobottelemetry(telemetry);
        robot.start();
        while (opModeIsActive()) {
            unhitchRobot();
            moveRight();
            stop();
        }
    }
    public void unhitchRobot()
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
    }
    public void moveRight(){
        robot.motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.motorLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorLeftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.motorRightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addLine("Move Right");
        robot.MoveR();
        telemetry.update();

        telemetry.addLine("Move from Right");
        robot.CorFromR();
        telemetry.update();
    }
}
