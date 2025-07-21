package pedroPathing.az.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import pedroPathing.constants.AutoConstants;

import pedroPathing.az.tools.AZUtil;
import pedroPathing.az.tools.CandyCane;
import pedroPathing.az.tools.DoubleArm;
import pedroPathing.az.tools.SpecimenTool;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;



@Autonomous (preselectTeleOp = "IntoTheDeepTeleOp")
public class RightAuto extends LinearOpMode {

    SpecimenTool specimenTool;
    CandyCane candyCane;

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer, motorWaitTimer;


    public enum PathState {
        FINAL_STATE, DROP_0_RAISE_ARM, DROP_0_EJECT, MOVE_TO_SPIKE_MARK_1,
        MOVE_TO_PUSH_IN_ZONE_1, MOVE_TO_SPIKE_MARK_2, MOVE_TO_PUSH_IN_ZONE_2,
        MOVE_TO_SPIKE_MARK_3, MOVE_TO_PUSH_IN_ZONE_3,
        MOVE_TO_COLLECT_1, GET_TO_DROP_1, WAIT_FOR_ARM_UP_1, SPECIMEN_TOOL_DROP_POS_1, NINETEEN,
        TWENTY, TWENTY_ONE, TWENTY_TWO, TWENTY_THREE, TWENTY_FOUR, TWENTY_FIVE, TWENTY_SIX,
        TWENTY_SEVEN, TWENTY_EIGHT, MOVE_TO_SCORE_PRELOAD

    }

    private PathState currentPathState;


    private final Pose startPose = new Pose(9, 60, Math.toRadians(180));

    private final Pose drop0Pose = new Pose(14.7, 63, Math.toRadians(180));

    private final Pose SpikeMark1ControlPose1 = new Pose(10, 45, Math.toRadians(180));

    private final Pose candyCane1Pose = new Pose(65, 38, Math.toRadians(180));

    private final Pose pushInZone1Pose = new Pose(20, 45, Math.toRadians(180));

    private final Pose candyCane2Pose = new Pose(30, 45, Math.toRadians(180));

    private final Pose pushInZone2Pose = new Pose(31, 44, Math.toRadians(180));

    private final Pose candyCane3Pose = new Pose(30, 39, Math.toRadians(180));

    private final Pose pushInZone3Pose = new Pose(27, 39, Math.toRadians(180));

    private final Pose collect1Pose = new Pose(20, 30, Math.toRadians(180));
    private final Pose collect1_1Pose = new Pose(13, 40, Math.toRadians(180));

    private final Pose drop1Pose = new Pose(14.7, 63, Math.toRadians(180));

    private final Pose collect2Pose = new Pose(13, 20, Math.toRadians(180));

    private final Pose drop2Pose = new Pose(37, 72, Math.toRadians(180));

    private final Pose collect3Pose = new Pose(12, 19, Math.toRadians(180));

    private final Pose drop3Pose = new Pose(37, 68, Math.toRadians(180));

    private final Pose collect4Pose = new Pose(13, 20, Math.toRadians(180));

    private final Pose drop4Pose = new Pose(35, 62, Math.toRadians(180));

    private final Pose parkPose = new Pose(13, 98, Math.toRadians(180));

    private final Pose parkControlPose = new Pose(13, 98, Math.toRadians(180));

    private Path scorePreload, park;
    private PathChain spikeMark1Traj, spikeMark2Traj, spikeMark3Traj, pushInZone1Traj,
            pushInZone2Traj, pushInZone3Traj, collect1Traj, drop1Traj, collect2Traj,
            drop2Traj, collect3Traj, drop3Traj, collect4Traj, drop4Traj;

