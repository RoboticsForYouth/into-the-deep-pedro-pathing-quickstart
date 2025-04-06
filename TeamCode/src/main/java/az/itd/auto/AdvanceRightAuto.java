package az.itd.auto;//package org.firstinspires.ftc.teamcode.az.itd.auto;
//
//import androidx.annotation.NonNull;
//
//import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.SequentialAction;
//import com.acmerobotics.roadrunner.SleepAction;
//import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//import org.firstinspires.ftc.teamcode.az.itd.tools.EnhancedClaw;
//import org.firstinspires.ftc.teamcode.az.itd.tools.AZUtil;
//
//@Autonomous(preselectTeleOp = "IntoTheDeepTeleOp")
//public class AdvanceRightAuto extends BasicRightAuto {
//
//
//    private Action samplePos2;
//    private Action collectAction;
//    private Action resetAction;
//    private Action moveToCollect2;
//    private Action moveToDrop1;
//    private TrajectoryActionBuilder specimenDropPosTraj;
//    private Action highDropEjectAction;
//    private Action dropAction;
//    private Action moveBackToResetAction;
//    private Action rightAutoDrop;
//    private Action moveToDrop1_1;
//    private Action secondSamplePosAction;
//    private Action parallelSpecimenHang;
//    private Action movePosAction;
//    private Action moveToParkAction;
//    private Action moveToDrop2;
//    private Action moveToDrop2_1;
//    private Action moveBackToResetAction2;
//    private Action samplePos3;
//    private Action moveToCollect3;
//    private Action moveToDrop3;
//    private Action moveToDrop3_1;
//    private Action moveBackToResetAction3;
//    private Action samplePos4;
//    private Action moveToCollect4;
//    private Action moveToDrop4;
//    private Action moveToDrop4_1;
//    private Action moveBackToResetAction4;
//
//
//    private void updateInit() {
//        initAuto();
//        addActions();
//    }
//
//    private void addActions() {
//
//        parallelSpecimenHang = new Action(){
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                AZUtil.runInParallel(new Runnable() {
//                    @Override
//                    public void run() {
//                        //specimenTool.teleOpSpecimenCollect();
//                        sleep(1000);
//                    }
//                });
//                return false;
//            }
//        };
//
////        specimenDropPosTraj = drive.actionBuilder(drive.pose)
////                .splineToConstantHeading(new Vector2d(21, 0), Math.toRadians(0));
////        specimenDropPos = specimenDropPosTraj.build();
//
//
//
//
//
//
//        TrajectoryActionBuilder moveToDropTraj1 = drive.actionBuilder(drive.pose)
//                .splineToLinearHeading(new Pose2d(10, 28, Math.toRadians(140)), 0);
//        moveToDrop1 = moveToDropTraj1.build();
//
//        rightAutoDrop = new Action(){
//
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.rightAutoDrop();
////                specimenTool.gripper.detectColorActionEjectAuto();
//                return false;
//            }
//        };
//
//        TrajectoryActionBuilder moveToDropTraj1_1 = moveToDropTraj1.endTrajectory().fresh()
//                .lineToX(4);
//        moveToDrop1_1 = moveToDropTraj1_1.build();
//
//        highDropEjectAction = new Action() {
//
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
////                specimenTool.gripper.detectColorActionEjectAuto();
//                return false;
//            }
//        };
//
//        TrajectoryActionBuilder resetActionTraj = moveToDropTraj1_1.endTrajectory().fresh()
//                .lineToX(14);
//        moveBackToResetAction = resetActionTraj
//                .build();
//
//        resetAction = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.resetAndWait();
//                return false;
//            }
//        };
//
//        TrajectoryActionBuilder samplePos2Traj = resetActionTraj.endTrajectory().fresh() //Change if starting here
//                .splineToLinearHeading(new Pose2d(24, -39, Math.toRadians(-50)), 0);
//        samplePos2 = samplePos2Traj.build();
//
//        collectAction = new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.autoCollect(EnhancedClaw.WRIST_POS.AUTO_PICKUP);
//                specimenTool.detectColorActionAuto();
//                return false;
//            }
//        };
//
//        movePosAction = new Action(){
//
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.move();
//                return false;
//            }
//        };
//
//        TrajectoryActionBuilder moveToCollect2Traj = samplePos2Traj.endTrajectory().fresh()
//                .lineToX(25);
//        moveToCollect2 = moveToCollect2Traj.build();
//
//        TrajectoryActionBuilder moveToDropTraj2 = moveToCollect2Traj.endTrajectory().fresh()
//                .splineToLinearHeading(new Pose2d(10, -28, Math.toRadians(-140)), 0);
//        moveToDrop2 = moveToDropTraj2.build();
//
//        TrajectoryActionBuilder samplePos3Traj = moveToDropTraj2.endTrajectory().fresh()
//                .splineToLinearHeading(new Pose2d(24, -50, Math.toRadians(-50)), 0);
//        samplePos3 = samplePos3Traj.build();
//
//        TrajectoryActionBuilder moveToCollect3Traj = samplePos3Traj.endTrajectory().fresh()
//                .lineToX(25);
//        moveToCollect3 = moveToCollect3Traj.build();
//
//        TrajectoryActionBuilder moveToDropTraj3 = moveToCollect3Traj.endTrajectory().fresh()
//                .splineToLinearHeading(new Pose2d(10, -28, Math.toRadians(-140)), 0);
//        moveToDrop3 = moveToDropTraj3.build();
//
//        TrajectoryActionBuilder samplePos4Traj = moveToDropTraj3.endTrajectory().fresh()
//                .splineToLinearHeading(new Pose2d(24, -27.5, Math.toRadians(-50)), 0);
//        samplePos4 = samplePos4Traj.build();
//
//        TrajectoryActionBuilder moveToCollect4Traj = samplePos4Traj.endTrajectory().fresh()
//                .lineToX(25);
//        moveToCollect4 = moveToCollect4Traj.build();
//
//        TrajectoryActionBuilder moveToDropTraj4 = moveToCollect4Traj.endTrajectory().fresh()
//                .splineToLinearHeading(new Pose2d(10, -28, Math.toRadians(-140)), 0);
//        moveToDrop4 = moveToDropTraj4.build();
//
//
//
//
//
//
//        TrajectoryActionBuilder moveToPark = moveToDropTraj4.endTrajectory().fresh()
//                .splineToLinearHeading(new Pose2d(15, -30, Math.toRadians(0)), 0)
//                .lineToX(2);
//        moveToParkAction = moveToPark
//                .build();
//
//
//
//
//
//
//        TrajectoryActionBuilder secondSamplePosTraj = resetActionTraj.endTrajectory().fresh()
//                .splineToLinearHeading(new Pose2d(22, 39, Math.toRadians(50)), 0);
//
//        secondSamplePosAction = secondSamplePosTraj.build();
//
//    }
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        updateInit();
//        Actions.runBlocking(
//                new SequentialAction(
////                        moveToDrop1, //start here!!!
////                        highDropArmSetupAction,
////                        new SleepAction(2),
////                        moveToDrop1_1,
////                        highDropEjectAction,
////                        moveBackToResetAction,
////                        resetAction,
//
//                        samplePos2,
//                        collectAction,
//                        new SleepAction(2),
//                        movePosAction,
//                        moveToCollect2,
//                        moveToDrop2,
//                        rightAutoDrop,
//                        resetAction,
//
//                        samplePos3,
//                        collectAction, //
//                        new SleepAction(2), //
//                        movePosAction, //
//                        moveToCollect3,
//                        moveToDrop3,
//                        rightAutoDrop, //
//                        resetAction, //
//
//                        samplePos4,
//                        collectAction, //
//                        new SleepAction(2), //
//                        movePosAction, //
//                        moveToCollect4,
//                        moveToDrop4,
//                        rightAutoDrop, //
//                        moveToDrop4_1,
//                        moveBackToResetAction4,
//                        resetAction, //
//
//                        moveToParkAction
//                )
//        );
////
//        telemetry.addData("current position", drive.pose);
//        telemetry.update();
//        sleep(1000);
//    }
//}
