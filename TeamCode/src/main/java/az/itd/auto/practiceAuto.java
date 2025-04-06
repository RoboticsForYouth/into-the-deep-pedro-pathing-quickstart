package az.itd.auto;//package org.firstinspires.ftc.teamcode.az.itd.auto;
//
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//@Autonomous(preselectTeleOp = "IntoTheDeepTeleOp")
//public class practiceAuto extends BasicLeftAuto {
//    private Action testDropPos;
//    private void updateInit() {
//        initAuto();
//        addActions();
//    }
//    private void addActions() {
//        TrajectoryActionBuilder testDropPosTraj = drive.actionBuilder(drive.pose)
//                .splineToConstantHeading(new Vector2d(5, 1), Math.toRadians(0));
//        testDropPos = testDropPosTraj.build();
//
//
////        TrajectoryActionBuilder firstSamplePosTraj = specimenDropPosTraj.endTrajectory().fresh()
////                .splineToLinearHeading(new Pose2d()
//
//    }
//    @Override
//    public void runOpMode() throws InterruptedException {
//        updateInit();
//        Actions.runBlocking(testDropPos);
//        telemetry.addData("current position", drive.pose);
//        telemetry.update();
//        sleep(10000);
//    }
//}
//
