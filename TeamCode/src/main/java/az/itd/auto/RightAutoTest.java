package az.itd.auto;//package org.firstinspires.ftc.teamcode.az.itd.auto;
//
//
//import androidx.annotation.NonNull;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.SequentialAction;
//import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
//import com.acmerobotics.roadrunner.TurnConstraints;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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
//@Autonomous (preselectTeleOp = "IntoTheDeepTeleOp")
//public class RightAutoTest extends RightAutoPosValues {
//
//    TurnConstraints turnConstraints = new TurnConstraints(
//            (Math.PI)*2,
//            -(Math.PI)*2,
//            (Math.PI)*2);
//
//    TurnConstraints fasterTurnConstraints = new TurnConstraints(
//            (Math.PI)*3,
//            -(Math.PI)*3,
//            (Math.PI)*3);
//
//
//
//
//    public static final int SPECIMEN_DROP_POS_HEADING = 180;
//
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
//    private Action specimenDropPos2;
//    private Action releaseSpecimenAction;
//    private Action specimenCollectAction;
//    private Action spikeMarkPos1;
//    private Action lowerCandyCaneAction;
//    private Action raiseCandyCaneAction;
//    private Action spikeMarkPos3;
//
//    private Action observationZoneDropPos1;
//    private Action spikeMarkPos2;
//    private Action observationZoneDropPos2;
//    private Action observationZoneDropPos3;
//    private Action resetCandyCaneAction;
//    private Action observationZonePos2;
//    private Action specimenDropPos3;
//    private Action observationZonePos3;
//    private Action specimenDropPos4;
//    private Action observationZonePos4;
//    private Action specimenDropPos5;
//    private Action specimenToolDropAfterPickupAction;
//    private Action afterDropSpecimenCollectAction;
//    private Action firstReleaseSpecimenAction;
//    private Action observationZonePos1_1;
//    private Action specimenCollectInParallelAction;
//    private Action parkPos;
//    private Action resetSpecimenToolAction;
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
//                .strafeToConstantHeading(specimenDrop1);
//                //.splineToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX, SPECIMEN_DROP_POS_YYYY), Math.toRadians(SPECIMEN_DROP_POS_HEADING));
//        specimenDropPos1 = specimenDropTraj1.build();
//
//
//
//        TrajectoryActionBuilder spikeMarkTraj1 = specimenDropTraj1.endTrajectory().fresh()
//                .strafeToLinearHeading(spikeMark1, Math.toRadians(SPIKE_MARK_HEADING_1));
////                .splineToLinearHeading(new Pose2d(SPIKE_MARK_POS_XXXX-3, -24, Math.toRadians(-30)), 0);
//        spikeMarkPos1 = spikeMarkTraj1.build();
//
//        TrajectoryActionBuilder observationZoneDropTraj1 = spikeMarkTraj1.endTrajectory().fresh()
//                .turnTo(Math.toRadians(HOCKEY_HEADING_1), fasterTurnConstraints);
//        observationZoneDropPos1 = observationZoneDropTraj1.build();
//
//        TrajectoryActionBuilder spikeMarkTraj2 = observationZoneDropTraj1.endTrajectory().fresh()
//                .strafeToLinearHeading(spikeMark2, Math.toRadians(SPIKE_MARK_HEADING_2));
//                //.splineToLinearHeading(new Pose2d(SPIKE_MARK_POS_XXXX+1, OBS_ZONE_POS_YYYY, Math.toRadians(-37)), 0);
//        spikeMarkPos2 = spikeMarkTraj2.build();
//
//        TrajectoryActionBuilder observationZoneDropTraj2 = spikeMarkTraj2.endTrajectory().fresh()
//                .turnTo(Math.toRadians(HOCKEY_HEADING_2), fasterTurnConstraints);
//        observationZoneDropPos2 = observationZoneDropTraj2.build();
//
//        TrajectoryActionBuilder spikeMarkTraj3 = observationZoneDropTraj2.endTrajectory().fresh()
//                .strafeToLinearHeading(spikeMark3, Math.toRadians(SPIKE_MARK_HEADING_3));
////                .splineToLinearHeading(new Pose2d(SPIKE_MARK_POS_XXXX+3, -37, Math.toRadians(-45)), 0);
//        spikeMarkPos3 = spikeMarkTraj3.build();
//
//        TrajectoryActionBuilder observationZoneDropTraj3 = spikeMarkTraj3.endTrajectory().fresh()
//                .turnTo(Math.toRadians(HOCKEY_HEADING_3), turnConstraints);
//        observationZoneDropPos3 = observationZoneDropTraj3.build();
//
//
//        TrajectoryActionBuilder observationZoneTraj1 = observationZoneDropTraj3.endTrajectory().fresh()
//                .strafeToLinearHeading(obsZone1, Math.toRadians(OBS_ZONE_HEADING_1));
////                .splineToLinearHeading(new Pose2d(OBS_ZONE_POS_XXXX, OBS_ZONE_POS_YYYY, Math.toRadians(SPECIMEN_DROP_POS_HEADING)), 0);
//        observationZonePos1 = observationZoneTraj1.build();
//
//        TrajectoryActionBuilder observationZoneTraj1_1 = observationZoneTraj1.endTrajectory().fresh()
//                .lineToX(OBS_ZONE_1_XXXX);
//        observationZonePos1_1 = observationZoneTraj1_1.build();
//
//
//
//
//
//        TrajectoryActionBuilder specimenDropTraj2 = observationZoneTraj1_1.endTrajectory().fresh()
//                .strafeToConstantHeading(specimenDrop2);
//                //.splineToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX+0.1, SPECIMEN_DROP_POS_YYYY), Math.toRadians(SPECIMEN_DROP_POS_HEADING));
//        specimenDropPos2 = specimenDropTraj2.build();
//
//        TrajectoryActionBuilder observationZoneTraj2 = specimenDropTraj2.endTrajectory().fresh()
////                .splineToLinearHeading(new Pose2d(OBS_ZONE_POS_XXXX, OBS_ZONE_POS_YYYY, Math.toRadians(SPECIMEN_DROP_POS_HEADING)), 0);
//                .strafeToConstantHeading(obsZone2);
//        observationZonePos2 = observationZoneTraj2.build();
//
//
//
//
//
//
//        TrajectoryActionBuilder specimenDropTraj3 = observationZoneTraj2.endTrajectory().fresh()
//                .strafeToConstantHeading(specimenDrop3);
//                //.splineToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX+0.2, SPECIMEN_DROP_POS_YYYY), Math.toRadians(SPECIMEN_DROP_POS_HEADING));
//        specimenDropPos3 = specimenDropTraj3.build();
//
//        TrajectoryActionBuilder observationZoneTraj3 = specimenDropTraj3.endTrajectory().fresh()
////                .splineToLinearHeading(new Pose2d(OBS_ZONE_POS_XXXX, OBS_ZONE_POS_YYYY, Math.toRadians(SPECIMEN_DROP_POS_HEADING)), 0);
//                .strafeToConstantHeading(obsZone3);
//        observationZonePos3 = observationZoneTraj3.build();
//
//
//
//
//
//        TrajectoryActionBuilder specimenDropTraj4 = observationZoneTraj3.endTrajectory().fresh()
//                .strafeToConstantHeading(specimenDrop4);
//                //.splineToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX+0.3, SPECIMEN_DROP_POS_YYYY), Math.toRadians(SPECIMEN_DROP_POS_HEADING));
//        specimenDropPos4 = specimenDropTraj4.build();
//
//        TrajectoryActionBuilder observationZoneTraj4 = specimenDropTraj4.endTrajectory().fresh()
////                .splineToLinearHeading(new Pose2d(OBS_ZONE_POS_XXXX, OBS_ZONE_POS_YYYY, Math.toRadians(SPECIMEN_DROP_POS_HEADING)), 0);
//                .strafeToConstantHeading(obsZone4);
//        observationZonePos4 = observationZoneTraj4.build();
//
//
//
//
//
//        TrajectoryActionBuilder specimenDropTraj5 = observationZoneTraj4.endTrajectory().fresh()
//                .strafeToConstantHeading(specimenDrop5);
//                //.splineToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX+0.4, SPECIMEN_DROP_POS_YYYY), Math.toRadians(SPECIMEN_DROP_POS_HEADING));
//        specimenDropPos5 = specimenDropTraj5.build();
//
//
//        TrajectoryActionBuilder parkTraj = observationZoneTraj4.endTrajectory().fresh()
//                .strafeToConstantHeading(park);
//        //.splineToConstantHeading(new Vector2d(SPECIMEN_DROP_POS_XXXX+0.4, SPECIMEN_DROP_POS_YYYY), Math.toRadians(SPECIMEN_DROP_POS_HEADING));
//        parkPos = parkTraj.build();
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
//                        specimenTool.gripper.rightAutoSpecimenDropPos();
//
//                        specimenTool.rightAutoSpecimenDrop();
//                    }
//                });
//                return false;
//            }
//        };
//
//        releaseSpecimenAction = new Action(){
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.rightAutoSpecimenDrop();
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
//        specimenCollectInParallelAction = new Action(){
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                AZUtil.runInParallel(new Runnable() {
//                    @Override
//                    public void run() {
//                        specimenTool.rightAutoSpecimenCollect();
//
//                    }
//                });
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
//                        sleep(200);
//                        specimenTool.afterDropRightAutoSpecimenCollect();
//                    }
//                });
//                return false;
//            }
//        };
//
//
//
//        specimenToolDropAfterPickupAction = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//
//                specimenTool.arm.setPosAndWait((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP_INTEMEDIATE_WAIT.getValue());
//
//                AZUtil.runInParallel(new Runnable() {
//                    @Override
//                    public void run() {
//                        sleep(1000);
//                        specimenTool.rightAutoSpecimenHangPos();
//                    }
//                });
//
//                return false;
//            }
//        };
//
//
//        lowerCandyCaneAction = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                candyCane.rightAutoLower();
//                return false;
//            }
//        };
//
//
//
//        raiseCandyCaneAction = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                candyCane.rightAutoRaise();
//                return false;
//            }
//        };
//
//        resetCandyCaneAction = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                candyCane.reset();
//                return false;
//            }
//        };
//
//        resetSpecimenToolAction = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.rightAutoResetEnd();
//                return false;
//            }
//        };
//
//
//    }
//
//
//
//
//    public void runOpMode() throws InterruptedException {
//        initAuto();
//        waitForStart();
//
//        Actions.runBlocking(
//                new SequentialAction(
//                        firstReleaseSpecimenAction
////                        specimenDropPos1,
////
////                        raiseCandyCaneAction,
////                        spikeMarkPos1,
////                        specimenCollectInParallelAction,
////                        lowerCandyCaneAction,
////                        new SleepAction(0.3),
////                        observationZoneDropPos1,
////
////                        raiseCandyCaneAction,
////                        spikeMarkPos2,
////                        lowerCandyCaneAction,
////                        new SleepAction(0.3),
////                        observationZoneDropPos2,
////
////                        raiseCandyCaneAction,
////                        spikeMarkPos3,
////                        lowerCandyCaneAction,
////                        new SleepAction(0.3),
////                        observationZoneDropPos3,
////
////                        resetCandyCaneAction,
////                        observationZonePos1,
////                        observationZonePos1_1,
////                        specimenToolDropAfterPickupAction,
////                        specimenDropPos2,
////                        releaseSpecimenAction,
////
////                        afterDropSpecimenCollectAction,
////                        observationZonePos2,
////                        specimenToolDropAfterPickupAction,
////                        specimenDropPos3,
////                        releaseSpecimenAction,
////
////                        afterDropSpecimenCollectAction,
////                        observationZonePos3,
////                        specimenToolDropAfterPickupAction,
////                        specimenDropPos4,
////                        releaseSpecimenAction,
////
////
////                        afterDropSpecimenCollectAction,
////                        observationZonePos4,
////                        specimenToolDropAfterPickupAction,
////                        specimenDropPos5,
////                        releaseSpecimenAction,
////                        resetSpecimenToolAction,
////                        parkPos
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
