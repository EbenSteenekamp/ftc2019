package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="ServoMotor", group="Linear Opmode")

public class ServoMotor extends  LinearOpMode {

    static final double INCREMENT   = 0.5;
    static final int    CYCLE_MS    =   50;

    CRServo servo1;
    Servo servo2;
    public final static double servoPower = 0.5;
    public final static double position = 0.0;
    public final static double servo_min = 0.0;
    public final static double servo_max = 1.0;
    public final static double Servo_Start = 0;

    @Override
    public void runOpMode() {

        servo1 = hardwareMap.get(CRServo.class, "servo1");
        servo2 = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo2");

        telemetry.addData(">", "Press Start Now to scan Servo." );
        telemetry.update();
        waitForStart();

        servo2.setPosition(Servo_Start);

        while(opModeIsActive()){
            if (gamepad2.a){
                servo2.setPosition(0);
            }
            if (gamepad2.b){
                servo2.setPosition(1);
            }
            while (gamepad2.dpad_up) {
                servo1.setPower(servoPower + 6);
                telemetry.addData("Push Up", "%5.2f", position);
                telemetry.update();
            }
            while (gamepad2.dpad_down){
                servo1.setPower(servoPower - 6);
                telemetry.addData("Push Down", "%5.2f", position);
                telemetry.update();
            }
            telemetry.addData("Servo Position", "%5.2f", position);
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();
        }
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
