package pedroPathing.az.teleop;

import pedroPathing.az.tools.AZUtil;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import pedroPathing.az.tools.CandyCane;
import pedroPathing.az.tools.DoubleArm;
import pedroPathing.az.tools.Slides;
import pedroPathing.az.tools.SpecimenTool;

@TeleOp
public class IntoTheDeepTeleOp extends LinearOpMode {

    static final boolean FIELD_CENTRIC = false;
    SpecimenTool specimenTool = null;

    DoubleArm arm = null;
    CandyCane candyCane = null;
    //    Slides slides = null;
    private boolean gamepad2DpadUpProcessing;
    private boolean gamepad2DpadRightProcessing;
    private boolean gamepad2DpadLeftProcessing;


    private boolean gamepad2dpadDownProcessing;
    private boolean dpadUpProcessing;
    private boolean dpadRightProcessing;
    private boolean dpadLeftProcessing;

    private boolean buttonAProcessing;
    private boolean gamepad2ButtonAProcessing;
    private boolean gamepad2RightBumperProcessing;


    private boolean buttonBProcessing;
    private boolean gamepad2ButtonBProcessing;

    private boolean buttonXProcessing;
    private boolean gamepad2ButtonXProcessing;
    private boolean gamepad2ButtonYProcessing;


    private boolean buttonYProcessing;
    private boolean rightTriggerProcessing;
    private boolean leftTriggerProcessing;
    private boolean rightBumperProcessing;
    private boolean leftBumperProcessing;
    private boolean dpadDownProcessing;

    GamepadEx gamepadEx1;
    GamepadEx gamepadEx2;
    private boolean teleOpSpecimenHangPosProcessing;
    private boolean teleOpSpecimenPickup;


//    public void setup() {
//        specimenTool.resetPos();
//    }

