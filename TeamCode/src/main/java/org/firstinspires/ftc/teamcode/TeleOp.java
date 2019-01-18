
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Edrich on 2018/03/30.
 */
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
//@Disabled
public class TeleOp extends LinearOpMode {

    TeleOpHardware robot = new TeleOpHardware();
    private ElapsedTime runtime = new ElapsedTime();

    double DriveSpeed = 1;
    double RelicSpeed = 0.5;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            while (gamepad2.left_stick_button) {
                Driver1();
                Driver2();
                robot.RelicRight.setPower(gamepad2.right_stick_y * RelicSpeed);
                robot.RelicLeft.setPower(0);
                robot.ExtendRight.setPower(0);
                robot.ExtendLeft.setPower(0);

            }
            while (gamepad2.right_stick_button) {
                Driver1();
                Driver2();
                robot.RelicLeft.setPower(gamepad2.left_stick_y * RelicSpeed);
                robot.RelicRight.setPower(0);
                robot.ExtendRight.setPower(0);
                robot.ExtendLeft.setPower(0);
            }
            while (!gamepad2.left_stick_button && !gamepad2.right_stick_button) {
                Driver1();
                Driver2();
                robot.ExtendLeft.setPower(-gamepad2.right_stick_y);
                robot.ExtendRight.setPower(gamepad2.left_stick_y);
                robot.RelicRight.setPower(0);
                robot.RelicLeft.setPower(0);
            }
        }
    }



    public void Driver1() {
        robot.GlyphPickUp.setPower(0);

        while (gamepad1.dpad_up) {
            robot.GlyphPickUp.setPower(1);
        }
        while (gamepad1.dpad_down) {
            robot.GlyphPickUp.setPower(-1);
        }
        if (gamepad1.back) {
            DriveSpeed = 0.1;
        }
        if (gamepad1.start) {
            DriveSpeed = 1;
        }
        if ((gamepad1.left_bumper && gamepad1.right_bumper) == false) {
            robot.DriveFrontLeft.setPower((gamepad1.left_stick_y * DriveSpeed));
            robot.DriveFrontRight.setPower(gamepad1.right_stick_y * DriveSpeed);
            robot.DriveBackLeft.setPower((gamepad1.left_stick_y * DriveSpeed));
            robot.DriveBackRight.setPower(gamepad1.right_stick_y * DriveSpeed);
        }
        while (gamepad1.left_bumper) {
            robot.DriveFrontLeft.setPower(1);
            robot.DriveFrontRight.setPower(-1);
            robot.DriveBackLeft.setPower(-1);
            robot.DriveBackRight.setPower(1);
        }
        while (gamepad1.right_bumper) {
            robot.DriveFrontLeft.setPower(-1);
            robot.DriveFrontRight.setPower(1);
            robot.DriveBackLeft.setPower(1);
            robot.DriveBackRight.setPower(-1);
        }
        if (gamepad1.x) {
            robot.GlyphRight.setPosition(0.9);
            robot.GlyphLeft.setPosition(0.1);
        }
        if (gamepad1.b) {
            robot.GlyphRight.setPosition(0.5);
            robot.GlyphLeft.setPosition(0.5);
        }
        if (gamepad1.a) {
            robot.GlyphRight.setPosition(0.7);
            robot.GlyphLeft.setPosition(0.3);
        }
        if (gamepad1.y) {
            robot.GlyphRight.setPosition(0.55);
            robot.GlyphLeft.setPosition(0.45);
        }

    }
    public void Driver2() {

        if (gamepad2.dpad_left){
            robot.RelicRightServo.setPosition(0.07);
        }
        if (gamepad2.dpad_right){
            robot.RelicRightServo.setPosition(0.5);
        }
        if (gamepad2.x){
            robot.RelicLeftServo.setPosition(0);
        }
        if (gamepad2.b){
            robot.RelicLeftServo.setPosition(0.25);
        }
    }
    public void Driver2Backup(){
        robot.RelicRight.setPower(-gamepad1.right_stick_y* RelicSpeed);
        robot.RelicLeft.setPower(-gamepad1.left_stick_y* RelicSpeed);
        if (gamepad1.back) {
            RelicSpeed = 0.1;
        }
        if (gamepad1.start) {
            RelicSpeed = 0.5;
        }
        robot.ExtendLeft.setPower(0);
        robot.ExtendRight.setPower(0);
        while(gamepad1.dpad_up){
            robot.ExtendRight.setPower(-0.4);
        }
        while(gamepad1.dpad_down){
            robot.ExtendRight.setPower(0.4);
        }
        while(gamepad1.y){
            robot.ExtendLeft.setPower(0.4);
        }
        while(gamepad1.a){
            robot.ExtendLeft.setPower(-0.4);
        }
        if (gamepad1.dpad_left){
            robot.RelicRightServo.setPosition(0.07);
        }
        if (gamepad1.dpad_right){
            robot.RelicRightServo.setPosition(0.5);
        }
        if (gamepad1.x){
            robot.RelicLeftServo.setPosition(0);
        }
        if (gamepad1.b){
            robot.RelicLeftServo.setPosition(0.25);
        }
    }
}
