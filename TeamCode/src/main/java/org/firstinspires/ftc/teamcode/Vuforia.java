package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "Vuforia")
public class Vuforia extends LinearOpMode {

    VuforiaLocalizer vuforiaLocalizer;
    VuforiaLocalizer.Parameters parameters;
    VuforiaTrackables visionTargets;
    VuforiaTrackable target1;
    VuforiaTrackable target2;
    VuforiaTrackable target3;
    VuforiaTrackable target4;
    VuforiaTrackableDefaultListener listener;

    OpenGLMatrix lastKnownLocation;
    OpenGLMatrix phoneLocation;

    public static final String VUFORIA_Key = "Adt99vT/////AAABmQfaL1Gc6k6YpSV4p0gyJUg3w2FZlS8RqVrXnweJXsLcl6JrGb5Age+Cv4I9IS+9XG2ZMhWR19WkeOkWkrTXMzKjblOZGI0FC/WUj9CXpGB7wTS8qQuNHut0NT3aZzPjx3aNnjfUCmBvwrCcVHgvLBLU460n9TE9Yug17HApyE+ix9xcJ2J5QtVejR9PNm8SNmpFAEyGmSasukJHF0Em7cNrHAsR5MxPSCBAnQA3B2pL5onCRotpWAu0rcOCYfXWrHeVtYAEHzm3GdFRj73cQ3eGFgOCHJSyMC0AhHH0M8cFlltdKc0f08FHioKrXu8OpvLGCTtYMSZ6Jq2we4+keaVEPBZY6U4YBffLK7jQnP1p";

    public void runOpMode() throws InterruptedException{

        setupVuforia();
        lastKnownLocation = createMatrix(0, 225, 0, 90, 0, 0);

        waitForStart();
        visionTargets.activate();

        while (opModeIsActive()){

            OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();
            if(latestLocation != null) {
                lastKnownLocation = latestLocation;
                telemetry.addData("Tracking" + target1.getName(), listener.isVisible());
                telemetry.addData("Tracking" + target2.getName(), listener.isVisible());
                telemetry.addData("Tracking" + target3.getName(), listener.isVisible());
                telemetry.addData("Tracking" + target4.getName(), listener.isVisible());
                telemetry.addData("Last known location", formatMatrix(latestLocation));
            }
            else {
                telemetry.addData("Tracking" + target1.getName(), listener.isVisible());
                telemetry.addData("Tracking" + target2.getName(), listener.isVisible());
                telemetry.addData("Tracking" + target3.getName(), listener.isVisible());
                telemetry.addData("Tracking" + target4.getName(), listener.isVisible());
            }

            telemetry.update();
            idle();
        }
    }
    public void setupVuforia(){

        parameters = new VuforiaLocalizer.Parameters(R.id. cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_Key;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.useExtendedTracking = false;
        vuforiaLocalizer = ClassFactory.createVuforiaLocalizer(parameters);

        visionTargets = vuforiaLocalizer.loadTrackablesFromAsset("RoverRuckus");
        Vuforia.setHint (HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);


        target1 = visionTargets.get(0);
        target2 = visionTargets.get(0);
        target3 = visionTargets.get(0);
        target4 = visionTargets.get(0);
        target1.setName("BluePerimeter");
        target2.setName("RedPerimeter");
        target3.setName("FrontPerimeter");
        target4.setName("BackPerimeter");
        target1.setLocation(createMatrix(0, 500, 0, 90, 0, 90));
        target2.setLocation(createMatrix(0, 500, 0, 90, 0, 180));
        target3.setLocation(createMatrix(0, 500, 0, 90, 0, 270));
        target4.setLocation(createMatrix(0, 500, 0, 90, 0, 360));
        phoneLocation = createMatrix(0, 225, 0, 90, 0, 0);
        listener = (VuforiaTrackableDefaultListener) target1.getListener();
        listener.setPhoneInformation(phoneLocation, parameters.cameraDirection);
    }

    private static void setHint(int hintMaxSimultaneousImageTargets, int i) {
    }

    public OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w){

        return OpenGLMatrix.translation(x, y, z).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC,
                AxesOrder.XYZ, AngleUnit.DEGREES, u, v, w ));
    }
     public String formatMatrix(OpenGLMatrix matrix){
        return matrix.formatAsTransform();
    }
}
