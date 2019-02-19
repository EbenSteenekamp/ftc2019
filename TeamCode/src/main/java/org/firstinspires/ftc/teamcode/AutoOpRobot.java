package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class AutoOpRobot extends LinearOpMode{

   public DcMotor motorLeftFront;
   public DcMotor motorLeftRear;
   public DcMotor motorRightFront;
   public DcMotor motorRightRear;
 //  public CRServo servo1;
 //  public DcMotor motorCollect;
 //  public DcMotor motorLift;
 //  public DcMotor motorExtend;

    HardwareMap masterConfig= null;
    private ElapsedTime period = new ElapsedTime();

   public AutoOpRobot(){

   }
    @Override
    public void runOpMode() throws InterruptedException {

    }
    public void init(HardwareMap amasterConfig){
       masterConfig = amasterConfig;

      //  servo1 = masterConfig.crservo.get("servo1");
      //  motorExtend = masterConfig.get(DcMotor.class, "motorExtend");
      //  motorLift = masterConfig.get(DcMotor.class, "motorLift");
        motorLeftFront = masterConfig.get(DcMotor.class, "motorLeftFront");
        motorLeftRear = masterConfig.get(DcMotor.class, "motorLeftRear");
        motorRightFront = masterConfig.get(DcMotor.class, "motorRightFront");
        motorRightRear = masterConfig.get(DcMotor.class, "motorRightRear");
        motorLeftFront.setDirection(DcMotor.Direction.REVERSE);
        motorLeftRear.setDirection(DcMotor.Direction.REVERSE);
      //  motorCollect = masterConfig.get(DcMotor.class, "motorCollect");
    }
    public void Stop(){
       motorLeftRear.setPower(0);
       motorLeftFront.setPower(0);
       motorRightRear.setPower(0);
       motorRightFront.setPower(0);
    }
    public void turnAroundUntilFound(){
       motorRightFront.setPower(0.2);
       motorRightRear.setPower(0.2);
       motorLeftFront.setPower(-0.2);
       motorLeftRear.setPower(-0.2);
    }
    /**  public void VuforiaLogic(String Team){
     if (Team == "Blue"){
     Phone.setPosition(0.35);
     if (VuCentre == true){
     PillarsToBePassed = 2;
     }
     else if (VuLeft == true){
     PillarsToBePassed = 1;
     }
     else if (VuRight == true){
     PillarsToBePassed = 3;
     }
     else{
     PillarsToBePassed = 1;
     }
     }
     if (Team == "Red"){
     Phone.setPosition(0.35);
     if (VuCentre == true){
     PillarsToBePassed = 2;
     ExtraDropDistance = 100;
     }
     else if (VuLeft == true){
     PillarsToBePassed = 3;
     ExtraDropDistance = 100;
     }
     else if (VuRight == true){
     PillarsToBePassed = 1;
     ExtraDropDistance = 210;
     }
     else{
     PillarsToBePassed = 1;
     ExtraDropDistance = 210;
     }
     }

     }
     public void JewelSelectorDown() throws InterruptedException {
     Jewel.setPosition(0.44);
     Thread.sleep(1000);

     }

     public void DriveUntilMotersDontTurn() {

     while (DriveFrontRight.getPower() > 0.3){
     DriveBackLeft.setPower(0.4);
     DriveBackRight.setPower(0.4);
     DriveFrontRight.setPower(0.4);
     DriveFrontLeft.setPower(0.4);
     }
     DriveBackLeft.setPower(0);
     DriveBackRight.setPower(0);
     DriveFrontRight.setPower(0);
     DriveFrontLeft.setPower(0);

     }

     public void Jewel(String Team) throws InterruptedException {
     color_C_reader = new I2cDeviceSynchImpl(colorC,I2cAddr.create8bit(0x3c),false);
     color_C_reader.engage();
     JewelSelectorDown();
     Thread.sleep(500);
     double EncReading1 = 0;
     double EncReading2 = 0;
     colorCcache = color_C_reader.read(0x04,1);
     while (colorCcache[0] == 0){
     colorCcache = color_C_reader.read(0x04,1);
     EncReading1 = DriveBackLeft.getCurrentPosition();
     DriveTrain(0.04);
     }
     DriveTrain(0);
     EncReading2 =  DriveBackLeft.getCurrentPosition();;
     ExtraDistance = Math.abs(EncReading2-EncReading1);
     if (colorCcache[0] < 7) {
     Colour = "Blue";
     } else {
     Colour = "Red";
     }

     if (Colour == "Blue"){
     if (Team == "Blue") {
     GyroTurn(-20, 0.1);
     Thread.sleep(500);
     Jewel.setPosition(0);
     GyroTurn(20, 0.1);
     }
     if (Team == "Red") {
     GyroTurn(20, 0.1);
     Thread.sleep(500);
     Jewel.setPosition(0);
     GyroTurn(-20, 0.1);
     }
     }
     if (Colour == "Red"){
     if (Team == "Blue") {
     GyroTurn(20, 0.1);
     Thread.sleep(500);
     Jewel.setPosition(0);
     GyroTurn(-20, 0.1);
     }
     if (Team == "Red") {
     GyroTurn(-20, 0.1);
     Thread.sleep(500);
     Jewel.setPosition(0);
     GyroTurn(20, 0.1);
     }
     }

     }
     public void turn(int target, double Speed) throws InterruptedException {
     GyroTurn(target + mrGyro.getIntegratedZValue(),Speed);
     }

     public void GyroTurn(int target, double TurnSpeed) {
     TurnSpeed = Math.abs(TurnSpeed);
     mrGyro.resetZAxisIntegrator();
     zAccumulated = 0;
     zAccumulated = mrGyro.getIntegratedZValue();
     while ((Math.abs(zAccumulated - target) > 2)) {
     if (zAccumulated < target) {
     DriveFrontLeft.setPower(-TurnSpeed);
     DriveFrontRight.setPower(TurnSpeed);
     DriveBackLeft.setPower(-TurnSpeed);
     DriveBackRight.setPower(TurnSpeed);
     }

     if (zAccumulated > target) {
     DriveFrontLeft.setPower(TurnSpeed);
     DriveFrontRight.setPower(-TurnSpeed);
     DriveBackLeft.setPower(TurnSpeed);
     DriveBackRight.setPower(-TurnSpeed);
     }
     zAccumulated = mrGyro.getIntegratedZValue();
     }
     DriveFrontLeft.setPower(0);
     DriveFrontRight.setPower(0);
     DriveBackLeft.setPower(0);
     DriveBackRight.setPower(0);
     }

     public void DriveTrain(double speed){
     DriveFrontRight.setPower(speed);
     DriveFrontLeft.setPower(speed);
     DriveBackLeft.setPower(speed);
     DriveBackRight.setPower(speed);
     }
     public void GlyphPickUp(int Time) throws InterruptedException {
     GlyphRight.setPosition(0.5);
     GlyphLeft.setPosition(0.5);
     Thread.sleep(250);
     GlyphPickUp.setPower(1);
     Thread.sleep(Time);
     GlyphPickUp.setPower(0);
     }
     public void GlyphDrop(int Time) throws InterruptedException {
     GlyphPickUp.setPower(-1);
     Thread.sleep(Time-100);
     GlyphPickUp.setPower(0);
     Thread.sleep(100);
     GlyphRight.setPosition(0.9);
     GlyphLeft.setPosition(0.1);

     }
     public void DriveWithRange(double Distance,double Speed, String Team) throws InterruptedException {
     Jewel.setPosition(0.1);
     double ExtraSpeed = 0;
     if(Team == "Blue"){
     ExtraSpeed = -0.1;
     }
     if(Team == "Red"){
     ExtraSpeed = 0.1;
     }
     double leftSpeed; //Power to feed the motors
     double rightSpeed;
     double target = mrGyro.getIntegratedZValue();  //Starting direction
     Pillars = 0;
     while (Pillars != PillarsToBePassed ) {  //While we have not passed out intended distance
     zAccumulated = mrGyro.getIntegratedZValue();  //Current direction
     leftSpeed = Speed + (zAccumulated - target) / 100;  //Calculate speed for each side
     rightSpeed = (Speed - (zAccumulated - target) / 100)+ExtraSpeed;  //See Gyro Straight video for detailed explanation
     leftSpeed = Range.clip(leftSpeed, -1, 1);
     rightSpeed = Range.clip(rightSpeed, -1, 1);
     DriveFrontLeft.setPower(leftSpeed);
     DriveBackLeft.setPower(leftSpeed);
     DriveBackRight.setPower(rightSpeed);
     DriveFrontRight.setPower(rightSpeed);
     if (rangeSensor.rawUltrasonic() < Distance) {
     Pillars = Pillars+1;
     telemetry.addData("Pillars:", Pillars);
     telemetry.update();
     Thread.sleep(500);
     }
     }
     DriveFrontLeft.setPower(0);
     DriveBackLeft.setPower(0);
     DriveBackRight.setPower(0);
     DriveFrontRight.setPower(0);
     }
     public void DriveWithDeltaRange(double Speed, String Team) throws InterruptedException {
     Jewel.setPosition(0.1);
     double ExtraSpeed = 0;
     int k = 0;
     if(Team == "Blue"){
     ExtraSpeed = -0.1;
     }
     if(Team == "Red"){
     ExtraSpeed = 0.1;
     }
     double leftSpeed; //Power to feed the motors
     double rightSpeed;
     double target = mrGyro.getIntegratedZValue();  //Starting direction
     Pillars = 0;
     while (Pillars != PillarsToBePassed ) {  //While we have not passed out intended distance
     zAccumulated = mrGyro.getIntegratedZValue();  //Current direction
     leftSpeed = Speed + (zAccumulated - target) / 100;  //Calculate speed for each side
     rightSpeed = (Speed - (zAccumulated - target) / 100)+ExtraSpeed;  //See Gyro Straight video for detailed explanation
     leftSpeed = Range.clip(leftSpeed, -1, 1);
     rightSpeed = Range.clip(rightSpeed, -1, 1);
     DriveFrontLeft.setPower(leftSpeed);
     DriveBackLeft.setPower(leftSpeed);
     DriveBackRight.setPower(rightSpeed);
     DriveFrontRight.setPower(rightSpeed);
     while (k ==0){
     Reading1 = rangeSensor.rawUltrasonic();
     Reading2 = rangeSensor.rawUltrasonic();
     k = 1;
     }
     Reading1 = Reading2;
     Reading2 = rangeSensor.rawUltrasonic();

     if ((Reading2-Reading1) > 3) {
     Pillars = Pillars+1 ;
     }
     }
     DriveFrontLeft.setPower(0);
     DriveBackLeft.setPower(0);
     DriveBackRight.setPower(0);
     DriveFrontRight.setPower(0);
     }
     public void DriveForwardGyro(double Distance, double Speed) {
     DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     DriveFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
     DriveFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
     DriveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
     DriveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
     double ExtraSpeed = 0.1;
     double leftSpeed;
     double rightSpeed;
     Distance = Math.abs(Distance);
     Speed = Math.abs(Speed);
     double target = mrGyro.getIntegratedZValue();  //Starting direction
     double startPosition = DriveBackLeft.getCurrentPosition();  //Starting position
     while (DriveBackLeft.getCurrentPosition() < Distance + startPosition) {  //While we have not passed out intended distance
     zAccumulated = mrGyro.getIntegratedZValue();  //Current direction

     leftSpeed = Speed + (zAccumulated - target) / 100;  //Calculate speed for each side
     rightSpeed = (Speed - (zAccumulated - target) / 100)+ExtraSpeed;  //See Gyro Straight video for detailed explanation

     leftSpeed = Range.clip(leftSpeed, -1, 1);
     rightSpeed = Range.clip(rightSpeed, -1, 1);

     DriveBackLeft.setPower(leftSpeed);
     DriveFrontLeft.setPower(leftSpeed);
     DriveBackRight.setPower(rightSpeed);
     DriveFrontRight.setPower(rightSpeed);


     }

     DriveBackLeft.setPower(0);
     DriveFrontLeft.setPower(0);
     DriveBackRight.setPower(0);
     DriveFrontRight.setPower(0);

     }

     public void DriveBackwardGyro(double Distance, double Speed) {
     DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     DriveFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
     DriveFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
     DriveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
     DriveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
     double ExtraSpeed = 0.1;
     double leftSpeed;
     double rightSpeed;
     Speed = Math.abs(Speed);
     Distance = -Math.abs(Distance);
     Speed = -Math.abs(Speed);
     double target = mrGyro.getIntegratedZValue();  //Starting direction
     double startPosition = DriveBackLeft.getCurrentPosition();  //Starting position
     while (DriveBackLeft.getCurrentPosition() > Distance + startPosition) {  //While we have not passed out intended distance
     zAccumulated = mrGyro.getIntegratedZValue();  //Current direction

     leftSpeed = Speed + (zAccumulated - target) / 100;  //Calculate speed for each side
     rightSpeed = (Speed - (zAccumulated - target) / 100)-ExtraSpeed;  //See Gyro Straight video for detailed explanation

     leftSpeed = Range.clip(leftSpeed, -1, 1);
     rightSpeed = Range.clip(rightSpeed, -1, 1);

     DriveBackLeft.setPower(leftSpeed);
     DriveFrontLeft.setPower(leftSpeed);
     DriveBackRight.setPower(rightSpeed);
     DriveFrontRight.setPower(rightSpeed);


     }

     DriveBackLeft.setPower(0);
     DriveFrontLeft.setPower(0);
     DriveBackRight.setPower(0);
     DriveFrontRight.setPower(0);

     }
     public void GetDriveDistance(double Angle){
     DriveDistance = 0;
     Angle = Math.abs(Angle)-90;
     DriveDistance = Math.abs((((Reading2)/Math.cos(Angle))*33.33333333)-500);
     }
     public void GetDriveDistanceDeg(double Angle){

     DriveDistance = 0;
     Angle = Math.abs(Angle)-90;
     Angle = Angle*Math.PI/180;
     DriveDistance = (((Reading2/Math.cos(Angle))*33.33333333333333333333333333333333))-520;
     }
     public void GyroTurnAccurate(int target, double TurnSpeed) {
     TurnSpeed = Math.abs(TurnSpeed);
     mrGyro.resetZAxisIntegrator();
     zAccumulated = 0;
     zAccumulated = mrGyro.getIntegratedZValue();
     while ((Math.abs(zAccumulated - target) != 0)) {
     if (zAccumulated < target) {
     DriveFrontLeft.setPower(-TurnSpeed);
     DriveFrontRight.setPower(TurnSpeed);
     DriveBackLeft.setPower(-TurnSpeed);
     DriveBackRight.setPower(TurnSpeed);
     }

     if (zAccumulated > target) {
     DriveFrontLeft.setPower(TurnSpeed);
     DriveFrontRight.setPower(-TurnSpeed);
     DriveBackLeft.setPower(TurnSpeed);
     DriveBackRight.setPower(-TurnSpeed);
     }
     zAccumulated = mrGyro.getIntegratedZValue();
     }
     DriveFrontLeft.setPower(0);
     DriveFrontRight.setPower(0);
     DriveBackLeft.setPower(0);
     DriveBackRight.setPower(0);
     */
}
