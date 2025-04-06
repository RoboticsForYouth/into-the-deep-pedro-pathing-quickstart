package az.sample;//package org.firstinspires.ftc.teamcode.az.sample;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.az.itd.tools.Arm;
//
//
//@Autonomous
//public class InitArmPosition extends LinearOpMode{
//
//    Arm arm = null;
//    LinearOpMode opMode;
//
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        this.opMode = this;
//
//        telemetry.addLine("Init");
//        telemetry.update();
//
//        Arm arm = new Arm(this);
//
//        arm.setup();
//        waitForStart();
//
//        arm.moveToPosition(1160); //changed for 435 rpm motor to 312rpm
//        sleep(6000);
//
//    }
//}
