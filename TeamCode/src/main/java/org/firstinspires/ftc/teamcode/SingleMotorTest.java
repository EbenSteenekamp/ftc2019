package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="SingleMotor", group="Linear Opmode")

public class SingleMotorTest extends LinearOpMode {
    DcMotor motorTest;
    @Override
    public void runOpMode(){
        motorTest = hardwareMap.get(DcMotor.class, "motorTest");
        motorTest.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.update();

        waitForStart();

        while (opModeIsActive()){
            if (gamepad1.left_stick_y > 0) {
                motorTest.setPower(gamepad1.left_stick_y);
                telemetry.addLine("Motor Active");
            }
            if (gamepad1.left_stick_y == 0){
                motorTest.setPower(0);
            }
            telemetry.update();
        }
    }
}
