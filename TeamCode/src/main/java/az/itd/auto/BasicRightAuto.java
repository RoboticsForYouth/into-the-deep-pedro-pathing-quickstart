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
//import com.acmerobotics.roadrunner.SleepAction;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DistanceSensor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.teamcode.az.itd.tools.AZImu;
//import org.firstinspires.ftc.teamcode.az.itd.tools.DoubleArm;
//import org.firstinspires.ftc.teamcode.az.itd.tools.Slides;
//import org.firstinspires.ftc.teamcode.az.itd.tools.SpecimenTool;
//import org.firstinspires.ftc.teamcode.az.sample.MecanumDrive;
//
//
//@Config
//@Autonomous ( preselectTeleOp = "IntoTheDeepTeleOp")
//public class BasicRightAuto extends LinearOpMode {
//    ElapsedTime runtime = new ElapsedTime();
//
//    SpecimenTool specimenTool = null;
//    DoubleArm arm = null;
//    Slides slides = null;
//    DistanceSensor distanceSensor;
//    MecanumDrive drive;
//    private Pose2d beginPose;
//
//    private AZImu imu;
//    private Action specimenHang;
//    private Action specimenDropPos;
//    private Action park;
//
//    public class specimenHang implements Action {
//
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//
//
//                    specimenTool.teleOpSpecimenCollect();
//                    sleep(1000);
//
////                    specimenTool.sampleDrop();
////                    sleep(200);
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
//        arm = new DoubleArm(this);
//        slides = new Slides(this);
//        specimenTool = new SpecimenTool(this);
//        specimenTool.reset();
//
////        specimenTool.initArmDistanceSensor(); //set arm to init position
//
//
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//        telemetry.update();
//        runtime.reset();
//        beginPose = new Pose2d(0,0,Math.toRadians(0));
//        drive = new MecanumDrive(hardwareMap, beginPose);
//        telemetry.addData("current position", drive.pose);
//        telemetry.update();
//
//        initActions();
//
//        waitForStart();
//
//    }
//
//    private void initActions() {
//        specimenHang = new Action(){
//
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.specimenHangPos();
//                return false;
//            }
//        };
//
//        specimenDropPos = drive.actionBuilder(beginPose)
//                .splineToConstantHeading(new Vector2d(21, 5), Math.toRadians(0))
//                .build();
//
//        park = drive.actionBuilder(drive.pose)
//                .splineToConstantHeading(new Vector2d(3, -60), Math.toRadians(0))
//                .build();
//
//    }
//
//
//    public void runOpMode() throws InterruptedException {
//        initAuto();
//
//
//
//
//
//        Actions.runBlocking(
//                new SequentialAction(
//                        specimenHang,
//                        specimenDropPos,
//                        ejectAction(),
//                        new SleepAction(2),
//                        new Action() {
//                            @Override
//                            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                                specimenTool.resetAndWait();
//                                sleep(1000);
//                                return false;
//                            }
//                        }
//                        ,park,
//                        new Action() {
//                            @Override
//                            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                                specimenTool.reset();
//                                sleep(1000);
//                                return false;
//                            }
//                        }
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
//    private Action ejectAction() {
//        return new Action() {
//            @Override
//            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//                specimenTool.eject();
//                sleep(1000);
//                specimenTool.gripper.reset();
//                return false;
//            }
//        };
//    }
//
//}
