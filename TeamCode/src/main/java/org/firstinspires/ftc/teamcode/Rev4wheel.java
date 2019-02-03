package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Rev4wheel", group="Linear Opmode")

public class Rev4wheel extends LinearOpMode {
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

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        double tgtPower1 = 0;
        double tgtPower2 = 0;
        double tgtPower3 = 0;
        double tgtPower4 = 0;
        Boolean tgtPowerML = false;
        Boolean tgtPowerMR = false;

        while (opModeIsActive()) {

            tgtPower1 = -this.gamepad1.left_stick_y;
            tgtPower2 = -this.gamepad1.left_stick_y;
            tgtPower3 = -this.gamepad1.right_stick_y;
            tgtPower4 = -this.gamepad1.right_stick_y;

            tgtPowerML = this.gamepad1.dpad_left;
            tgtPowerMR = this.gamepad1.dpad_right;

            if(this.gamepad1.dpad_left == true)
            {
                motorLeftFront.setPower(-1);
                motorLeftRear.setPower(+1);
                motorRightFront.setPower(+1);
                motorRightRear.setPower(-1);
            }

            if(this.gamepad1.dpad_right == true)
            {
                motorLeftFront.setPower(+1);
                motorLeftRear.setPower(-1);
                motorRightFront.setPower(-1);
                motorRightRear.setPower(+1);
            }

            motorLeftFront.setPower(tgtPower1);
            motorLeftRear.setPower(tgtPower2);
            motorRightFront.setPower(tgtPower3);
            motorRightRear.setPower(tgtPower4);

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
