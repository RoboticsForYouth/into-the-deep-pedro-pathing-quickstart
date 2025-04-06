package az.itd.teleop;//package org.firstinspires.ftc.teamcode.az.itd.teleop;
//
//import com.arcrobotics.ftclib.drivebase.MecanumDrive;
//import com.arcrobotics.ftclib.gamepad.GamepadEx;
//import com.arcrobotics.ftclib.hardware.motors.Motor;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.az.itd.tools.CandyCane;
//import org.firstinspires.ftc.teamcode.az.itd.tools.DoubleArm;
//import org.firstinspires.ftc.teamcode.az.itd.tools.SpecimenTool;
//import org.firstinspires.ftc.teamcode.az.itd.tools.AZUtil;
//
//
//public class SpecimenTeleOpCombined extends LinearOpMode {
//    static final boolean FIELD_CENTRIC = false;
//
//
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
//
//
//        GamepadEx driverOp = new GamepadEx(gamepad1);
//
//
//        drive = new MecanumDrive(
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
//        waitForStart();
//
//        specimenTool.teleOpSpecimenToolInit();
//
//        currentSampleState.execute(specimenTool);
//        currentSpecimenState.execute(specimenTool);
//        currentHangState.execute(specimenTool);
//
//
//        while (!isStopRequested()) {
//
//            if (gamepad1.a) { //x
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
//            if (gamepad1.b) { //circle
//                if(!buttonBProcessing){
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
//            if(gamepad1.dpad_down){
//                if(!dpadDownProcessing) {
//                    AZUtil.runInParallel(new Runnable() {
//                        @Override
//                        public void run() {
//                            dpadDownProcessing = true;
//                            cycleToNextHangState();
//                            currentHangState.execute(specimenTool);
//                            dpadDownProcessing = false;
//                        }
//                    });
//                }
//
//            }
//
//            //Sample
//
//            if(gamepad1.right_bumper){
//                if(!rightBumperProcessing) {
//                    AZUtil.runInParallel(new Runnable() {
//                        @Override
//                        public void run() {
//                            rightBumperProcessing = true;
//                            cycleToNextStateSample();
//                            currentSampleState.execute(specimenTool);
//                            rightBumperProcessing = false;
//                        }
//                    });
//                    //specimenTool.collect();
//                }
//
//            }
//
//            //Specimen
//
//            if(gamepad1.y){
//                if(!buttonYProcessing) {
//
//                    AZUtil.runInParallel(new Runnable() {
//                        @Override
//                        public void run() {
//                            //specimenTool.collect();
//                            buttonYProcessing = true;
//                            cycleToNextSpecimenState();
//                            currentSpecimenState.execute(specimenTool);
//                            buttonYProcessing = false;
//                        }
//                    });
//                }
//
//
//            }
//
//            if(gamepad1.right_trigger > 0){
//                //if not processing then perform this operation
//                if( !rightTriggerProcessing) {
//                    AZUtil.runInParallel(new Runnable() {
//                        @Override
//                        public void run() {
//                            rightTriggerProcessing = true;
//                            specimenTool.teleOpSlidesExtend(gamepad1.right_trigger);
//                            rightTriggerProcessing = false;
//                        }
//                    });
//                }
//            }
//
//            if(gamepad1.x){ //square
//                if(!buttonXProcessing){
//                    AZUtil.runInParallel(new Runnable() {
//                        @Override
//                        public void run() {
//                            buttonXProcessing = true;
//
//                            if(arm.getCurrentPosition() < 500) {
//                                specimenTool.teleOpCollectVertical();
//
//                            }
//                            else {
//                                //change order of resetPos to ensure that slides do not hit the basket
//                                specimenTool.teleOpHighResetVertical();
//                            }
//                            buttonXProcessing = false;
//                        }
//                    });
//                }
//            }
//
//            if(gamepad1.left_bumper){
//                if( !leftBumperProcessing){
//                    AZUtil.runInParallel(new Runnable() {
//                        @Override
//                        public void run() {
//                            leftBumperProcessing = true;
//                            arm.moveDown();
//                            leftBumperProcessing = false;
//                        }
//                    });
//                }
//
//            }
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
//
//        SpecimenTool.SampleState[] states = SpecimenTool.SampleState.values();
//        int nextStateOrdinal = (currentSampleState.ordinal() + 1) % states.length;
//        currentSampleState = states[nextStateOrdinal];
//    }
//
//    private void cycleToNextSpecimenState(){
//        SpecimenTool.SpecimenState[] states = SpecimenTool.SpecimenState.values();
//        int nextStateOrdinal = (currentSpecimenState.ordinal() + 1) % states.length;
//        currentSpecimenState = states[nextStateOrdinal];
//
//    }
//
//    private void cycleToNextHangState(){
//        SpecimenTool.HangState[] states = SpecimenTool.HangState.values();
//        int nextStateOrdinal = (currentHangState.ordinal() + 1) % states.length;
//        currentHangState = states[nextStateOrdinal];
//    }
//
//
//}