    public void buildPaths() {

        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(drop0Pose)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), drop0Pose.getHeading());

        spikeMark1Traj = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(drop0Pose), new Point(SpikeMark1ControlPose1), new Point(candyCane1Pose)))
                .setLinearHeadingInterpolation(drop0Pose.getHeading(), candyCane1Pose.getHeading())
                .build();

        pushInZone1Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(candyCane1Pose), new Point(pushInZone1Pose)))
                .setLinearHeadingInterpolation(candyCane1Pose.getHeading(), pushInZone1Pose.getHeading())
                .build();

        spikeMark2Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pushInZone1Pose), new Point(candyCane2Pose)))
                .setLinearHeadingInterpolation(pushInZone1Pose.getHeading(), candyCane2Pose.getHeading())
                .build();

        pushInZone2Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(candyCane2Pose), new Point(pushInZone2Pose)))
                .setLinearHeadingInterpolation(candyCane2Pose.getHeading(), pushInZone2Pose.getHeading())
                .build();

        spikeMark3Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pushInZone2Pose), new Point(candyCane3Pose)))
                .setLinearHeadingInterpolation(pushInZone2Pose.getHeading(), candyCane3Pose.getHeading())
                .build();

        pushInZone3Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(candyCane3Pose), new Point(pushInZone3Pose)))
                .setLinearHeadingInterpolation(candyCane3Pose.getHeading(), pushInZone3Pose.getHeading())
                .build();

        collect1Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pushInZone3Pose), new Point(collect1Pose)))
                .setLinearHeadingInterpolation(pushInZone3Pose.getHeading(), collect1Pose.getHeading())
                .addPath(new BezierLine(new Point(collect1Pose), new Point(collect1_1Pose)))
                .setLinearHeadingInterpolation(collect1Pose.getHeading(), collect1_1Pose.getHeading())
                .build();

        drop1Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(collect1Pose), new Point(drop1Pose)))
                .setLinearHeadingInterpolation(collect1Pose.getHeading(), drop1Pose.getHeading())
                .build();

        collect2Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(drop1Pose), new Point(collect2Pose)))
                .setLinearHeadingInterpolation(drop1Pose.getHeading(), collect2Pose.getHeading())
                .build();

        drop2Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(collect2Pose), new Point(drop2Pose)))
                .setLinearHeadingInterpolation(collect2Pose.getHeading(), drop2Pose.getHeading())
                .build();

        collect3Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(drop2Pose), new Point(collect3Pose)))
                .setLinearHeadingInterpolation(drop2Pose.getHeading(), collect3Pose.getHeading())
                .build();

        drop3Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(collect3Pose), new Point(drop3Pose)))
                .setLinearHeadingInterpolation(collect3Pose.getHeading(), drop3Pose.getHeading())
                .build();

        collect4Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(drop3Pose), new Point(collect4Pose)))
                .setLinearHeadingInterpolation(drop3Pose.getHeading(), collect4Pose.getHeading())
                .build();

        drop4Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(collect4Pose), new Point(drop4Pose)))
                .setLinearHeadingInterpolation(collect4Pose.getHeading(), drop4Pose.getHeading())
                .build();

        park = new Path(new BezierCurve(new Point(drop3Pose), /* Control Point */ new Point(parkControlPose), new Point(parkPose)));
        park.setLinearHeadingInterpolation(drop3Pose.getHeading(), parkPose.getHeading());
    }

    public void autonomousPathUpdate() {
        switch (currentPathState) {
            case MOVE_TO_SCORE_PRELOAD: // move to scorePreload pos
                follower.followPath(scorePreload, true);
                setPathState(PathState.DROP_0_RAISE_ARM);
                break;
            case DROP_0_RAISE_ARM:
                if(pathTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_DROP_DELAY_ARM) {
                    follower.followPath(scorePreload, true);

                    specimenTool.arm.setArmPos(DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP);
                    specimenTool.gripper.rightAutoSpecimenDropPos0();

                    actionTimer.resetTimer();

                    setPathState(PathState.DROP_0_EJECT);
                }
                break;
            case DROP_0_EJECT: // delay then set gripper drop pos and reset action timer
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_DROP_0_DELAY_EJECT) {
                    specimenTool.gripper.drop();

                    setPathState(PathState.MOVE_TO_SPIKE_MARK_1);
                }
                break;
            case MOVE_TO_SPIKE_MARK_1: // move to spikeMark1 pos then set candy cane pos
                if(!follower.isBusy()) {
                    follower.followPath(spikeMark1Traj,true);

                    setPathState(PathState.MOVE_TO_PUSH_IN_ZONE_1);
                }
                break;
            case MOVE_TO_PUSH_IN_ZONE_1: // delay for candy cane and move to pushInZone1 pos
                if(!follower.isBusy()) {
                    specimenTool.rightAutoSpecimenCollect();

                    follower.followPath(pushInZone1Traj,true);
                    setPathState(PathState.MOVE_TO_SPIKE_MARK_2);
                }
                break;
            case MOVE_TO_SPIKE_MARK_2: // raise candy cane and move to spikeMark2 pos
                if(!follower.isBusy()) {

                    follower.followPath(spikeMark2Traj,true);
                    setPathState(PathState.MOVE_TO_PUSH_IN_ZONE_2);
                }
                break;
            case MOVE_TO_PUSH_IN_ZONE_2: // delay for candy cane and move to pushInZone2 pos
                if(!follower.isBusy()) {

                    follower.followPath(pushInZone2Traj,true);
                    setPathState(PathState.MOVE_TO_SPIKE_MARK_3);
                }
                break;
            case MOVE_TO_SPIKE_MARK_3: // raise candy cane and move to spikeMark3 pos
                if(!follower.isBusy()) {

                    follower.followPath(spikeMark3Traj,true);
                    setPathState(PathState.MOVE_TO_PUSH_IN_ZONE_3);
                }
                break;

            case MOVE_TO_PUSH_IN_ZONE_3: // delay for candy cane and move to pushInZone3 pos
                if(!follower.isBusy()) {

                    follower.followPath(pushInZone3Traj, true);
                    setPathState(PathState.MOVE_TO_COLLECT_1);
                }
                break;
            case MOVE_TO_COLLECT_1: // delay for candy cane and move to collect1 pos
                if(!follower.isBusy()) {

                    follower.followPath(collect1Traj, true);
                    setPathState(PathState.GET_TO_DROP_1);
                }
                break;
            case GET_TO_DROP_1: // set arm pos and move to drop1 pos
                if(!follower.isBusy()) {
                    specimenTool.arm.setArmPos(DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP);
                    actionTimer.resetTimer();

                    follower.followPath(drop1Traj, true);
                    setPathState(PathState.WAIT_FOR_ARM_UP_1);
                }
                break;
            case WAIT_FOR_ARM_UP_1: // wait for arm and reset action timer
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_DROP_DELAY_ARM) {

                    specimenTool.gripper.rightAutoSpecimenDropPos();

                    actionTimer.resetTimer();
                    setPathState(PathState.SPECIMEN_TOOL_DROP_POS_1);
                }
                break;
            case SPECIMEN_TOOL_DROP_POS_1: // delay and set specimen tool pos
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_HANG_DELAY) {
                    specimenTool.rightAutoSpecimenHangPos();
                    setPathState(PathState.NINETEEN);

                }
                break;
            case NINETEEN: //
                if(!follower.isBusy()) {
                    specimenTool.arm.rightAutoSpecimenDrop();


                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
//                        sleep(100);
                            specimenTool.afterDropRightAutoSpecimenCollect();
                        }
                    });

                    setPathState(PathState.TWENTY);
                }
                break;
            case TWENTY:
                if((specimenTool.arm.getCurrentPosition() >
                        (DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP.getValue()-AutoConstants.HIGH_WAIT_TOLERANCE)
                        && specimenTool.arm.getCurrentPosition() <
                        (DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP.getValue()+AutoConstants.HIGH_WAIT_TOLERANCE))
                        || (motorWaitTimer.getElapsedTimeSeconds() > AutoConstants.MOTOR_WAIT_DELAY)) {

                    specimenTool.gripper.drop();

                    follower.followPath(collect2Traj, true);

                    actionTimer.resetTimer();

                    setPathState(PathState.TWENTY_ONE);
                }
                break;
            case TWENTY_ONE:
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_DROP_DELAY_ARM) {
                    specimenTool.gripper.rollerCollect();
                    setPathState(PathState.TWENTY_TWO);

                }
                break;
            case TWENTY_TWO:
                if(!follower.isBusy()) {
                    specimenTool.arm.setPosAndWait((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP_INTEMEDIATE_WAIT.getValue());

                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            sleep((int)AutoConstants.SPECIMEN_HANG_DELAY_MS);
                            specimenTool.rightAutoSpecimenHangPos();
                        }
                    });

                    follower.followPath(drop2Traj, true);
                    setPathState(PathState.TWENTY_THREE);
                }
                break;
            case TWENTY_THREE:
                if(!follower.isBusy()) {
                    specimenTool.rightAutoSpecimenDrop();

                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
//                        sleep(100);
                            specimenTool.afterDropRightAutoSpecimenCollect();
                        }
                    });

                    follower.followPath(collect3Traj, true);
                    setPathState(PathState.TWENTY_FOUR);
                }
                break;
            case TWENTY_FOUR:
                if(!follower.isBusy()) {
                    specimenTool.arm.setPosAndWait((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP_INTEMEDIATE_WAIT.getValue());

                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            sleep((int)AutoConstants.SPECIMEN_HANG_DELAY_MS);
                            specimenTool.rightAutoSpecimenHangPos();
                        }
                    });

                    follower.followPath(drop3Traj, true);
                    setPathState(PathState.TWENTY_FIVE);
                }
                break;
            case TWENTY_FIVE:
                if(!follower.isBusy()) {
                    specimenTool.rightAutoSpecimenDrop();

                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
//                        sleep(100);
                            specimenTool.afterDropRightAutoSpecimenCollect();
                        }
                    });

                    follower.followPath(collect4Traj, true);
                    setPathState(PathState.TWENTY_SIX);
                }
                break;
            case TWENTY_SIX:
                if(!follower.isBusy()) {
                    specimenTool.arm.setPosAndWait((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP_INTEMEDIATE_WAIT.getValue());

                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            sleep((int)AutoConstants.SPECIMEN_HANG_DELAY_MS);
                            specimenTool.rightAutoSpecimenHangPos();
                        }
                    });

                    follower.followPath(drop4Traj, true);
                    setPathState(PathState.TWENTY_SEVEN);
                }
                break;
            case TWENTY_SEVEN:
                if(!follower.isBusy()) {
                    specimenTool.rightAutoSpecimenDrop();

                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
//                        sleep(100);
                            specimenTool.afterDropRightAutoSpecimenCollect();
                        }
                    });

                    follower.followPath(park,true);
                    setPathState(PathState.TWENTY_EIGHT);
                }
                break;
            case TWENTY_EIGHT:
                if(!follower.isBusy()) {

                    setPathState(PathState.FINAL_STATE);
                }
                break;
        }
    }


    @Override
    public void runOpMode() throws InterruptedException {
        initAuto();
        waitForStart();

        sleep(AutoConstants.STARTUP_DELAY_MS);

        opmodeTimer.resetTimer();

        setPathState(PathState.MOVE_TO_SCORE_PRELOAD);

        while (!isStopRequested() && currentPathState != PathState.FINAL_STATE && opmodeTimer.getElapsedTimeSeconds() < AutoConstants.AUTO_TIMEOUT_SECONDS) {
            follower.update();
            autonomousPathUpdate();

            telemetry.addData("path state", currentPathState);
            telemetry.addData("x", follower.getPose().getX());
            telemetry.addData("y", follower.getPose().getY());
            telemetry.addData("heading", follower.getPose().getHeading());
            telemetry.addData("action timer", actionTimer.getElapsedTimeSeconds());
            telemetry.addData("path timer", pathTimer.getElapsedTimeSeconds());
            telemetry.addData("opmode timer", opmodeTimer.getElapsedTimeSeconds());
            telemetry.addData("follower is busy", follower.isBusy());
            telemetry.update();
        }
    }

    public void initAuto() {
        specimenTool = new SpecimenTool(this);
        specimenTool.rightAutoReset();
        candyCane = new CandyCane(this);

        sleep(15000);

        telemetry.addLine("Time's Up");
        telemetry.update();

        motorWaitTimer = new Timer();
        motorWaitTimer.resetTimer();

        pathTimer = new Timer();
        actionTimer = new Timer();
        actionTimer.resetTimer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

//        specimenTool = new SpecimenTool(this);

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();

        telemetry.addLine("Initialized");
        telemetry.update();
    }

    public void setPathState(PathState newState) {
        this.currentPathState = newState;
        pathTimer.resetTimer();
//        actionTimer.resetTimer();
    }



}