    @Override
    public void runOpMode() throws InterruptedException {
        // constructor takes in frontLeft, frontRight, backLeft, backRight motors
        // IN THAT ORDER
        MecanumDrive drive = new MecanumDrive(
                new Motor(hardwareMap, "frontLeft", Motor.GoBILDA.RPM_435),
                new Motor(hardwareMap, "frontRight", Motor.GoBILDA.RPM_435),
                new Motor(hardwareMap, "backLeft", Motor.GoBILDA.RPM_435),
                new Motor(hardwareMap, "backRight", Motor.GoBILDA.RPM_435)
        );

        arm = new DoubleArm(this);
        specimenTool = new SpecimenTool(this);
        candyCane = new CandyCane(this);
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
        GamepadEx driverOp = new GamepadEx(gamepad1);

        candyCane.reset();


        // the extended gamepad object

        //specimenTool.arm.initPos(); //set arm to init position
//        InitialValues.SetInitPos();

//        telemetry.addLine(InitialValues.printCurrentPos());
//        telemetry.update();

        telemetry.addLine("Initialized");
        telemetry.update();

        waitForStart();

        specimenTool.teleOpSpecimenToolInit();


        while (!isStopRequested()) {

            drive.driveRobotCentric(
                    -driverOp.getLeftX() * 1.75,
                    -driverOp.getLeftY() * 1.75,
                    -driverOp.getRightX() * 1.75,
                    false
            );

            // Collect / Reset
            if (gamepad1.a && !buttonAProcessing) {
                buttonAProcessing = true;
                new Thread(() -> {
                    if (specimenTool.slides.getCurrentPos() <= Slides.SlidesPos.COLLECT.getValue() + 100) {
                        specimenTool.duringTelOpReset();
                    } else {
                        specimenTool.teleOpHighReset();
                    }
                    buttonAProcessing = false;
                }).start();
            }

            // Drop specimen
            if (gamepad1.b && !buttonBProcessing) {
                buttonBProcessing = true;
                new Thread(() -> {
                    specimenTool.teleOpEject();
                    buttonBProcessing = false;
                }).start();
            }

            // Collect vertical
            if (gamepad1.x && !buttonXProcessing) {
                buttonXProcessing = true;
                new Thread(() -> {
                    if (specimenTool.slides.getCurrentPos() <= Slides.SlidesPos.COLLECT.getValue() + 100) {
                        specimenTool.teleOpCollectVertical();
                    } else {
                        specimenTool.teleOpHighReset();
                    }
                    buttonXProcessing = false;
                }).start();
            }

            // Low basket
            if (gamepad1.dpad_up && !dpadUpProcessing) {
                dpadUpProcessing = true;
                new Thread(() -> {
                    specimenTool.teleOpSpecimenLowBasket();
                    dpadUpProcessing = false;
                }).start();
            }

            // Specimen hang
            if (gamepad1.right_bumper && !teleOpSpecimenHangPosProcessing) {
                teleOpSpecimenHangPosProcessing = true;
                new Thread(() -> {
                    specimenTool.teleOpSpecimenHangPos();
                    teleOpSpecimenHangPosProcessing = false;
                }).start();
            }

            // Roller stop
            if (gamepad2.right_bumper && !gamepad2RightBumperProcessing) {
                gamepad2RightBumperProcessing = true;
                new Thread(() -> {
                    specimenTool.gripper.rollerStop();
                    gamepad2RightBumperProcessing = false;
                }).start();
            }

            // Specimen pickup
            if (gamepad1.dpad_left && !teleOpSpecimenPickup) {
                teleOpSpecimenPickup = true;
                new Thread(() -> {
                    if (specimenTool.slides.getCurrentPos() <= Slides.SlidesPos.COLLECT.getValue() + 100) {
                        specimenTool.teleOpSpecimenPickup();
                    } else {
                        specimenTool.teleOpSpecimenPickupFromHighDrop();
                    }
                    teleOpSpecimenPickup = false;
                }).start();
            }

            // Level 2 hang
            if (gamepad1.dpad_down && !dpadDownProcessing) {
                dpadDownProcessing = true;
                new Thread(() -> {
                    specimenTool.teleOpLevel2Hang();
                    dpadDownProcessing = false;
                }).start();
            }

            // Emergency reset encoders
            if (gamepad2.b && !gamepad2ButtonBProcessing) {
                gamepad2ButtonBProcessing = true;
                new Thread(() -> {
                    specimenTool.teleOpEmergencyResetEncoders();
                    gamepad2ButtonBProcessing = false;
                }).start();
            }

            // CandyCane reset/raise
            if (gamepad2.y && !gamepad2ButtonYProcessing) {
                gamepad2ButtonYProcessing = true;
                new Thread(() -> {
                    candyCane.reset();
                    gamepad2ButtonYProcessing = false;
                }).start();
            }

            if (gamepad2.x && !gamepad2ButtonXProcessing) {
                gamepad2ButtonXProcessing = true;
                new Thread(() -> {
                    candyCane.teleOpRaise();
                    gamepad2ButtonXProcessing = false;
                }).start();
            }

            // Slides / Arm slow movements
            if (gamepad2.dpad_up && !gamepad2DpadUpProcessing) {
                gamepad2DpadUpProcessing = true;
                new Thread(() -> {
                    specimenTool.slides.moveUpSlow();
                    gamepad2DpadUpProcessing = false;
                }).start();
            }

            if (gamepad2.dpad_down && !gamepad2dpadDownProcessing) {
                gamepad2dpadDownProcessing = true;
                new Thread(() -> {
                    specimenTool.slides.moveDownSlow();
                    gamepad2dpadDownProcessing = false;
                }).start();
            }

            if (gamepad2.dpad_left && !gamepad2DpadLeftProcessing) {
                gamepad2DpadLeftProcessing = true;
                new Thread(() -> {
                    specimenTool.arm.moveDownSlow();
                    gamepad2DpadLeftProcessing = false;
                }).start();
            }

            if (gamepad2.dpad_right && !gamepad2DpadRightProcessing) {
                gamepad2DpadRightProcessing = true;
                new Thread(() -> {
                    specimenTool.arm.moveUpSlow();
                    gamepad2DpadRightProcessing = false;
                }).start();
            }

            // Drop high basket
            if (gamepad1.left_bumper && !leftBumperProcessing) {
                leftBumperProcessing = true;
                new Thread(() -> {
                    specimenTool.teleOpDropHighBasket();
                    leftBumperProcessing = false;
                }).start();
            }

            // Slides / Arm extend
            if (gamepad1.right_trigger > 0 && !rightTriggerProcessing) {
                rightTriggerProcessing = true;
                new Thread(() -> {
                    specimenTool.teleOpSlidesExtend(gamepad1.right_trigger);
                    rightTriggerProcessing = false;
                }).start();
            }

            if (gamepad1.left_trigger > 0 && !leftTriggerProcessing) {
                leftTriggerProcessing = true;
                new Thread(() -> {
                    specimenTool.teleOpArmExtend(gamepad1.left_trigger);
                    leftTriggerProcessing = false;
                }).start();
            }

            // Emergency reset
            if (gamepad2.a && !gamepad2ButtonAProcessing) {
                gamepad2ButtonAProcessing = true;
                new Thread(() -> {
                    specimenTool.teleOpEmergencyReset();
                    gamepad2ButtonAProcessing = false;
                }).start();
            }

            // Sample collect
            if (gamepad1.y && !buttonYProcessing) {
                buttonYProcessing = true;
                new Thread(() -> {
                    specimenTool.teleOpSampleCollect();
                    buttonYProcessing = false;
                }).start();
            }
        }
    }
}
