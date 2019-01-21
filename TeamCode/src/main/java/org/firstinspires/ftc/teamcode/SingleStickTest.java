package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="SingleStickDrive", group="Linear Opmode")

public class SingleStickTest extends LinearOpMode {
    DcMotor motorLeftFront;
    DcMotor motorLeftRear;
    DcMotor motorRightFront;
    DcMotor motorRightRear;

    @Override
    public void runOpMode() throws InterruptedException {

        motorLeftFront = hardwareMap.get(DcMotor.class, "motorLeftFront");
        motorLeftRear = hardwareMap.get(DcMotor.class, "motorLeftRear");
        motorRightFront = hardwareMap.get(DcMotor.class, "motorRightFront");
        motorRightRear = hardwareMap.get(DcMotor.class, "motorRightRear");
        motorLeftFront.setDirection(DcMotor.Direction.REVERSE);
        motorLeftRear.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        double SpeedControl = 0.5;
        double tgtPower1 = 0;
        double tgtPower2 = 0;
        double tgtPower3 = 0;
        double tgtPower4 = 0;
        Boolean tgtPowerML = false;
        Boolean tgtPowerMR = false;

        while (opModeIsActive()) {

            if (gamepad1.right_bumper) {
                SpeedControl = 1;
            }
            if (gamepad1.left_bumper) {
                SpeedControl = 0.50;
            }

            if ((gamepad1.dpad_right && gamepad1.dpad_left) == false) {

                if (gamepad1.left_trigger >= 0) {

                    if (gamepad1.left_stick_x == 0) {

                        motorLeftFront.setPower(gamepad1.left_stick_y * SpeedControl);
                        motorLeftRear.setPower(gamepad1.left_stick_y * SpeedControl);
                        motorRightFront.setPower(gamepad1.left_stick_y * SpeedControl);
                        motorRightRear.setPower(gamepad1.left_stick_y * SpeedControl);
                    }
                    if (gamepad1.left_stick_x >= 0) {

                        motorLeftFront.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) * SpeedControl );
                        motorLeftRear.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) * SpeedControl );
                        motorRightFront.setPower(gamepad1.left_stick_y * SpeedControl);
                        motorRightRear.setPower(gamepad1.left_stick_y * SpeedControl);
                    }

                    if (gamepad1.left_stick_x <= 0) {

                        motorLeftFront.setPower(gamepad1.left_stick_y * SpeedControl);
                        motorLeftRear.setPower(gamepad1.left_stick_y * SpeedControl);
                        motorRightFront.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) * SpeedControl );
                        motorRightRear.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x) * SpeedControl );

                    }
                }

                if (gamepad1.left_trigger == 0) {

                    if ((gamepad1.dpad_right && gamepad1.dpad_left) == false) {

                        motorLeftFront.setPower(gamepad1.right_stick_y * SpeedControl);
                        motorLeftRear.setPower(gamepad1.right_stick_y * SpeedControl);
                        motorRightFront.setPower(gamepad1.left_stick_y * SpeedControl);
                        motorRightRear.setPower(gamepad1.left_stick_y * SpeedControl);

                    }
                }
            }


            while (gamepad1.dpad_right) {

                motorLeftFront.setPower(-1);
                motorLeftRear.setPower(+1);
                motorRightFront.setPower(+1);
                motorRightRear.setPower(-1);
            }
            while (gamepad1.dpad_left) {
                motorLeftFront.setPower(+1);
                motorLeftRear.setPower(-1);
                motorRightFront.setPower(-1);
                motorRightRear.setPower(+1);
            }

            telemetry.addData("LF Target Power", tgtPower1);
            telemetry.addData("LF Motor Power", motorLeftFront.getPower());

            telemetry.addData("LR Target Power", tgtPower2);
            telemetry.addData("LR Motor Power", motorLeftRear.getPower());

            telemetry.addData("RF Target Power", tgtPower3);
            telemetry.addData("RF Motor Power", motorRightFront.getPower());

            telemetry.addData("RR Target Power", tgtPower4);
            telemetry.addData("RR Motor Power", motorRightRear.getPower());

            telemetry.addData("Status", "Running");
            telemetry.update();

        }
    }
}
