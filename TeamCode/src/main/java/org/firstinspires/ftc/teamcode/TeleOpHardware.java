
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by Edric on 2018/03/31.
 */

public class TeleOpHardware {
    public DcMotor DriveFrontLeft= null;
    public DcMotor DriveFrontRight= null;
    public DcMotor DriveBackRight= null;
    public DcMotor DriveBackLeft= null;
    public DcMotor ExtendLeft= null;
    public DcMotor ExtendRight= null;
    public DcMotor RelicLeft= null;
    public DcMotor RelicRight= null;
    public Servo Phone= null;
    public Servo GlyphRight= null;
    public Servo GlyphLeft= null;
    public Servo Jewel= null;
    public Servo RelicLeftServo= null;
    public Servo RelicRightServo= null;
    public CRServo GlyphPickUp= null;

    HardwareMap hwMap =null;
    private ElapsedTime period  = new ElapsedTime();

    public TeleOpHardware(){

    }

    public void init(HardwareMap ahwMap){
        hwMap = ahwMap;
        DriveBackLeft =hwMap.get(DcMotor.class, "DriveBackLeft");
        DriveBackRight =hwMap.get(DcMotor.class, "DriveBackRight");
        DriveFrontLeft =hwMap.get(DcMotor.class, "DriveFrontLeft");
        DriveFrontRight =hwMap.get(DcMotor.class, "DriveFrontRight");
        ExtendLeft =hwMap.get(DcMotor.class, "ExtendLeft");
        ExtendRight =hwMap.get(DcMotor.class, "ExtendRight");
        RelicRight =hwMap.get(DcMotor.class, "RelicRight");
        RelicLeft =hwMap.get(DcMotor.class, "RelicLeft");
        Phone =hwMap.get(Servo.class, "Phone");
        GlyphRight =hwMap.get(Servo.class, "GlyphRight");
        GlyphLeft =hwMap.get(Servo.class, "GlyphLeft");
        Jewel =hwMap.get(Servo.class, "Jewel");
        RelicLeftServo =hwMap.get(Servo.class, "RelicLeftServo");
        RelicRightServo =hwMap.get(Servo.class, "RelicRightServo");
        GlyphPickUp = hwMap.get(CRServo.class,"GlyphPickUp");
        DriveBackRight.setDirection(DcMotor.Direction.REVERSE);
        DriveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        ExtendLeft.setDirection(DcMotor.Direction.REVERSE);
        ExtendRight.setDirection(DcMotor.Direction.REVERSE);
        GlyphPickUp.setPower(0);
        RelicLeftServo.setPosition(0.25);
        RelicRightServo.setPosition(0.07);
        Phone.setPosition(0);
    }
}
