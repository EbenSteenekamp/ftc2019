package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

import java.util.ArrayList;
import java.util.List;


@Autonomous(name = "Blue2", group = "Blue")
public class TestA extends LinearOpMode {
    AutoOpRobot robot = new AutoOpRobot();

    boolean blockMove = false;

    private static final float mmFTCFieldWidth = (12 * 6);
    private static final float mmTargetHeight = (6);

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    private OpenGLMatrix lastLocation = null;
    private boolean targetVisible = false;

    boolean FindTarget = false;

    private static final String VUFORIA_KEY = "Adt99vT/////AAABmQfaL1Gc6k6YpSV4p0gyJUg3w2FZlS8RqVrXnweJXsLcl6JrGb5Age+Cv4I9IS+9XG2ZMhWR19WkeOkWkrTXMzKjblOZGI0FC/WUj9CXpGB7wTS8qQuNHut0NT3aZzPjx3aNnjfUCmBvwrCcVHgvLBLU460n9TE9Yug17HApyE+ix9xcJ2J5QtVejR9PNm8SNmpFAEyGmSasukJHF0Em7cNrHAsR5MxPSCBAnQA3B2pL5onCRotpWAu0rcOCYfXWrHeVtYAEHzm3GdFRj73cQ3eGFgOCHJSyMC0AhHH0M8cFlltdKc0f08FHioKrXu8OpvLGCTtYMSZ6Jq2we4+keaVEPBZY6U4YBffLK7jQnP1p";
    private VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

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

        while (opModeIsActive()) {
            if (blockMove = false) {
                robot.VuforiaOpMode();
            }
        }
    }
}
