package pedroPathing.az.tools;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import pedroPathing.az.auto.RightAuto;

@TeleOp
public class SpecimenTool extends LinearOpMode {
    public  LinearOpMode opMode;
    public DoubleArm arm;
    public EnhancedClaw gripper;
    public Slides slides;


    public SpecimenTool(){
        super();
    }

    public SpecimenTool(LinearOpMode opMode) {
        this.opMode = opMode;
        init(opMode);
    }

    private void init(LinearOpMode opMode){
        arm = new DoubleArm(opMode);
        gripper = new EnhancedClaw(opMode);
        slides = new Slides(opMode);
    }

    public void printPos(Telemetry telemetry){
        telemetry.addData("Slide Pos", slides.printCurrentPos());
        telemetry.addData("Arm Pos:", arm.getCurrentPosition());
        telemetry.addData("Gripper Pos:", gripper.toString());
        telemetry.update();
    }



















    //--------------------------------------------------------------------------------------------------------------------
    //TELE OP!!!!

    public void teleOpSpecimenToolInit() {
        slides.move();
        sleep(500);
        arm.move();
        sleep(1000);
        gripper.move();
    }

    public void teleOpCollect() {
        slides.collect();
//        sleep(500);
        gripper.samplePickup();
//        sleep(500);
        arm.collect();
//        sleep(1000);
    }

    public void teleOpHighReset () {
//        slides.collect();
        gripper.samplePickup();
        slides.setPosAndWait((int) Slides.SlidesPos.COLLECT.getValue());

//        sleep(1000);
        arm.collect();

    }


    public void teleOpEject() {
        gripper.drop();
        //sleep(500);
    }

    public void teleOpCollectVertical() {
        slides.collect();
//        sleep(500);
        gripper.samplePickUp90();
//        sleep(500);
        arm.collect();
//        sleep(1000);
    }

    public void teleOpSpecimenLowBasket() {
        arm.lowBasketDrop();
        sleep(1000);
        slides.lowBasket();
        sleep(1000);
        gripper.sampleDrop();
        sleep(500);
    }


    public void reset() {
        slides.resetPos();
        sleep(2000);
        arm.reset();
        sleep(2000);
        gripper.reset();
        sleep(500);
    }

    public void teleOpEmergencyReset() {
        gripper.reset();
        slides.emergencyResetPos();
        //sleep(2000);
        arm.emergencyResetPos();
        //sleep(2000);
    }

    public void teleOpEmergencyResetEncoders() {
        gripper.reset();
        slides.emergencyResetEncoders();
        //sleep(2000);
        arm.emergencyResetEncoders();
        //sleep(2000);
    }

    public void teleOpDropHighBasket() {
        arm.setPosAndWait((int) DoubleArm.DoubleArmPos.BASKET_DROP.getValue());
//        arm.moveToPosition(DoubleArm.DoubleArmPos.BASKET_DROP);
//        sleep(1000);
        slides.moveToPosition(Slides.SlidesPos.BASKET_DROP);
        sleep(700);
        gripper.sampleDrop();
    }

    public void teleOpSlidesExtend(float factor) {slides.extend(factor);}
    //teleOpSlidesExtend by a factor between 0 and 1

    public void teleOpArmExtend(float factor) {arm.extend(factor);}
    //teleOpSlidesExtend by a factor between 0 and 1

    public void teleOpSpecimenHangPos() {
        arm.setArmPos(DoubleArm.DoubleArmPos.TELEOP_SPECIMEN_DROP);
        slides.moveToPosition(Slides.SlidesPos.TELEOP_SPECIMEN_DROP);
        gripper.teleOpSpecimenDropPos();
        sleep(1000);
        slides.stopMotor();
//        gripper.specimenDrop();
    }

    public void teleOpSpecimenCollect() {
        gripper.specimenPickUp();
        sleep(500);

        arm.specimenCollect();
        sleep(500);
        slides.specimenCollect();
//        sleep(1000);
    }

