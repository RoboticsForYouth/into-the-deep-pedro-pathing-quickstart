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


//            slides = new Motor(hardwareMap, "slides");
            //collect
            if (gamepad1.a) { //x
                if (!buttonAProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            buttonAProcessing = true;
                            if (specimenTool.slides.getCurrentPos() <= Slides.SlidesPos.COLLECT.getValue() + 100) {
                                specimenTool.duringTelOpReset();
                            } else {
                                specimenTool.teleOpHighReset();
                            }

                            buttonAProcessing = false;
                        }
                    });
                }
            }


            //drop the specimen
            if (gamepad1.b) { //circle
                if (!buttonBProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            buttonBProcessing = true;
                            specimenTool.teleOpEject();
                            buttonBProcessing = false;
                        }
                    });

                }
            }

            //set to move position
            if (gamepad1.x) { //square
                if (!buttonXProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            buttonXProcessing = true;

                            if (specimenTool.slides.getCurrentPos() <= Slides.SlidesPos.COLLECT.getValue() + 100) {
                                specimenTool.teleOpCollectVertical();

                            } else {
                                //change order of resetPos to ensure that slides do not hit the basket
                                specimenTool.teleOpHighReset();
                            }
                            buttonXProcessing = false;
                        }
                    });
                }
            }

            //low basket
            if (gamepad1.dpad_up) {
                if (!dpadUpProcessing) {
                    dpadUpProcessing = true;
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            specimenTool.teleOpSpecimenLowBasket();
                            dpadUpProcessing = false;
                        }
                    });

                }

            }

            if (gamepad1.right_bumper) {
                if (!teleOpSpecimenHangPosProcessing) {
                    teleOpSpecimenHangPosProcessing = true;
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            specimenTool.teleOpSpecimenHangPos();
                            teleOpSpecimenHangPosProcessing = false;
                        }
                    });
                }
            }

            if (gamepad1.dpad_left) {
                if (!teleOpSpecimenPickup) {
                    teleOpSpecimenPickup = true;
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            if (specimenTool.slides.getCurrentPos() <= Slides.SlidesPos.COLLECT.getValue() + 100) {
                                specimenTool.teleOpSpecimenPickup();

                            } else {
                                //change order of resetPos to ensure that slides do not hit the basket
                                specimenTool.teleOpSpecimenPickupFromHighDrop();
                            }
                            teleOpSpecimenPickup = false;
                        }
                    });
                }
            }

            //Level 2 hang
            if (gamepad1.dpad_down) {
                if (!dpadDownProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            dpadDownProcessing = true;
                            specimenTool.teleOpLevel2Hang();
                            dpadDownProcessing = false;
                        }
                    });
                }

            }

            //resetPos to initialize position
            if (gamepad2.b) {
                if (!gamepad2ButtonBProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            gamepad2ButtonBProcessing = true;
                            specimenTool.teleOpEmergencyResetEncoders();
                            gamepad2ButtonBProcessing = false;
                        }
                    });
                }

            }

            if (gamepad2.y) {
                if (!gamepad2ButtonYProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            gamepad2ButtonYProcessing = true;
                            candyCane.reset();
                            gamepad2ButtonYProcessing = false;
                        }
                    });
                }

            }

            if (gamepad2.x) {
                if (!gamepad2ButtonXProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            gamepad2ButtonXProcessing = true;
                            candyCane.teleOpRaise();
                            gamepad2ButtonXProcessing = false;
                        }
                    });
                }

            }

            if (gamepad2.dpad_up) {
                if (!gamepad2DpadUpProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            gamepad2DpadUpProcessing = true;
                            specimenTool.slides.moveUpSlow();
                            gamepad2DpadUpProcessing = false;
                        }
                    });
                }
            }

            if (gamepad2.dpad_down) {
                if (!gamepad2dpadDownProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            gamepad2dpadDownProcessing = true;
                            specimenTool.slides.moveDownSlow();
                            gamepad2dpadDownProcessing = false;
                        }
                    });
                }
            }

            if (gamepad2.dpad_left) {
                if (!gamepad2DpadLeftProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            gamepad2DpadLeftProcessing = true;
                            specimenTool.arm.moveDownSlow();
                            gamepad2DpadLeftProcessing = false;
                        }
                    });
                }
            }

            if (gamepad2.dpad_right) {
                if (!gamepad2DpadRightProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            gamepad2DpadRightProcessing = true;
                            specimenTool.arm.moveUpSlow();
                            gamepad2DpadRightProcessing = false;
                        }
                    });
                }

            }


            //set to high basket
            if (gamepad1.left_bumper) {
                if (!leftBumperProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            leftBumperProcessing = true;
                            specimenTool.teleOpDropHighBasket();
                            leftBumperProcessing = false;
                        }
                    });
                }

            }

            //extend the slides
            if (gamepad1.right_trigger > 0) {
                //if not processing then perform this operation
                if (!rightTriggerProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            rightTriggerProcessing = true;
                            specimenTool.teleOpSlidesExtend(gamepad1.right_trigger);
                            rightTriggerProcessing = false;
                        }
                    });
                }
            }

            if (gamepad1.left_trigger > 0) {
                //if not processing then perform this operation
                if (!leftTriggerProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            leftTriggerProcessing = true;
                            specimenTool.teleOpArmExtend(gamepad1.left_trigger);
                            leftTriggerProcessing = false;
                        }
                    });
                }
            }


            if (gamepad1.right_bumper) {
                if (!rightBumperProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            rightBumperProcessing = true;
                            specimenTool.teleOpSpecimenHangPos();
                            rightBumperProcessing = false;
                        }
                    });
                }

            }


            //resetPos the slides
            if (gamepad2.a) { //circle
                if (!gamepad2ButtonAProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            gamepad2ButtonAProcessing = true;
                            specimenTool.teleOpEmergencyReset();
                            gamepad2ButtonAProcessing = false;
                        }
                    });

                }
            }

            if (gamepad1.y) {
                if (!buttonYProcessing) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            buttonYProcessing = true;
                            specimenTool.teleOpSampleCollect();
                            buttonYProcessing = false;
                        }
                    });
                }

            }
//            telemetry.addLine(InitialValues.printCurrentPos());
//            specimenTool.printPos(telemetry);
        }
    }
}
