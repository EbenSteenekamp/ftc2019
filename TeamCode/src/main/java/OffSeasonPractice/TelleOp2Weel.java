package OffSeasonPractice;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TelleOp2Weel", group = "TeleOp" )
public class TelleOp2Weel extends LinearOpMode{
    RobotHardwareMap robot = new RobotHardwareMap();

    double speedControl = 0.25;
    double turnModifyerL;
    double turnModifyerR;

    public void runOpMode() throws InterruptedException{
        robot.init(hardwareMap);

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a = true) {
                speedControl = 0.25;
            }
            if (gamepad1.b = true) {
                speedControl = 0.25;
            }

            telemetry.addData("speed", speedControl);
            telemetry.update();

            if (gamepad1.left_stick_y > 0) {
                robot.leftmotor.setPower((gamepad1.left_stick_y * speedControl) + turnModifyerR);
                robot.rightmotor.setPower((gamepad1.left_stick_y * speedControl) + turnModifyerL);
            }
            if (gamepad1.left_stick_y < 0) {
                robot.leftmotor.setPower((gamepad1.left_stick_y * speedControl) - turnModifyerR);
                robot.rightmotor.setPower((gamepad1.left_stick_y * speedControl) - turnModifyerL);
            }
            if (gamepad1.left_stick_y == 0) {
                robot.leftmotor.setPower(0);
                robot.rightmotor.setPower(0);
            }
            if (gamepad1.right_stick_x >= 0) {
                turnModifyerL = gamepad1.right_stick_x;
                //telemetry.addData("turn mod L", turnModifyerL);
                telemetry.update();
            }
            if (gamepad1.right_stick_x <= 0) {
                turnModifyerR = -gamepad1.right_stick_x;
                //telemetry.addData("turn mod R", turnModifyerR);
                telemetry.update();
            }
        }
    }
}