    public void teleOpLevel2Hang(){

        gripper.gripperHang();


        //slides go to 2000
        slides.setPosAndWait((int) Slides.SlidesPos.LEVEL_2_HANG_START_OPTION2_END_POS.getValue());
        sleep(1000);
        //arm to 75
        arm.moveToPosition(DoubleArm.DoubleArmPos.LEVEL_TWO_HANG);


//        arm.moveToPosition(DoubleArm.DoubleArmPos.PRE_LEVEL_TWO_HANG);
//        slides.setPosAndWait((int) Slides.SlidesPos.LEVEL_2_HANG_START.getValue());
//
//        sleep(1000);
//        arm.moveToPosition(DoubleArm.DoubleArmPos.LEVEL_TWO_HANG);
//        sleep(500);
//
//        slides.setPosAndWait((int) Slides.SlidesPos.LEVEL_2_HANG_END.getValue());
//        sleep(500);
        gripper.gripperHang();
//        arm.moveToPosition(DoubleArm.DoubleArmPos.LEVEL_TWO_HANG_PART_TWO);


    }

    //--------------------------------------------------------------------------------------------------------------------


    //--------------------------------------------------------------------------------------------------------------------
    //LEFT AUTO!!!!

    public void leftAutoReset(){
        slides.resetPos();
        sleep(2000);
        arm.reset();
        sleep(2000);
        gripper.leftAutoReset();
        sleep(500);
    }


    public void leftAutoDropHighBasket() {

        AZUtil.runInParallel(new Runnable() {
            @Override
            public void run() {
                gripper.leftAutoIntermediate();
                //sleep(500);
                arm.setPosAndWait((int) DoubleArm.DoubleArmPos.LEFT_AUTO_BASKET_DROP.getValue());
                slides.setPosAndWaitWithTolerance((int) Slides.SlidesPos.LEFT_AUTO_BASKET_DROP.getValue(), 15);
                sleep(1100);
                gripper.leftAutoSampleDrop();
//                LeftAutoHighDropArmSetupActionDone = true;
            }
        });
    }

    public void leftAutoCollect(final Slides.SlidesPos slidesPos, final EnhancedClaw.WRIST_POS wristPos, final DoubleArm.DoubleArmPos armPos)   {
        AZUtil.runInParallel(new Runnable() {
            @Override
            public void run() {
                gripper.leftAutoPickup(wristPos);
                sleep(400);
                slides.setPosAndWait((int) Slides.SlidesPos.LEFT_AUTO_INTERMEDIATE_PICKUP.getValue());
                sleep(200);
                arm.leftAutoPickup(armPos);
                slides.leftAutoPickup(slidesPos);

            }
        });
    }

    public void thirdLeftAutoCollect(final Slides.SlidesPos slidesPos, final EnhancedClaw.WRIST_POS wristPos, final DoubleArm.DoubleArmPos armPos)   {
        AZUtil.runInParallel(new Runnable() {
            @Override
            public void run() {
                gripper.leftAutoPickup(wristPos);
                sleep(400);
                slides.setPosAndWait((int) Slides.SlidesPos.LEFT_AUTO_INTERMEDIATE_PICKUP.getValue());
                sleep(1100);
                arm.leftAutoPickup(armPos);
                slides.leftAutoPickup(slidesPos);

            }
        });
    }

    public void leftAutoLaterDropsHighBasket() {

//        slides.reset();
//        sleep(500);

        slides.setPosAndWaitWithTolerance((int) Slides.SlidesPos.RESET.getValue(), 15);
        //sleep(400);

        AZUtil.runInParallel(new Runnable() {
            @Override
            public void run() {
//                gripper.autoProtect();
//                sleep(500);

                gripper.leftAutoIntermediate();
//                sleep(500);

                arm.setPosAndWait((int) DoubleArm.DoubleArmPos.LEFT_AUTO_BASKET_DROP.getValue());
                slides.setPosAndWaitWithTolerance((int) Slides.SlidesPos.LEFT_AUTO_BASKET_DROP.getValue(), 15);
                sleep(1000);
                gripper.leftAutoSampleDrop();

//                LeftAutoHighDropArmSetupActionDone = true;
            }
        });
    }

