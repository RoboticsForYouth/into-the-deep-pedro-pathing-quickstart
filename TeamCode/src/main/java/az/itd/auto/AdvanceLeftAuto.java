package az.itd.auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TurnConstraints;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.az.itd.tools.DoubleArm;
import org.firstinspires.ftc.teamcode.az.itd.tools.EnhancedClaw;
import org.firstinspires.ftc.teamcode.az.itd.tools.Slides;

@Autonomous(preselectTeleOp = "IntoTheDeepTeleOp")
public class AdvanceLeftAuto extends BasicLeftAuto {

    TurnConstraints turnConstraints = new TurnConstraints(
            (Math.PI)/3,
            -(Math.PI)/3,
            (Math.PI)/3);



    private Action moveToDropPos0;
    private Action moveToDropPos1;
    private Action moveToDropPos2;
    private Action moveToDropPos3;
    private Action collectPos1;
    private Action collectPos2;
    private Action collectPos3;
    private Action highDropArmSetupAction;
    private Action resetAction;
    private Action collectAction1;
    private Action collectAction2;
    private Action collectAction3;
    private Action collectPos1_1;
    private Action collectPos2_1;
    private Action collectPos3_1;
    private Action waitForArmAction1;
    private Action
            highLaterDropsArmSetupAction;
    private Action waitForArmAction2;
    private Action waitForArmAction3;
    private Action parkPos;
    private Action levelOneAscentAction;



   public static boolean LeftAutoHighDropArmSetupActionDone = false;

    private void updateInit() {
        initAuto();
        addActions();

        telemetry.addLine("Initialized");
        telemetry.update();
    }

