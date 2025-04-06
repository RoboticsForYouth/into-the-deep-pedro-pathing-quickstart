package az.itd.test;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.az.itd.tools.DoubleArm;
import org.firstinspires.ftc.teamcode.az.itd.tools.SpecimenTool;
import org.firstinspires.ftc.teamcode.az.sample.AZUtil;

@TeleOp
public class TestTeleOp extends LinearOpMode {

    // This variable determines whether the following program
    // uses field-centric or robot-centric driving styles. The
    // differences between them can be read here in the docs:
    // https://docs.ftclib.org/ftclib/features/drivebases#control-scheme
    static final boolean FIELD_CENTRIC = false;
    SpecimenTool specimenTool = null;

    DoubleArm arm = null;
    //    Slides slides = null;
    private boolean dpadUpProcessing;
    private boolean gamepad2DpadUpProcessing;
    private boolean gamepad2dpadDownProcessing;
    private boolean gamepad2DpadDownProcessing;
    private boolean dpadRightProcessing;
    private boolean dpadLeftProcessing;

    private boolean buttonAProcessing;
    private boolean gamepad2ButtonAProcessing;

    private boolean buttonBProcessing;
    private boolean buttonXProcessing;
    private boolean buttonYProcessing;
    private boolean rightTriggerProcessing;
    private boolean leftTriggerProcessing;
    private boolean rightBumperProcessing;
    private boolean leftBumperProcessing;
    private boolean dpadDownProcessing;

    GamepadEx gamepadEx1;
    GamepadEx gamepadEx2;

    public void setup() {
        specimenTool.reset();
    }

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

        specimenTool = new SpecimenTool(this);
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        // the extended gamepad object
        GamepadEx driverOp = new GamepadEx(gamepad1);

        waitForStart();

        //specimenTool.specimenToolInit();

//

//        autoTest();
        while(opModeIsActive()){
            AZUtil.runInParallel(new Runnable() {
                @Override
                public void run() {
                    if(gamepad1.dpad_up){
                        specimenTool.slides.moveUp();
                    }

                    if(gamepad1.dpad_down){
                        specimenTool.slides.moveDown();
                    }

                    if( gamepad1.dpad_left){
                        specimenTool.arm.moveDown();
                    }

                    if( gamepad1.dpad_right){
                        specimenTool.arm.moveUp();
                    }



                    if(gamepad2.dpad_up){
                        specimenTool.slides.moveUpSlow();
                    }

                    if(gamepad2.dpad_down){
                        specimenTool.slides.moveDownSlow();
                    }

                    if( gamepad2.dpad_left){
                        specimenTool.arm.moveDownSlow();
                    }

                    if( gamepad2.dpad_right){
                        specimenTool.arm.moveUpSlow();
                    }




                    if( gamepad1.b){
                        specimenTool.arm.setup();
                        specimenTool.slides.resetPos();
                    }

                    if(gamepad1.right_trigger > 0) {
                        specimenTool.gripper.moveUp();
                    }
                    if(gamepad1.left_trigger > 0) {
                        specimenTool.gripper.moveDown();
                    }

                    if(gamepad1.a) {
                        specimenTool.arm.specimenPickUp();
                        specimenTool.slides.specimenCollect();
                        specimenTool.gripper.specimenPickUp();
                    }

                    if(gamepad1.right_bumper) {
                        specimenTool.gripper.teleOpSpecimenDropPos();
                    }

                    // Pickup block on pressing A button
                    if (gamepad2.a) {
                        specimenTool.teleOpCollect();
                    }

                    // Eject block on pressing B button
                    if (gamepad2.b) {
                        specimenTool.gripper.sampleDrop();
                    }

                    // Stop the roller on pressing X button
                    if (gamepad2.x) {
                        specimenTool.reset();
                    }

                    if(gamepad2.right_bumper){
                        specimenTool.gripper.samplePickUp90();
                    }

                    if(gamepad2.left_bumper){
                        specimenTool.gripper.specimenPickUp();
                    }

//                    if(gamepad2.dpad_up){
////                        specimenTool.gripper.specimenDrop();
//                        specimenTool.gripper.drop();
//                    }
//                    if(gamepad2.dpad_down){
////                        specimenTool.gripper.specimenDrop();
//                        specimenTool.gripper.samplePickup();
//                    }
//                    if(gamepad2.dpad_down){
////                        specimenTool.gripper.specimenDrop();
//                        specimenTool.gripper.autoSampleDrop();
//                    }
                }
            });
            drive.driveRobotCentric(
                    -driverOp.getLeftX(),
                    -driverOp.getLeftY(),
                    -driverOp.getRightX(),
                    false
            );
//            specimenTool.gripper.detectColorAction();
            specimenTool.printPos(telemetry);
            telemetry.addLine(specimenTool.arm.toString());
            telemetry.update();
        }
    }
}
