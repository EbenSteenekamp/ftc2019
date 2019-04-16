package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.Locale;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "GyroStrafe", group = "Sensor")
public class GyroStrafe extends LinearOpMode {

    DcMotor motorLeftFront;
    DcMotor motorLeftRear;
    DcMotor motorRightFront;
    DcMotor motorRightRear;
    DcMotor motorCollect;
    DcMotor motorLift;
    DcMotor motorExtend;
    Servo dropBeaconServo;
    Servo hitchServo;
    Servo latchLockServo;

    double liftmotorUpPowerSetting = 1; //We need all the Power to Lift
    double liftmotorDownPowerSetting = -1; //We can make the motot slower when the are goes down
    double collectorMotorPowerSetting = 0.7 ; //We don't need full Power at the Collector, Run one direction
    double extendingArmPowerSetting = 1; //We only need half power, retracting and extending use same value, only sign change for motor direction

    boolean moveServoOpen = false;
    boolean moveServoClose = false;


    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    // The IMU sensor object
    BNO055IMU imu;

    // State used for updating telemetry
    Orientation angles;
    Acceleration gravity;

    //----------------------------------------------------------------------------------------------
    // Main logic
    //----------------------------------------------------------------------------------------------

    @Override public void runOpMode() {

        hitchServo = hardwareMap.servo.get("hitchServo");
        motorExtend = hardwareMap.get(DcMotor.class, "motorExtend");
        motorExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dropBeaconServo = hardwareMap.servo.get("DropBeaconServo");
        latchLockServo = hardwareMap.servo.get("latchLockServo");
        // collectorStop = hardwareMap.servo.get("CollectorStopServo");
        motorLift = hardwareMap.get(DcMotor.class, "motorLift");
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //Front wheels
        motorLeftFront = hardwareMap.get(DcMotor.class, "motorLeftFront");
        motorRightFront = hardwareMap.get(DcMotor.class, "motorRightFront");
        //Set Encoder Stuff
        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //Set Position to Zero
        motorLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Rear wheels
        motorLeftRear = hardwareMap.get(DcMotor.class, "motorLeftRear");
        motorRightRear = hardwareMap.get(DcMotor.class, "motorRightRear");

        motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Reverse wheels on the one side
        motorRightRear.setDirection(DcMotor.Direction.REVERSE);
        motorRightFront.setDirection(DcMotor.Direction.REVERSE);
        motorExtend.setDirection(DcMotor.Direction.REVERSE);
        dropBeaconServo.setDirection(Servo.Direction.REVERSE);
        motorCollect = hardwareMap.get(DcMotor.class, "motorCollect");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here just reports accelerations to the logcat log; it doesn't actually
        // provide positional information.
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        // Set up our telemetry dashboard
        composeTelemetry();

        // Wait until we're told to go
        waitForStart();

        latchLockServo.setPosition(1);
        dropBeaconServo.setPosition(0.5);
        double speedControlPowerSetting = 0.75;
        double leftFrontPowerSetting = 0;
        double leftRearPowerSetting = 0;
        double rightFrontPowerSetting = 0;
        double rightRearPowerSetting = 0;
        boolean collecting = false;

        // Start the logging of measured acceleration
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

        // Loop and update the dashboard
        while (opModeIsActive()) {
            telemetry.update();

            if (gamepad1.right_trigger >0) {

                double xAces = gravity.xAccel;

                while (gamepad1.right_trigger > 0) {

                    if (gravity.xAccel == xAces) {
                        motorLeftFront.setPower(-speedControlPowerSetting);
                        motorLeftRear.setPower(+speedControlPowerSetting);
                        motorRightFront.setPower(+speedControlPowerSetting);
                        motorRightRear.setPower(-speedControlPowerSetting);
                    }

                    if (gravity.xAccel > xAces){
                        motorLeftFront.setPower(-speedControlPowerSetting);
                        motorLeftRear.setPower(+speedControlPowerSetting);
                        motorRightFront.setPower(+speedControlPowerSetting + 0.05);
                        motorRightRear.setPower(-speedControlPowerSetting - 0.05);
                    }

                    if (gravity.xAccel < xAces) {
                        motorLeftFront.setPower(-speedControlPowerSetting - 0.05);
                        motorLeftRear.setPower(+speedControlPowerSetting + 0.05);
                        motorRightFront.setPower(+speedControlPowerSetting);
                        motorRightRear.setPower(-speedControlPowerSetting);
                    }

                    telemetry.addData("Path1", "Running to Target LF,LR, RF, RR %7d :%7d :%7d :%7d",
                            motorLeftFront.getCurrentPosition(),
                            motorLeftRear.getCurrentPosition(),
                            motorRightFront.getCurrentPosition(),
                            motorRightRear.getCurrentPosition());
                    telemetry.update();
                }
            }

            if (gamepad1.left_trigger >0) {

                double xAces = gravity.xAccel;

                while (gamepad1.left_trigger > 0) {
                    if (gravity.xAccel == xAces) {
                        motorLeftFront.setPower(+speedControlPowerSetting);
                        motorLeftRear.setPower(-speedControlPowerSetting);
                        motorRightFront.setPower(-speedControlPowerSetting);
                        motorRightRear.setPower(+speedControlPowerSetting);
                    }

                    if (gravity.xAccel > xAces){
                        motorLeftFront.setPower(+speedControlPowerSetting);
                        motorLeftRear.setPower(-speedControlPowerSetting);
                        motorRightFront.setPower(-speedControlPowerSetting - 0.05);
                        motorRightRear.setPower(+speedControlPowerSetting + 0.05);
                    }

                    if (gravity.xAccel < xAces) {
                        motorLeftFront.setPower(+speedControlPowerSetting + 0.05);
                        motorLeftRear.setPower(-speedControlPowerSetting - 0.05);
                        motorRightFront.setPower(-speedControlPowerSetting);
                        motorRightRear.setPower(+speedControlPowerSetting);
                    }

                    telemetry.addData("Path1", "Running to Target LF,LR, RF, RR %7d :%7d :%7d :%7d",
                            motorLeftFront.getCurrentPosition(),
                            motorLeftRear.getCurrentPosition(),
                            motorRightFront.getCurrentPosition(),
                            motorRightRear.getCurrentPosition());
                    telemetry.update();
                }
            }
            telemetry.update();
        }

    }

    //----------------------------------------------------------------------------------------------
    // Telemetry Configuration
    //----------------------------------------------------------------------------------------------

    void composeTelemetry() {

        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() { @Override public void run()
        {
            // Acquiring the angles is relatively expensive; we don't want
            // to do that in each of the three items that need that info, as that's
            // three times the necessary expense.
            angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            gravity  = imu.getGravity();
        }
        });

        telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override public String value() {
                        return imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override public String value() {
                        return imu.getCalibrationStatus().toString();
                    }
                });

        telemetry.addLine()
                .addData("heading", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.firstAngle);
                    }
                })
                .addData("roll", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.thirdAngle);
                    }
                });

        telemetry.addLine()
                .addData("grvty", new Func<String>() {
                    @Override public String value() {
                        return gravity.toString();
                    }
                })
                .addData("mag", new Func<String>() {
                    @Override public String value() {
                        return String.format(Locale.getDefault(), "%.3f",
                                Math.sqrt(gravity.xAccel*gravity.xAccel
                                        + gravity.yAccel*gravity.yAccel
                                        + gravity.zAccel*gravity.zAccel));
                    }
                });
    }

    //----------------------------------------------------------------------------------------------
    // Formatting
    //----------------------------------------------------------------------------------------------

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}

