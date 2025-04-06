package az.itd.test;//package org.firstinspires.ftc.teamcode.az.itd.test;
//
//import com.qualcomm.hardware.rev.RevColorSensorV3;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//import org.firstinspires.ftc.teamcode.az.itd.tools.LaserRangeFinder;
//import org.firstinspires.ftc.teamcode.az.itd.tools.SpecimenTool;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//
//
//
//@TeleOp
//public class DistanceSensorTest extends LinearOpMode {
//
//    LaserRangeFinder lrf = null;
//    SpecimenTool specimenTool = null;
//
//    public void runOpMode() throws InterruptedException {
//
//        lrf = new LaserRangeFinder(hardwareMap.get(RevColorSensorV3.class, "Laser"));
//        lrf.setDistanceMode(LaserRangeFinder.DistanceMode.SHORT);
//        specimenTool = new SpecimenTool(this);
//
//        waitForStart();
//        specimenTool.arm.runWithoutEncoder();
//
//        while (opModeIsActive()) {
//
//            double distance = lrf.getDistance(DistanceUnit.INCH);
//
//            while (!(distance >= (LaserRangeFinder.height - LaserRangeFinder.tolerance)) || !(distance <= (LaserRangeFinder.height + LaserRangeFinder.tolerance))) {
//
//                double factor = (LaserRangeFinder.height - distance) / 7;
//
//                specimenTool.arm.moveFactor(factor);
//
//                distance = lrf.getDistance(DistanceUnit.INCH); //continuously record distance
//
//                telemetry.addData("Factor", factor);
//                telemetry.addData("Distance", distance);
//                telemetry.addData("Status", lrf.getStatus());
//                telemetry.update();
//            }
//            specimenTool.arm.moveFactor(0);
//            telemetry.addLine("Done");
//            telemetry.update();
//
////            if(gamepad1.dpad_up){
////                specimenTool.arm.moveUpSlow();
////            }
////
////            if(gamepad1.dpad_down){
////                specimenTool.arm.moveDownSlow();
////            }
//
////            double distance = lrf.getDistance(DistanceUnit.INCH);
//
////            telemetry.addData("Distance", distance);
////            telemetry.addData("Status", lrf.getStatus());
////            telemetry.update();
//        }
//    }
//}
//