    private void addActions() {

        TrajectoryActionBuilder moveToDropTraj0 = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(drop0, Math.toRadians(BASKET_HEADING_0));
        moveToDropPos0 = moveToDropTraj0.build();

        TrajectoryActionBuilder collectTraj1 = moveToDropTraj0.endTrajectory().fresh()
                .turnTo(Math.toRadians(COLLECT_HEADING_1), turnConstraints) //6
                .strafeToConstantHeading(collect1);
//                .strafeToLinearHeading(new Vector2d(14.25, 9.5), Math.toRadians(6));
        collectPos1 = collectTraj1.build();

        TrajectoryActionBuilder collectTraj1_1 = collectTraj1.endTrajectory().fresh()
                .strafeToConstantHeading(collect1_1);
//                .turnTo(Math.toRadians(6), turnConstraints);
        collectPos1_1 = collectTraj1_1.build();

        TrajectoryActionBuilder moveToDropTraj1 = collectTraj1_1.endTrajectory().fresh()
//                .turnTo(Math.toRadians(HIGH_BASKET_HEADING));
                .strafeToLinearHeading(drop1, Math.toRadians(BASKET_HEADING_1));
        moveToDropPos1 = moveToDropTraj1.build();

        TrajectoryActionBuilder collectTraj2 = moveToDropTraj1.endTrajectory().fresh()
                .turnTo(Math.toRadians(COLLECT_HEADING_2), turnConstraints)
                .strafeToConstantHeading(collect2)
//                .strafeToLinearHeading(new Vector2d(10, 8.5), Math.toRadians(10));
                ; //35
        collectPos2 = collectTraj2.build();

        TrajectoryActionBuilder collectTraj2_1 = collectTraj2.endTrajectory().fresh()
                .strafeToConstantHeading(collect2_1);
//                .turnTo(Math.toRadians(35), turnConstraints);
        collectPos2_1 = collectTraj2_1.build();

        TrajectoryActionBuilder moveToDropTraj2 = collectTraj2_1.endTrajectory().fresh()
//                .turnTo(Math.toRadians(HIGH_BASKET_HEADING));
                .strafeToLinearHeading(drop2, Math.toRadians(BASKET_HEADING_2));
        moveToDropPos2 = moveToDropTraj2.build();

        TrajectoryActionBuilder collectTraj3 = moveToDropTraj2.endTrajectory().fresh()
                .strafeToConstantHeading(collect3)
//                .strafeToLinearHeading(new Vector2d(9, 6.5), Math.toRadians(36));
                .turnTo(Math.toRadians(COLLECT_HEADING_3), turnConstraints); //31
        collectPos3 = collectTraj3.build();

//        TrajectoryActionBuilder collectTraj3_1 = collectTraj3.endTrajectory().fresh()
//                .strafeToConstantHeading(collect3_1);
////                .turnTo(Math.toRadians(45), turnConstraints);
//        collectPos3_1 = collectTraj3_1.build();

        TrajectoryActionBuilder moveToDropTraj4 = collectTraj3.endTrajectory().fresh()
//                .turnTo(Math.toRadians(HIGH_BASKET_HEADING));
                .strafeToLinearHeading(drop3, Math.toRadians(BASKET_HEADING_3));
        moveToDropPos3 = moveToDropTraj4.build();

        TrajectoryActionBuilder parkTraj = moveToDropTraj4.endTrajectory().fresh()
                .strafeToLinearHeading(park1, Math.toRadians(PARK_HEADING_1))
                .strafeToConstantHeading(park2);
        parkPos = parkTraj.build();




        highDropArmSetupAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                LeftAutoHighDropArmSetupActionDone = false;
                specimenTool.leftAutoDropHighBasket();
                return false;
            }
        };

        highLaterDropsArmSetupAction = new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                LeftAutoHighDropArmSetupActionDone = false;

                specimenTool.leftAutoLaterDropsHighBasket();
                return false;
            }
        };

        collectAction1 = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                while( !LeftAutoHighDropArmSetupActionDone){
                    Thread.yield();
                }
                specimenTool.leftAutoCollect(Slides.SlidesPos.LEFT_AUTO_PICKUP_FIRST, EnhancedClaw.WRIST_POS.LEFT_AUTO_PICKUP_FIRST, DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_FIRST);
                return false;
            }
        };

        collectAction2 = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                while( !LeftAutoHighDropArmSetupActionDone){
                    Thread.yield();
                }
                specimenTool.leftAutoCollect(Slides.SlidesPos.LEFT_AUTO_PICKUP_SECOND, EnhancedClaw.WRIST_POS.LEFT_AUTO_PICKUP_SECOND, DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_SECOND);
                return false;
            }
        };

        collectAction3 = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                while( !LeftAutoHighDropArmSetupActionDone){
                    Thread.yield();
                }
                specimenTool.thirdLeftAutoCollect(Slides.SlidesPos.LEFT_AUTO_PICKUP_THIRD, EnhancedClaw.WRIST_POS.LEFT_AUTO_PICKUP_THIRD, DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_THIRD);
                return false;
            }
        };



        waitForArmAction1 = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.arm.setPosAndWaitLowPowerLeftAuto((int) DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_FIRST.getValue());
                specimenTool.slides.setPosAndWaitLeftAuto((int) Slides.SlidesPos.LEFT_AUTO_PICKUP_FIRST.getValue());
                return false;
            }
        };

        waitForArmAction2 = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.arm.setPosAndWaitLowPowerLeftAuto((int) DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_SECOND.getValue());
                specimenTool.slides.setPosAndWaitLeftAuto((int) Slides.SlidesPos.LEFT_AUTO_PICKUP_SECOND.getValue());
                return false;
            }
        };

        waitForArmAction3 = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                specimenTool.arm.setPosAndWaitLowPowerLeftAuto((int) DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_THIRD.getValue());
                specimenTool.slides.setPosAndWaitLeftAuto((int) Slides.SlidesPos.LEFT_AUTO_PICKUP_THIRD.getValue());
                return false;
            }
        };



        resetAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                while( !LeftAutoHighDropArmSetupActionDone) {
                    Thread.yield();
                }


                specimenTool.leftAutoResetEnd();
                candyCane.preLeftAutoLevelOneAscent();

                return false;
            }
        };

        levelOneAscentAction = new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                candyCane.leftAutoLevelOneAscent();
                return false;
            }
        };

    }

    @Override
    public void runOpMode() throws InterruptedException {
        updateInit();
        Actions.runBlocking(
                new SequentialAction(
                        //sample 1
                        highDropArmSetupAction,
                        moveToDropPos0, //start here!!!
                        collectAction1,
                        collectPos1,
//                        new SleepAction(0.5),
                        waitForArmAction1,
                        collectPos1_1,

                        highLaterDropsArmSetupAction,
                        moveToDropPos1,
                        collectAction2,
                        collectPos2,
//                        new SleepAction(0.5),
                        waitForArmAction2,
                        collectPos2_1,

                        highLaterDropsArmSetupAction,
                        moveToDropPos2,
                        collectAction3,
                        collectPos3,
                        new SleepAction(0.5),
                        waitForArmAction3,
//                        collectPos3_1,

                        highLaterDropsArmSetupAction,
                        moveToDropPos3,
                        resetAction,
                        parkPos,
                        levelOneAscentAction


                        )
        );
//
        telemetry.addData("current position", drive.pose);
        telemetry.update();


        sleep(10000);
    }
}
