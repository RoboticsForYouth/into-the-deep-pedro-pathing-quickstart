package az.itd.auto;//package org.firstinspires.ftc.teamcode.az.itd.auto;
//
//
//import androidx.annotation.NonNull;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.ParallelAction;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.SequentialAction;
//import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
//import com.acmerobotics.roadrunner.TurnConstraints;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.teamcode.az.itd.tools.CandyCane;
//import org.firstinspires.ftc.teamcode.az.itd.tools.DoubleArm;
//import org.firstinspires.ftc.teamcode.az.itd.tools.SpecimenTool;
//import org.firstinspires.ftc.teamcode.az.itd.tools.AZUtil;
//import org.firstinspires.ftc.teamcode.az.sample.MecanumDrive;
//
//
//@Config
//@Autonomous
//public class RightAutoSingleSpecimenTest extends LinearOpMode {
//
//    TurnConstraints turnConstraints = new TurnConstraints(
//            (Math.PI)*1.5,
//            -(Math.PI)*1.5,
//            (Math.PI)*1.5);
//
//
//
//
//
//    public static final double SPECIMEN_DROP_POS_XXXX = 25.2;
//    public static final int SPECIMEN_DROP_POS_YYYY = 13;
//    public static final int SPECIMEN_DROP_POS_HEADING = 180;
//    public static final int SPIKE_MARK_POS_XXXX = 20;
//    public static final int OBS_ZONE_POS_XXXX = 11;
//    public static final int OBS_ZONE_POS_YYYY = -33;
//    public static final double OBS_ZONE_POS_1_XXXX = 5.5;
//    public static final int OBS_ZONE_DROP_HEADING = -140;
//    ElapsedTime runtime = new ElapsedTime();
//
//
//
//    SpecimenTool specimenTool = null;
//    CandyCane candyCane = null;
//    private MecanumDrive drive;
//    private Pose2d beginPose;
//    private Action specimenDropPos1;
//    private Action observationZonePos1;
//    private Action observationZonePos1_1;
//    private Action specimenDropPos2;
//    private Action parkPos;
//    private Action specimenToolDrop;
//    private Action armDrop;
//    private Action slidesReset;
//    private Action readyToDropSpecimen;
//    private Action park;
//    private Action armGripperReset;
//    private Action releaseSpecimenAction;
//    private Action specimenCollectAction;
//    private Action spikeMarkPos1;
//    private Action lowerCandyCane;
//    private Action raiseCandyCane;
//    private Action spikeMarkPos3;
//
//    private Action observationZoneDropPos1;
//    private Action move;
//    private Action drop;
//    private Action spikeMarkPos2;
//    private Action observationZoneDropPos2;
//    private Action observationZoneDropPos3;
//    private Action resetCandyCane;
//    private Action observationZonePos2;
//    private Action observationZonePos2_1;
//    private Action specimenDropPos3;
//    private Action observationZonePos3;
//    private Action observationZonePos3_1;
//    private Action specimenDropPos4;
//    private Action observationZonePos4;
//    private Action observationZonePos4_1;
//    private Action specimenDropPos5;
//    private Action specimenToolDropAfterPickup;
//    private Action lowerCandyCaneAfterDrop;
//    private Action afterDropSpecimenCollectAction;
//    private Action firstReleaseSpecimenAction;
//
//
//
//    public class specimenHang implements Action {
//
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//
//
//            specimenTool.teleOpSpecimenCollect();
//            sleep(1000);
//
//
//
//            return false;
//        }
//    }
//
//    public Action specimenHang() {
//        return new specimenHang();
//    }
//
//    public void initAuto() {
//        specimenTool = new SpecimenTool(this);
//        specimenTool.rightAutoReset();
//
//        candyCane = new CandyCane((this));
//
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//        runtime.reset();
//        beginPose = new Pose2d(0,0,Math.toRadians(SPECIMEN_DROP_POS_HEADING));
//        drive = new MecanumDrive(hardwareMap, beginPose);
//        telemetry.addData("current position", drive.pose);
//        telemetry.update();
//        createActions();
//    }
//
//    public void createActions() {
//        TrajectoryActionBuilder specimenDropTraj1 = drive.actionBuilder(beginPose)
//                .strafeToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX, SPECIMEN_DROP_POS_YYYY));
//        //.splineToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX, SPECIMEN_DROP_POS_YYYY), Math.toRadians(SPECIMEN_DROP_POS_HEADING));
//        specimenDropPos1 = specimenDropTraj1.build();
//
//
//
//        TrajectoryActionBuilder spikeMarkTraj1 = specimenDropTraj1.endTrajectory().fresh()
//                .strafeToLinearHeading(new Vector2d(SPIKE_MARK_POS_XXXX-3, -24), Math.toRadians(-30));
////                .splineToLinearHeading(new Pose2d(SPIKE_MARK_POS_XXXX-3, -24, Math.toRadians(-30)), 0);
//        spikeMarkPos1 = spikeMarkTraj1.build();
//
//        TrajectoryActionBuilder observationZoneDropTraj1 = spikeMarkTraj1.endTrajectory().fresh()
//                .turnTo(Math.toRadians(OBS_ZONE_DROP_HEADING+5), turnConstraints);
//        observationZoneDropPos1 = observationZoneDropTraj1.build();
//
//        TrajectoryActionBuilder spikeMarkTraj2 = observationZoneDropTraj1.endTrajectory().fresh()
//                .splineToLinearHeading(new Pose2d(SPIKE_MARK_POS_XXXX+1, OBS_ZONE_POS_YYYY, Math.toRadians(-37)), 0);
//        spikeMarkPos2 = spikeMarkTraj2.build();
//
//        TrajectoryActionBuilder observationZoneDropTraj2 = spikeMarkTraj2.endTrajectory().fresh()
//                .turnTo(Math.toRadians(OBS_ZONE_DROP_HEADING+5), turnConstraints);
//        observationZoneDropPos2 = observationZoneDropTraj2.build();
//
//        TrajectoryActionBuilder spikeMarkTraj3 = observationZoneDropTraj2.endTrajectory().fresh()
//                .splineToLinearHeading(new Pose2d(SPIKE_MARK_POS_XXXX+1, -37.5, Math.toRadians(-45)), 0);
//        spikeMarkPos3 = spikeMarkTraj3.build();
//
//        TrajectoryActionBuilder observationZoneDropTraj3 = spikeMarkTraj3.endTrajectory().fresh()
//                .turnTo(Math.toRadians(OBS_ZONE_DROP_HEADING), turnConstraints);
//        observationZoneDropPos3 = observationZoneDropTraj3.build();
//
//
//        TrajectoryActionBuilder observationZoneTraj1 = observationZoneDropTraj3.endTrajectory().fresh()
//                .strafeToLinearHeading(new Vector2d(OBS_ZONE_POS_1_XXXX, OBS_ZONE_POS_YYYY), Math.toRadians(SPECIMEN_DROP_POS_HEADING));
////                .splineToLinearHeading(new Pose2d(OBS_ZONE_POS_XXXX, OBS_ZONE_POS_YYYY, Math.toRadians(SPECIMEN_DROP_POS_HEADING)), 0);
//        observationZonePos1 = observationZoneTraj1.build();
//
//
//
////        TrajectoryActionBuilder observationZoneTraj1_1 = observationZoneTraj1.endTrajectory().fresh()
////                .lineToX(OBS_ZONE_POS_1_XXXX);
////        observationZonePos1_1 = observationZoneTraj1_1.build();
//
//
//
//        TrajectoryActionBuilder specimenDropTraj2 = observationZoneTraj1.endTrajectory().fresh()
//                .strafeToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX+0.1, SPECIMEN_DROP_POS_YYYY));
//        //.splineToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX+0.1, SPECIMEN_DROP_POS_YYYY), Math.toRadians(SPECIMEN_DROP_POS_HEADING));
//        specimenDropPos2 = specimenDropTraj2.build();
//
//        TrajectoryActionBuilder observationZoneTraj2 = specimenDropTraj2.endTrajectory().fresh()
////                .splineToLinearHeading(new Pose2d(OBS_ZONE_POS_XXXX, OBS_ZONE_POS_YYYY, Math.toRadians(SPECIMEN_DROP_POS_HEADING)), 0);
//                .strafeToConstantHeading(new Vector2d(OBS_ZONE_POS_1_XXXX, OBS_ZONE_POS_YYYY));
//        observationZonePos2 = observationZoneTraj2.build();
//
////        TrajectoryActionBuilder observationZoneTraj2_1 = observationZoneTraj2.endTrajectory().fresh()
////                .lineToX(OBS_ZONE_POS_1_XXXX);
////        observationZonePos2_1 = observationZoneTraj2_1.build();
//
//        TrajectoryActionBuilder specimenDropTraj3 = observationZoneTraj2.endTrajectory().fresh()
//                .strafeToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX+0.2, SPECIMEN_DROP_POS_YYYY));
//        //.splineToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX+0.2, SPECIMEN_DROP_POS_YYYY), Math.toRadians(SPECIMEN_DROP_POS_HEADING));
//        specimenDropPos3 = specimenDropTraj3.build();
//
//        TrajectoryActionBuilder observationZoneTraj3 = specimenDropTraj3.endTrajectory().fresh()
////                .splineToLinearHeading(new Pose2d(OBS_ZONE_POS_XXXX, OBS_ZONE_POS_YYYY, Math.toRadians(SPECIMEN_DROP_POS_HEADING)), 0);
//                .strafeToConstantHeading(new Vector2d(OBS_ZONE_POS_1_XXXX, OBS_ZONE_POS_YYYY));
//        observationZonePos3 = observationZoneTraj3.build();
//
////        TrajectoryActionBuilder observationZoneTraj3_1 = observationZoneTraj3.endTrajectory().fresh()
////                .lineToX(OBS_ZONE_POS_1_XXXX);
////        observationZonePos3_1 = observationZoneTraj3_1.build();
//
//        TrajectoryActionBuilder specimenDropTraj4 = observationZoneTraj3.endTrajectory().fresh()
//                .strafeToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX+0.3, SPECIMEN_DROP_POS_YYYY));
//        //.splineToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX+0.3, SPECIMEN_DROP_POS_YYYY), Math.toRadians(SPECIMEN_DROP_POS_HEADING));
//        specimenDropPos4 = specimenDropTraj4.build();
//
//        TrajectoryActionBuilder observationZoneTraj4 = specimenDropTraj4.endTrajectory().fresh()
////                .splineToLinearHeading(new Pose2d(OBS_ZONE_POS_XXXX, OBS_ZONE_POS_YYYY, Math.toRadians(SPECIMEN_DROP_POS_HEADING)), 0);
//                .strafeToConstantHeading(new Vector2d(OBS_ZONE_POS_1_XXXX, OBS_ZONE_POS_YYYY));
//        observationZonePos4 = observationZoneTraj4.build();
//
////        TrajectoryActionBuilder observationZoneTraj4_1 = observationZoneTraj4.endTrajectory().fresh()
////                .lineToX(OBS_ZONE_POS_1_XXXX);
////        observationZonePos4_1 = observationZoneTraj4_1.build();
//
//        TrajectoryActionBuilder specimenDropTraj5 = observationZoneTraj4.endTrajectory().fresh()
//                .strafeToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX+0.4, SPECIMEN_DROP_POS_YYYY));
//        //.splineToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX+0.4, SPECIMEN_DROP_POS_YYYY), Math.toRadians(SPECIMEN_DROP_POS_HEADING));
//        specimenDropPos5 = specimenDropTraj5.build();
//
//
//
//
//
//
//
//        parkPos = drive.actionBuilder(drive.pose)
//                .splineToConstantHeading(new Vector2d(3, -36), Math.toRadians(0))
//                .build();
//
//
//        firstReleaseSpecimenAction = new Action(){
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                AZUtil.runInParallel(new Runnable() {
//                    @Override
//                    public void run() {
//                        sleep(500);
//
//                        specimenTool.gripper.specimenDropPos();
//
//                        specimenTool.specimenDrop();
//                    }
//                });
//                return false;
//            }
//        };
//
//        releaseSpecimenAction = new Action(){
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.specimenDrop();
//                return false;
//            }
//        };
//
//        specimenCollectAction = new Action(){
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.rightAutoSpecimenCollect();
//                return false;
//            }
//        };
//
//        afterDropSpecimenCollectAction = new Action(){
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                AZUtil.runInParallel(new Runnable() {
//                    @Override
//                    public void run() {
//                        sleep(2000);
//                        specimenTool.rightAutoSpecimenCollect();
//                    }
//                });
//                return false;
//            }
//        };
//
//
//
//        specimenToolDrop = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                AZUtil.runInParallel(new Runnable() {
//                    @Override
//                    public void run() {
//                        specimenTool.rightAutoSpecimenHangPos();
//                    }
//                });
//                return false;
//            }
//        };
//
//        specimenToolDropAfterPickup = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//
//                specimenTool.arm.setPosAndWait((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP_INTEMEDIATE_WAIT.getValue());
//
//                AZUtil.runInParallel(new Runnable() {
//                    @Override
//                    public void run() {
//                        sleep(2000);
//                        specimenTool.rightAutoSpecimenHangPos();
//                    }
//                });
//
//                return false;
//            }
//        };
//
//
//        lowerCandyCane = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                candyCane.rightAutoLower();
//                return false;
//            }
//        };
//
//
//
//        raiseCandyCane = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                candyCane.rightAutoRaise();
//                return false;
//            }
//        };
//
//        resetCandyCane = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                candyCane.reset();
//                return false;
//            }
//        };
//
//        move = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.gripper.autoMoveAfterSpecimenCollect();
//                return false;
//            }
//        };
//
//        drop = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.gripper.drop();
//                return false;
//            }
//        };
//
//
//
////        readyToDropSpecimen = new ParallelAction(specimenToolDrop, specimenDropPos);
//
//
//
//
//
//        armDrop = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.arm.specimenDrop();
//                return false;
//            }
//        };
//
//
//        slidesReset = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.slides.resetPos();
//                return false;
//            }
//        };
//
//
//        armGripperReset = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.arm.reset();
//                specimenTool.gripper.reset();
//                return false;
//            }
//        };
//
//
//        park = new ParallelAction(armGripperReset, parkPos);
//    }
//
//
//    public void runOpMode() throws InterruptedException {
//        initAuto();
//        waitForStart();
//        specimenTool.gripper.stopPower();
//
//        Actions.runBlocking(
//                new SequentialAction(
////                        firstReleaseSpecimenAction,
//                        specimenDropPos1,
//
////                        raiseCandyCane,
//                        spikeMarkPos1
////                        lowerCandyCane
////                        new SleepAction(0.5),
////                        observationZoneDropPos1,
////
////                        raiseCandyCane,
////                        spikeMarkPos2,
////                        lowerCandyCane,
////                        new SleepAction(0.5),
////                        observationZoneDropPos2,
////
////                        raiseCandyCane,
////                        spikeMarkPos3,
////                        lowerCandyCane,
////                        new SleepAction(0.5),
////                        observationZoneDropPos3,
////
////                        resetCandyCane,
////                        observationZonePos1,
//////                        specimenCollectAction,
////                        //observationZonePos1_1,
//////                        specimenToolDropAfterPickup,
////                        specimenDropPos2,
//////                        releaseSpecimenAction,
////
//////                        afterDropSpecimenCollectAction,
////                        observationZonePos2,
////                        //observationZonePos2_1,
//////                        specimenToolDropAfterPickup,
////                        specimenDropPos3,
//////                        releaseSpecimenAction,
////
//////                        afterDropSpecimenCollectAction,
////                        observationZonePos3,
////                        //observationZonePos3_1,
//////                        specimenToolDropAfterPickup,
////                        specimenDropPos4,
//////                        releaseSpecimenAction,
//////
//////                        afterDropSpecimenCollectAction,
////                        observationZonePos4,
////                        //observationZonePos4_1,
//////                        specimenToolDropAfterPickup,
////                        specimenDropPos5
//////                        releaseSpecimenAction
//
//                )
//        );
////
//        sleep(5000);
//        telemetry.addData("current position",drive.pose);
//        telemetry.update();
//        sleep(10000);
//
//
//
//    }
//
//}
