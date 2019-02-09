package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
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
    VuforiaTrackable target;
    VuforiaTrackableDefaultListener listener;

    OpenGLMatrix lastKnownLocation;
    OpenGLMatrix phoneLocation;

    public static final String VUFORIA_Key = "Adt99vT/////AAABmQfaL1Gc6k6YpSV4p0gyJUg3w2FZlS8RqVrXnweJXsLcl6JrGb5Age+Cv4I9IS+9XG2ZMhWR19WkeOkWkrTXMzKjblOZGI0FC/WUj9CXpGB7wTS8qQuNHut0NT3aZzPjx3aNnjfUCmBvwrCcVHgvLBLU460n9TE9Yug17HApyE+ix9xcJ2J5QtVejR9PNm8SNmpFAEyGmSasukJHF0Em7cNrHAsR5MxPSCBAnQA3B2pL5onCRotpWAu0rcOCYfXWrHeVtYAEHzm3GdFRj73cQ3eGFgOCHJSyMC0AhHH0M8cFlltdKc0f08FHioKrXu8OpvLGCTtYMSZ6Jq2we4+keaVEPBZY6U4YBffLK7jQnP1p";

    public void runOpMode() throws InterruptedException{

        waitForStart();

        while (opModeIsActive()){

            telemetry.update();
            idle();
        }
    }
    public void setupVuforia(){

        parameters = new VuforiaLocalizer.Parameters(R.id. cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_Key;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforiaLocalizer = ClassFactory.createVuforiaLocalizer(parameters);

        visionTargets = vuforiaLocalizer.loadTrackablesFromAsset("RoverRuckus");

        target = visionTargets.get(0);
        target.setName("BluePerimeter");
    }
    public OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w){

        return OpenGLMatrix.translation(x, y, z).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC))
    }
}
