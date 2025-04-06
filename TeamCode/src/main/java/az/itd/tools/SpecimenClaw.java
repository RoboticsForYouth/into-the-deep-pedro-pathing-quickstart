package az.itd.tools;//package org.firstinspires.ftc.teamcode.az.itd.tools;
//
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.Servo;
//
//@Autonomous
//public class SpecimenClaw extends LinearOpMode {
//
//    private Servo specimenClaw;
//    LinearOpMode opMode;
//    private SpecimenClawPos currentPosValue;
//
//    public SpecimenClaw() {
//        super();
//    }
//
//    public SpecimenClaw(LinearOpMode newOpMode) {
//        this.opMode = newOpMode;
//        setup();
//    }
//
//    public void grab() {
//        specimenClaw.setPosition(SpecimenClawPos.SPECIMEN_CLAW_COLLECT.value);
//    }
//
//    public void setCurrentPosValue(SpecimenClawPos specimenClawCollect) {
//        currentPosValue = specimenClawCollect;
//    }
//
//
//
//    public boolean isGrabbedSpecimen() {
//        return currentPosValue == SpecimenClawPos.SPECIMEN_CLAW_COLLECT;
//    }
//
//    public double getCurrentPosValue() {
//        return 0;
//    }
//
//    public enum SpecimenClawPos {
//
//        SPECIMEN_CLAW_RESET(0.0),
//        SPECIMEN_CLAW_COLLECT(0.47);
//
//
//        private double value;
//
//        SpecimenClawPos(double val) {
//            this.value = val;
//        }
//
//        public double getValue() {
//            return this.value;
//        }
//    }
//
//    public void setup() {
//        specimenClaw = opMode.hardwareMap.get(Servo.class, "specimenClaw");
////        specimenClaw.setDirection(Servo.Direction.REVERSE);
//        resetPos();
//    }
//
//    public void resetPos() {
//        SpecimenClawPos specimenClawReset = SpecimenClawPos.SPECIMEN_CLAW_RESET;
//        specimenClaw.setPosition(specimenClawReset.value);
//        currentPosValue = specimenClawReset;
//    }
//
//    public void collect() {
//        specimenClaw.setPosition(SpecimenClaw.SpecimenClawPos.SPECIMEN_CLAW_COLLECT.value);
//    }
//
//
//    @Override
//    public void runOpMode() {
//        this.opMode = this;
//
//        telemetry.addLine("Init");
//        telemetry.update();
//
//        setup();
//        waitForStart();
//
//            collect();
//            sleep(3000);
//
//            resetPos();
//            sleep(3000);
//
//        }
//}