    public void leftAutoResetEnd() {
        gripper.move();
        sleep(200);
        slides.reset();
//        gripper.drop();

        sleep(1000);
        arm.leftAutoReset();
        gripper.reset();

////        sleep(1000);
//        sleep(1000);
//        gripper.resetPos();
    }

    //--------------------------------------------------------------------------------------------------------------------


    //--------------------------------------------------------------------------------------------------------------------
    //RIGHT AUTO!!!!

    public void rightAutoReset(){
        slides.resetPos();
        sleep(2000);
        arm.reset();
        sleep(2000);
        gripper.specimenAutoReset();
        sleep(500);
    }

    public void rightAutoSpecimenDrop() {
//        slides.moveToPosition(Slides.SlidesPos.RESET);


        arm.setPosAndWaitThreshold((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP.getValue(), 120);


        gripper.drop();
        sleep(80);

        AZUtil.runInParallel(new Runnable() {
            @Override
            public void run() {
                sleep(600);
                gripper.rollerCollect();
            }
        });

    }

    public void firstRightAutoSpecimenDrop() {
//        slides.moveToPosition(Slides.SlidesPos.RESET);


        arm.setPosAndWaitThreshold((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP.getValue(), 45);
        sleep(200);

        gripper.drop();
        sleep(220);

        gripper.rightAutoDown();

        AZUtil.runInParallel(new Runnable() {
            @Override
            public void run() {
                sleep(1400);
                gripper.rollerCollect();
            }
        });

    }

    public void rightAutoSpecimenCollect() {
        gripper.rightAutoSpecimenPickUp();
        slides.reset();
        arm.setPosAndWait((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_PICKUP_UP.getValue());
        sleep(500);
    }

    public void afterDropRightAutoSpecimenCollect() {

        arm.setPosAndWaitPower((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_PICKUP_INTERMEDIATE_WAIT.getValue());
        slides.reset();
        arm.setPosPower((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_PICKUP_UP.getValue());
        sleep(500);
        gripper.rightAutoSpecimenPickUp();
//        sleep(500);
    }

    public void rightAutoSpecimenHangPos() {

        arm.setArmPos(DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP);


        slides.moveToPosition(Slides.SlidesPos.RIGHT_AUTO_SPECIMEN_DROP);
        gripper.rightAutoSpecimenDropPos();
//        gripper.specimenDrop();
    }

    public void rightAutoResetEnd() {
//        gripper.move();
//        sleep(200);
        slides.reset();
//        gripper.drop();

        sleep(200);
        arm.rightAutoReset();
        gripper.reset();

////        sleep(1000);
//        sleep(1000);
//        gripper.resetPos();
    }
    //--------------------------------------------------------------------------------------------------------------------














    @Override
    public void runOpMode() throws InterruptedException {
        this.opMode = this;
        init(opMode);

        waitForStart();

//        autoTest();
        while(opModeIsActive()){

            if(gamepad1.dpad_up){
                slides.moveUpSlow();
            }

            if(gamepad1.dpad_down){
                slides.moveDown();
            }

            if( gamepad1.dpad_left){
                arm.moveDownSlow();
            }

            if( gamepad1.dpad_right){
                arm.moveUp();
            }
            if( gamepad1.b){
//                arm.setupPos();
//                arm.moveToPosition(1160); //changed for 435 rpm motor to 312rpm
            }
        }


    }

    private void autoTest() {
        teleOpSpecimenToolInit();
        sleep(5000);

        teleOpCollect();
        sleep(5000);


        teleOpHighReset();
        sleep(5000);


        teleOpDropHighBasket();
        sleep(5000);

        teleOpHighReset();
        sleep(5000);


        reset();
        sleep(5000);
    }

}
