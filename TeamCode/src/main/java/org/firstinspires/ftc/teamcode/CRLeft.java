package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

    @TeleOp(name = "CRLeft", group = "CR")
        public class CRLeft extends LinearOpMode {
        private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
        private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
        private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

        private static final String VUFORIA_KEY = "Adt99vT/////AAABmQfaL1Gc6k6YpSV4p0gyJUg3w2FZlS8RqVrXnweJXsLcl6JrGb5Age+Cv4I9IS+9XG2ZMhWR19WkeOkWkrTXMzKjblOZGI0FC/WUj9CXpGB7wTS8qQuNHut0NT3aZzPjx3aNnjfUCmBvwrCcVHgvLBLU460n9TE9Yug17HApyE+ix9xcJ2J5QtVejR9PNm8SNmpFAEyGmSasukJHF0Em7cNrHAsR5MxPSCBAnQA3B2pL5onCRotpWAu0rcOCYfXWrHeVtYAEHzm3GdFRj73cQ3eGFgOCHJSyMC0AhHH0M8cFlltdKc0f08FHioKrXu8OpvLGCTtYMSZ6Jq2we4+keaVEPBZY6U4YBffLK7jQnP1p";

        /**
         * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
         * localization engine.
         */
        private VuforiaLocalizer vuforia;

        /**
         * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
         * Detection engine.
         */
        private TFObjectDetector tfod;

        boolean blockMove = false;

        @Override
        public void runOpMode() {
            // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
            // first.

            initVuforia();

            if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
                initTfod();
            } else {
                telemetry.addData("Sorry!", "This device is not compatible with TFOD");
            }
            if(blockMove){
                telemetry.addData("Block move","Vakue");
            }

            /** Wait for the game to begin */
            telemetry.addData(">", "Press Play to start tracking");
            telemetry.update();
            waitForStart();

            if (opModeIsActive()) {
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
                                    } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                        telemetry.addData("Gold Mineral Position", "Right");
                                    } else {
                                        telemetry.addData("Gold Mineral Position", "Center");
                                    }
                                }
                            }
                            telemetry.update();
                        }
                    }
                }
            }

            if (tfod != null) {
                tfod.shutdown();
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
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
        }
    }


