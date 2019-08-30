package OffSeasonPractice;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.vuforia.Trackable;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RobotHardwareMap extends LinearOpMode{

    public DcMotor leftmotor;
    public DcMotor rightmotor;

    public DigitalChannel digitalTouch;
    public ModernRoboticsI2cRangeSensor rangeSensor;

    HardwareMap masterConfig =null;

    public RobotHardwareMap(){}

    @Override
    public void runOpMode(){}

    public void init(HardwareMap amasterConfig){
        masterConfig = amasterConfig;

        leftmotor = masterConfig.get(DcMotor.class, "leftmotor");
        rightmotor = masterConfig.get(DcMotor.class, "rightmotor");

        //digitalTouch = masterConfig.get(DigitalChannel.class, "digitalTouch");
        //rangeSensor = masterConfig.get(ModernRoboticsI2cRangeSensor.class, "rangeSensor");

//        leftmotor.setDirection(DcMotor.Direction.REVERSE);
       rightmotor.setDirection(DcMotor.Direction.REVERSE);

        leftmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
