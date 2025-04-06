package az.itd.teleop;//package org.firstinspires.ftc.teamcode.az.itd.teleop;
//
//import com.arcrobotics.ftclib.drivebase.MecanumDrive;
//import com.arcrobotics.ftclib.gamepad.GamepadEx;
//import com.arcrobotics.ftclib.hardware.motors.Motor;
//import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.Gamepad;
//import com.qualcomm.robotcore.hardware.IMU;
//
//import org.firstinspires.ftc.teamcode.az.itd.tools.CandyCane;
//import org.firstinspires.ftc.teamcode.az.itd.tools.DoubleArm;
//import org.firstinspires.ftc.teamcode.az.itd.tools.SpecimenTool;
//import org.firstinspires.ftc.teamcode.az.itd.tools.AZUtil;
//
//
//public class SpecimenTeleOp extends LinearOpMode {
//    static final boolean FIELD_CENTRIC = false;
//    Gamepad currentGamepad1 = new Gamepad();
//    Gamepad currentGamepad2 = new Gamepad();
//
//    Gamepad previousGamepad1 = new Gamepad();
//    Gamepad previousGamepad2 = new Gamepad();
//
//
//    DoubleArm arm = null;
//    CandyCane candyCane = null;
//
//    private boolean gamepad2DpadUpProcessing;
//    private boolean gamepad2dpadDownProcessing;
//    private boolean dpadUpProcessing;
//    private boolean gamepad2DpadDownProcessing;
//    private boolean dpadRightProcessing;
//    private boolean dpadLeftProcessing;
//
//    private boolean buttonAProcessing;
//    private boolean gamepad2ButtonAProcessing;
//
//    private boolean buttonBProcessing;
//    private boolean buttonXProcessing;
//    private boolean buttonYProcessing;
//    private boolean rightTriggerProcessing;
//    private boolean leftTriggerProcessing;
//    private boolean rightBumperProcessing;
//    private boolean leftBumperProcessing;
//    private boolean dpadDownProcessing;
//
//
//    private MecanumDrive drive;
//    private SpecimenTool specimenTool;
//
//    private SpecimenTool.SpecimenState currentSpecimenState;
//    private SpecimenTool.SampleState currentSampleState;
//
//    private SpecimenTool.HangState currentHangState;
//    private boolean previousButtonState = false;
//
//    //I want to be able to execute commands after a specified delay. the commands
//    @Override
//    public void runOpMode() throws InterruptedException {
//        // Store the gamepad values from the previous loop iteration in
//        // previousGamepad1/2 to be used in this loop iteration.
//        // This is equivalent to doing this at the end of the previous
//        // loop iteration, as it will run in the same order except for
//        // the first/last iteration of the loop.
//        previousGamepad1.copy(currentGamepad1);
//        previousGamepad2.copy(currentGamepad2);
//
//        // Store the gamepad values from this loop iteration in
//        // currentGamepad1/2 to be used for the entirety of this loop iteration.
//        // This prevents the gamepad values from changing between being
//        // used and stored in previousGamepad1/2.
//        currentGamepad1.copy(gamepad1);
//        currentGamepad2.copy(gamepad2);
//
//        GamepadEx driverOp = new GamepadEx(gamepad1);
//
//
//        MecanumDrive drive = new MecanumDrive(
//                new Motor(hardwareMap, "frontLeft", Motor.GoBILDA.RPM_435),
//                new Motor(hardwareMap, "frontRight", Motor.GoBILDA.RPM_435),
//                new Motor(hardwareMap, "backLeft", Motor.GoBILDA.RPM_435),
//                new Motor(hardwareMap, "backRight", Motor.GoBILDA.RPM_435)
//        );
//
//        arm = new DoubleArm(this);
//        specimenTool = new SpecimenTool(this);
//        candyCane = new CandyCane(this);
//
//        currentSampleState = SpecimenTool.SampleState.BASE;
//        currentSpecimenState = SpecimenTool.SpecimenState.BASE;
//        currentHangState = SpecimenTool.HangState.BASE;
//        //currentSampleState = SpecimenTool.SampleState.COLLECT;
//        specimenTool = new SpecimenTool(this);
//
//        IMU imu = hardwareMap.get(IMU.class, "imu");
//        // Adjust the orientation parameters to match your robot
//        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
//                RevHubOrientationOnRobot.LogoFacingDirection.UP,
//                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
//        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
//        imu.initialize(parameters);
//
//        waitForStart();
//        currentSampleState.execute(specimenTool);
//        currentSpecimenState.execute(specimenTool);
//        currentHangState.execute(specimenTool);
//
//
//        while (opModeIsActive()) {
//
//            previousGamepad1.copy(currentGamepad1);
//            previousGamepad2.copy(currentGamepad2);
//
//            currentGamepad1.copy(gamepad1);
//            currentGamepad2.copy(gamepad2);
//
//            if (currentGamepad1.a) { //x
//                if(!buttonAProcessing ){
//                    AZUtil.runInParallel(new Runnable() {
//                        @Override
//                        public void run() {
//                            buttonAProcessing = true;
//                            if(arm.getCurrentPosition() < 500) {
//
//                                specimenTool.teleOpCollect();
//                            }
//
//                            else {
//                                specimenTool.teleOpHighReset();
//                            }
//
//                            currentSampleState = SpecimenTool.SampleState.BASE;
//                            currentSpecimenState = SpecimenTool.SpecimenState.BASE;
//                            currentHangState = SpecimenTool.HangState.BASE;
//                            buttonAProcessing = false;
//                        }
//                    });
//                }
//            }
//
//
//            //drop the specimen
//            if (currentGamepad1.b && !previousGamepad1.b) { //circle
//                if(!buttonBProcessing){
//
//                    AZUtil.runInParallel(new Runnable() {
//                        @Override
//                        public void run() {
//                            buttonBProcessing = true;
//                            specimenTool.teleOpEject();
//                            buttonBProcessing = false;
//                        }
//                    });
//
//
//                }
//            }
//
//            if(currentGamepad1.dpad_down & !previousGamepad1.dpad_down){
//                AZUtil.runInParallel(new Runnable() {
//                    @Override
//                    public void run() {
//                        cycleToNextHangState();
//                        currentHangState.execute(specimenTool);
//                    }
//                });
//
//            }
//
//            //Sample
//
//            if(currentGamepad1.right_bumper && !previousGamepad1.right_bumper){
//                AZUtil.runInParallel(new Runnable() {
//                    @Override
//                    public void run() {
//                        cycleToNextStateSample();
//                        currentSampleState.execute(specimenTool);
//                    }
//                });
//                //specimenTool.collect();
//
//            }
//
//            //Specimen
//
//            if(currentGamepad1.y && !previousGamepad1.y){
//
//                AZUtil.runInParallel(new Runnable() {
//                    @Override
//                    public void run() {
//                        //specimenTool.collect();
//                        cycleToNextSpecimenState();
//                        currentSpecimenState.execute(specimenTool);
//                    }
//                });
//
//
//            }
//
//
//
//
//            /* if(gamepad1.b){
//                specimenTool.setGrabAndLiftSpecimenPos();
//            }
//            if(gamepad1.x){
//                specimenTool.setResetPos();
//            }
//            if( gamepad1.y){
//                specimenTool.setSpecimenDropPos();
//            }
//
//            if( gamepad1.right_bumper){
//                specimenTool.setSpecimenClipPos();
//            }
//
//            //set specimen tool to current position
//            specimenTool.setCurrentPos();
//            */
//
//                drive.driveRobotCentric(
//                        -driverOp.getLeftX(),
//                        -driverOp.getLeftY(),
//                        -driverOp.getRightX(),
//                        false
//                );
//            }
//
//        }
//
//    private void cycleToNextStateSample(){
//        // Cycle to the next state in the SampleState enum
//        SpecimenTool.SampleState[] states = SpecimenTool.SampleState.values();
//        int nextStateOrdinal = (currentSampleState.ordinal() + 1) % states.length;  // Loop back to the first state
//        currentSampleState = states[nextStateOrdinal];
//    }
//
//    private void cycleToNextSpecimenState(){
//        SpecimenTool.SpecimenState[] states = SpecimenTool.SpecimenState.values();
//        int nextStateOrdinal = (currentSpecimenState.ordinal() + 1) % states.length;  // Loop back to the first state
//        currentSpecimenState = states[nextStateOrdinal];
//
//    }
//
//    private void cycleToNextHangState(){
//        SpecimenTool.HangState[] states = SpecimenTool.HangState.values();
//        int nextStateOrdinal = (currentHangState.ordinal() + 1) % states.length;  // Loop back to the first state
//        currentHangState = states[nextStateOrdinal];
//
//    }
//
//
//}
