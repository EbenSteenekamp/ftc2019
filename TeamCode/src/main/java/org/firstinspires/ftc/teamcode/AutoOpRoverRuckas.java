package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "AutoOpRoverRuckas", group = "AutoOp")
public class AutoOpRoverRuckas extends LinearOpMode {
    AutoOpRobot robot = new AutoOpRobot();

    private static final String VUFORIA_KEY = "Adt99vT/////AAABmQfaL1Gc6k6YpSV4p0gyJUg3w2FZlS8RqVrXnweJXsLcl6JrGb5Age+Cv4I9IS+9XG2ZMhWR19WkeOkWkrTXMzKjblOZGI0FC/WUj9CXpGB7wTS8qQuNHut0NT3aZzPjx3aNnjfUCmBvwrCcVHgvLBLU460n9TE9Yug17HApyE+ix9xcJ2J5QtVejR9PNm8SNmpFAEyGmSasukJHF0Em7cNrHAsR5MxPSCBAnQA3B2pL5onCRotpWAu0rcOCYfXWrHeVtYAEHzm3GdFRj73cQ3eGFgOCHJSyMC0AhHH0M8cFlltdKc0f08FHioKrXu8OpvLGCTtYMSZ6Jq2we4+keaVEPBZY6U4YBffLK7jQnP1p";
    private static final float mmFTCFieldWidth = (12 * 6);       // the width of the FTC field (from the center point to the outer panels)
    private static final float mmTargetHeight = (6);          // the height of the center of the target image above the floor

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    private OpenGLMatrix lastLocation = null;
    private boolean targetVisible = false;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    boolean FindTarget = false;
    boolean blockMove = true;
    boolean BlueRoverV = false;
    boolean RedFootprintV = false;
    boolean FrontCratersV = false;
    boolean BackSpaceV = false;
    boolean targetFound = false;

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() throws InterruptedException {

        if (blockMove == false) {
            initVuforia();

            if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
                initTfod();
            } else {
                telemetry.addData("Sorry!", "This device is not compatible with TFOD");
            }

            /** Wait for the game to begin */
            telemetry.addData(">", "Press Play to start tracking");
            telemetry.update();
            waitForStart();

            robot.init(hardwareMap);

            robot.motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.motorLeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.motorRightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            telemetry.addData("Path0", "Starting at %7d :%7d",
                    robot.motorLeftFront.getCurrentPosition(),
                    robot.motorLeftRear.getCurrentPosition(),
                    robot.motorRightFront.getCurrentPosition(),
                    robot.motorRightRear.getCurrentPosition());
            telemetry.update();

            while (opModeIsActive()) {

                robot.lower();
                telemetry.addLine("Lower");
                telemetry.update();

                /** Activate Tensor Flow Object Detection. */
                if (tfod != null) {
                    tfod.activate();
                }

                while (opModeIsActive()) {
                    if (tfod != null) {
                        // getUpdatedRecognitions() will return null if no new information is available since
                        // the last time that call was made.
                        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                        if (updatedRecognitions != null) {
                            telemetry.addData("# Object Detected", updatedRecognitions.size());
                            if (updatedRecognitions.size() == 3) {
                                int goldMineralX = -1;
                                int silverMineral1X = -1;
                                int silverMineral2X = -1;
                                for (Recognition recognition : updatedRecognitions) {
                                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                        goldMineralX = (int) recognition.getLeft();
                                    } else if (silverMineral1X == -1) {
                                        silverMineral1X = (int) recognition.getLeft();
                                    } else {
                                        silverMineral2X = (int) recognition.getLeft();
                                    }
                                }
                                if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                    if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                        telemetry.addData("Gold Mineral Position", "Left");
                                        robot.MoveL();
                                        robot.Sample();
                                        robot.ResetLeft();
                                        blockMove = true;
                                    } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                        telemetry.addData("Gold Mineral Position", "Right");
                                        robot.MoveR();
                                        robot.Sample();
                                        robot.ResetRight();
                                        blockMove = true;
                                    } else {
                                        telemetry.addData("Gold Mineral Position", "Center");
                                        robot.Sample();
                                        robot.ResetMid();
                                        blockMove = true;
                                    }
                                }
                            }
                            telemetry.update();
                        }
                    }
                }
            }
        }
        if (blockMove == true && FindTarget == false) {
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

            parameters.vuforiaLicenseKey = VUFORIA_KEY;
            parameters.cameraDirection = CAMERA_CHOICE;

            vuforia = ClassFactory.getInstance().createVuforia(parameters);

            VuforiaTrackables targetsRoverRuckus = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
            VuforiaTrackable blueRover = targetsRoverRuckus.get(0);
            blueRover.setName("Blue-Rover");
            VuforiaTrackable redFootprint = targetsRoverRuckus.get(1);
            redFootprint.setName("Red-Footprint");
            VuforiaTrackable frontCraters = targetsRoverRuckus.get(2);
            frontCraters.setName("Front-Craters");
            VuforiaTrackable backSpace = targetsRoverRuckus.get(3);
            backSpace.setName("Back-Space");

            List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
            allTrackables.addAll(targetsRoverRuckus);

            /**
             * If you are standing in the Red Alliance Station looking towards the center of the field,
             *     - The X axis runs from your left to the right. (positive from the center to the right)
             *     - The Y axis runs from the Red Alliance Station towards the other side of the field
             *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
             *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
             */

            /**
             * To place the BlueRover target in the middle of the blue perimeter wall:
             * - First we rotate it 90 around the field's X axis to flip it upright.
             * - Then, we translate it along the Y axis to the blue perimeter wall.
             */
            OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                    .translation(0, mmFTCFieldWidth, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
            blueRover.setLocation(blueRoverLocationOnField);

            /**
             * To place the RedFootprint target in the middle of the red perimeter wall:
             * - First we rotate it 90 around the field's X axis to flip it upright.
             * - Second, we rotate it 180 around the field's Z axis so the image is flat against the red perimeter wall
             *   and facing inwards to the center of the field.
             * - Then, we translate it along the negative Y axis to the red perimeter wall.
             */
            OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                    .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
            redFootprint.setLocation(redFootprintLocationOnField);

            /**
             * To place the FrontCraters target in the middle of the front perimeter wall:
             * - First we rotate it 90 around the field's X axis to flip it upright.
             * - Second, we rotate it 90 around the field's Z axis so the image is flat against the front wall
             *   and facing inwards to the center of the field.
             * - Then, we translate it along the negative X axis to the front perimeter wall.
             */
            OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                    .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90));
            frontCraters.setLocation(frontCratersLocationOnField);

            /**
             * To place the BackSpace target in the middle of the back perimeter wall:
             * - First we rotate it 90 around the field's X axis to flip it upright.
             * - Second, we rotate it -90 around the field's Z axis so the image is flat against the back wall
             *   and facing inwards to the center of the field.
             * - Then, we translate it along the X axis to the back perimeter wall.
             */
            OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                    .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
            backSpace.setLocation(backSpaceLocationOnField);

            /**
             * If using the rear (High Res) camera:
             * We need to rotate the camera around it's long axis to bring the rear camera forward.
             * This requires a negative 90 degree rotation on the Y axis
             *
             * Next, translate the camera lens to where it is on the robot.
             * In this example, it is centered (left to right), but 110 mm forward of the middle of the robot, and 200 mm above ground level.
             */

            final int CAMERA_FORWARD_DISPLACEMENT = 110;   // eg: Camera is 110 mm in front of robot center
            final int CAMERA_VERTICAL_DISPLACEMENT = 200;   // eg: Camera is 200 mm above ground
            final int CAMERA_LEFT_DISPLACEMENT = 0;     // eg: Camera is ON the robot's center line

            OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                    .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                            CAMERA_CHOICE == FRONT ? 90 : -90, 0, 0));

            for (VuforiaTrackable trackable : allTrackables) {
                ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
            }

            telemetry.addData(">", "Press Play to start tracking");
            telemetry.update();

            waitForStart();
            robot.init(hardwareMap);
            robot.lower();

            targetsRoverRuckus.activate();
            while (opModeIsActive()) {
                // check all the trackable target to see which one (if any) is visible.

                telemetry.addData(">", "Tracking");
                telemetry.update();


                if (!targetFound) {
                    telemetry.addLine("Target not Found");
                    targetVisible = false;
                    for (VuforiaTrackable trackable : allTrackables) {
                        if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                            telemetry.addData("Visible Target", trackable.getName());
                            targetVisible = true;

                            if (trackable.getName().matches("Blue-Rover")) {
                                BlueRoverV = true;
                            }
                            if (trackable.getName().matches("Red-Footprint")) {
                                RedFootprintV = true;
                            }
                            if (trackable.getName().matches("Front-Craters")) {
                                FrontCratersV = true;
                            }
                            if (trackable.getName().matches("Back-Space")) {
                                BackSpaceV = true;
                            }
                            // getUpdatedRobotLocation() will return null if no new information is available since
                            // the last time that call was made, or if the trackable is not currently visible.
                            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                            if (robotLocationTransform != null) {
                                lastLocation = robotLocationTransform;
                            }
                            break;
                        }
                    }
                    // Provide feedback as to where the robot is located (if we know).
                    if (targetVisible) {
                        // express position (translation) of robot in mm.
                        VectorF translation = lastLocation.getTranslation();
                        telemetry.addData("Pos (mm)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                                translation.get(0), translation.get(1), translation.get(2));

                        // express the rotation of the robot in degrees.
                        Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                        telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);

                        FindTarget = true;
                        robot.stopRobot();
                        telemetry.addLine("Stop Target is visible");
                        telemetry.update();
                    } else {
                        telemetry.addData("Visible Target", "none");
                        //if (FindTarget == false) {
                       // robot.turnAroundUntilFound();
                        robot.MoveTillEnd();
                        telemetry.addLine("Turn around");
                        telemetry.update();
                        //}
                    }
                }
                if (FindTarget) {
                    if (BlueRoverV || RedFootprintV) {
                        robot.stopRobot();
                        telemetry.addLine("Stop Blue or Red");
                        telemetry.update();
                        robot.MoveFromCrator();
                    }
                    if (BackSpaceV || FrontCratersV) {
                        robot.stopRobot();
                        telemetry.addLine("Stop Back Space or Front Crater");
                        telemetry.update();
                        robot.MoveFromCorner();
                    }
                }
                telemetry.update();
            }
        }

    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        telemetry.addLine("Tensorflow init");
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
        telemetry.update();
    }
}

