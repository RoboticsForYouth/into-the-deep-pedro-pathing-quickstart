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
        MOVE_TO_SPIKE_MARK_2, LOWER_CANDY_CANE_2, 
        MOVE_TO_SPIKE_MARK_3, LOWER_CANDY_CANE_3, RESET_CANDY_CANE,
        MOVE_TO_COLLECT_1, GET_TO_DROP_1, WAIT_FOR_ARM_UP_1, SPECIMEN_TOOL_DROP_POS_1, MOVE_TO_COLLECT_2,
        GET_TO_DROP_2, END, LOWER_CANDY_CANE_1, PRE_LOWER_CANDY_CANE_1, SPECIMEN_TOOL_DROP_POS_2, MOVE_TO_COLLECT_3, SPECIMEN_TOOL_DROP_POS_4, GET_TO_DROP_4, WAIT_FOR_ARM_UP_4, MOVE_TO_COLLECT_4, SPECIMEN_TOOL_DROP_POS_3, GET_TO_DROP_3, PARK, GRIPPER_DROP_1, GRIPPER_DROP_2, GRIPPER_DROP_3, GRIPPER_DROP_4, SPECIMEN_TOOL_COLLECT_POS_2, SPECIMEN_TOOL_COLLECT_POS_3, SPECIMEN_TOOL_COLLECT_POS_4, SPECIMEN_TOOL_COLLECT_POS_5, MOVE_TO_SCORE_PRELOAD

    }

    private PathState currentPathState;


    private final Pose startPose = new Pose(9, 60, Math.toRadians(180));

    private final Pose drop0Pose = new Pose(14.7, 63, Math.toRadians(180));

    private final Pose candyCane1ControlPose = new Pose(13, 53);

    private final Pose candyCane1Pose = new Pose(36, 48, Math.toRadians(-60));

    private final Pose pushInZone1Pose = new Pose(24, 45, Math.toRadians(-100));

    private final Pose candyCane2Pose = new Pose(32, 43, Math.toRadians(-60));

    private final Pose pushInZone2Pose = new Pose(23, 42, Math.toRadians(-90));

    private final Pose candyCane3Pose = new Pose(36, 37, Math.toRadians(-70));

    private final Pose pushInZone3Pose = new Pose(25, 38, Math.toRadians(-100));

    private final Pose collect1Pose = new Pose(28, 37, Math.toRadians(180));
    private final Pose collect1_1Pose = new Pose(16, 37, Math.toRadians(180));

    private final Pose drop1Pose = new Pose(24.3, 63, Math.toRadians(180));

    private final Pose collect2Pose = new Pose(16.9, 43, Math.toRadians(180));

    private final Pose drop2Pose = new Pose(24, 63, Math.toRadians(180));

    private final Pose collect3Pose = new Pose(17, 43, Math.toRadians(180));

    private final Pose drop3Pose = new Pose(24, 63, Math.toRadians(180));

    private final Pose collect4Pose = new Pose(16.7, 43, Math.toRadians(180));

    private final Pose drop4Pose = new Pose(24, 63, Math.toRadians(180));

    private final Pose parkPose = new Pose(17, 43, Math.toRadians(180));

    private Path scorePreload, park;
    private PathChain spikeMark1Traj, spikeMark2Traj, spikeMark3Traj, collect1Traj, 
            drop1Traj, collect2Traj, drop2Traj, collect3Traj, drop3Traj, collect4Traj, drop4Traj;

    public void buildPaths() {

        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(drop0Pose)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), drop0Pose.getHeading());

        // Combined candy cane 1 movement for efficiency
        spikeMark1Traj = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(drop0Pose), new Point(candyCane1ControlPose), new Point(candyCane1Pose)))
                .setLinearHeadingInterpolation(drop0Pose.getHeading(), candyCane1Pose.getHeading())
                .addPath(new BezierLine(new Point(candyCane1Pose), new Point(pushInZone1Pose)))
                .setLinearHeadingInterpolation(candyCane1Pose.getHeading(), pushInZone1Pose.getHeading())
                .build();

        // Removed separate pushInZone1Traj - now combined with spikeMark1Traj

        // Combined candy cane 2 and 3 movements
        spikeMark2Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pushInZone1Pose), new Point(candyCane2Pose)))
                .setLinearHeadingInterpolation(pushInZone1Pose.getHeading(), candyCane2Pose.getHeading())
                .addPath(new BezierLine(new Point(candyCane2Pose), new Point(pushInZone2Pose)))
                .setLinearHeadingInterpolation(candyCane2Pose.getHeading(), pushInZone2Pose.getHeading())
                .build();

        spikeMark3Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pushInZone2Pose), new Point(candyCane3Pose)))
                .setLinearHeadingInterpolation(pushInZone2Pose.getHeading(), candyCane3Pose.getHeading())
                .addPath(new BezierLine(new Point(candyCane3Pose), new Point(pushInZone3Pose)))
                .setLinearHeadingInterpolation(candyCane3Pose.getHeading(), pushInZone3Pose.getHeading())
                .build();

        collect1Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pushInZone3Pose), new Point(collect1Pose)))
                .setConstantHeadingInterpolation(collect1Pose.getHeading())
                .addPath(new BezierLine(new Point(collect1Pose), new Point(collect1_1Pose)))
                .setConstantHeadingInterpolation(collect1_1Pose.getHeading())
                .build();

        drop1Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(collect1_1Pose), new Point(drop1Pose)))
                .setConstantHeadingInterpolation(drop1Pose.getHeading())
                .build();

        collect2Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(drop1Pose), new Point(collect2Pose)))
                .setConstantHeadingInterpolation(collect2Pose.getHeading())
                .build();

        drop2Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(collect2Pose), new Point(drop2Pose)))
                .setConstantHeadingInterpolation(drop2Pose.getHeading())
                .build();

        collect3Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(drop2Pose), new Point(collect3Pose)))
                .setConstantHeadingInterpolation(collect3Pose.getHeading())
                .build();

        drop3Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(collect3Pose), new Point(drop3Pose)))
                .setConstantHeadingInterpolation(drop3Pose.getHeading())
                .build();

        collect4Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(drop3Pose), new Point(collect4Pose)))
                .setConstantHeadingInterpolation(collect4Pose.getHeading())
                .build();


        drop4Traj = follower.pathBuilder()
                .addPath(new BezierLine(new Point(collect4Pose), new Point(drop4Pose)))
                .setConstantHeadingInterpolation(drop4Pose.getHeading())
                .build();

        park = new Path(new BezierLine(new Point(drop4Pose), new Point(parkPose)));
        park.setConstantHeadingInterpolation(parkPose.getHeading());
    }

    public void autonomousPathUpdate() {
        switch (currentPathState) {
            case MOVE_TO_SCORE_PRELOAD: // move to scorePreload pos
                follower.followPath(scorePreload, true);
                setPathState(PathState.DROP_0_RAISE_ARM);
                break;
            case DROP_0_RAISE_ARM:
                if(pathTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_DROP_DELAY_ARM_0) {
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

                    setPathState(PathState.PRE_LOWER_CANDY_CANE_1);
                }
                break;
            case PRE_LOWER_CANDY_CANE_1:
                if(pathTimer.getElapsedTimeSeconds() > AutoConstants.PRE_CANDY_CANE_DELAY) {
                    candyCane.rightAutoRaise();

                    setPathState(PathState.LOWER_CANDY_CANE_1);
                }
                break;
            case LOWER_CANDY_CANE_1: // lower candy cane during path execution
                if(pathTimer.getElapsedTimeSeconds() > 1.5) { // Lower during the second half of path
                    candyCane.rightAutoLower();
                    setPathState(PathState.MOVE_TO_SPIKE_MARK_2);
                }
                break;
            case MOVE_TO_SPIKE_MARK_2: // raise candy cane and move to spikeMark2 pos (includes push)
                if(!follower.isBusy()) {
                    candyCane.rightAutoRaise();
                    specimenTool.rightAutoSpecimenCollect();

                    follower.followPath(spikeMark2Traj,true);
                    setPathState(PathState.LOWER_CANDY_CANE_2);
                }
                break;
            case LOWER_CANDY_CANE_2: // lower candy cane during path execution
                if(pathTimer.getElapsedTimeSeconds() > 1.2) { // Lower during path
                    candyCane.rightAutoLower();
                    setPathState(PathState.MOVE_TO_SPIKE_MARK_3);
                }
                break;
            case MOVE_TO_SPIKE_MARK_3: // raise candy cane and move to spikeMark3 pos (includes push)
                if(!follower.isBusy()) {
                    candyCane.rightAutoRaise();

                    follower.followPath(spikeMark3Traj,true);
                    setPathState(PathState.LOWER_CANDY_CANE_3);
                }
                break;
            case LOWER_CANDY_CANE_3: // lower candy cane during path execution
                if(pathTimer.getElapsedTimeSeconds() > 1.2) { // Lower during path
                    candyCane.rightAutoLower();
                    setPathState(PathState.RESET_CANDY_CANE);
                }
                break;
            case RESET_CANDY_CANE: // lower candy cane
                if(!follower.isBusy()) {
                    actionTimer.resetTimer();
                    candyCane.reset();

                    setPathState(PathState.MOVE_TO_COLLECT_1);
                }
                break;
            case MOVE_TO_COLLECT_1: // delay for candy cane and move to collect1 pos
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.AFTER_DROP_0_DELAY) {

                    follower.followPath(collect1Traj, true);
                    setPathState(PathState.GET_TO_DROP_1);
                }
                break;
            case GET_TO_DROP_1: // start arm movement early during path
                if(pathTimer.getElapsedTimeSeconds() > 0.5) { // Start arm after 0.5s into path
                    specimenTool.arm.setArmPos(DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP);
                    setPathState(PathState.WAIT_FOR_ARM_UP_1);
                }
                break;
            case WAIT_FOR_ARM_UP_1: // continue path and prepare gripper
                if(!follower.isBusy()) {
                    follower.followPath(drop1Traj, true);
                    specimenTool.gripper.preRightAutoSpecimenDropPos();

                    actionTimer.resetTimer();
                    setPathState(PathState.SPECIMEN_TOOL_DROP_POS_1);
                }
                break;
            case SPECIMEN_TOOL_DROP_POS_1: // delay and set specimen tool pos
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_HANG_DELAY) {
                    specimenTool.rightAutoSpecimenHangPos();

                    actionTimer.resetTimer();
                    setPathState(PathState.GRIPPER_DROP_1);

                }
                break;
            case GRIPPER_DROP_1:
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.GRIPPER_DROP_DELAY) {
                    specimenTool.gripper.drop();
                    actionTimer.resetTimer();

                    setPathState(PathState.SPECIMEN_TOOL_COLLECT_POS_2);
                }
                break;
            case SPECIMEN_TOOL_COLLECT_POS_2: // delay for candy cane and move to collect1 pos
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.AFTER_DROP_DELAY) {
                    specimenTool.rightAutoSpecimenCollect();
                    actionTimer.resetTimer();
                    setPathState(PathState.MOVE_TO_COLLECT_2);

                }
                break;
            case MOVE_TO_COLLECT_2:
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_TOOL_COLLECT_DELAY) {
                    follower.followPath(collect2Traj, true);
                    
                    // Start arm movement immediately
                    specimenTool.arm.setArmPos(DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP);
                    setPathState(PathState.GET_TO_DROP_2);
                }
                break;
            case GET_TO_DROP_2: // continue to drop position
                if(!follower.isBusy()) {
                    follower.followPath(drop2Traj, true);
                    specimenTool.gripper.preRightAutoSpecimenDropPos();

                    actionTimer.resetTimer();
                    setPathState(PathState.SPECIMEN_TOOL_DROP_POS_2);
                }
                break;
            case SPECIMEN_TOOL_DROP_POS_2: // delay and set specimen tool pos
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_HANG_DELAY) {
                    specimenTool.rightAutoSpecimenHangPos();

                    actionTimer.resetTimer();
                    setPathState(PathState.GRIPPER_DROP_2);

                }
                break;
            case GRIPPER_DROP_2:
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.GRIPPER_DROP_DELAY) {
                    specimenTool.gripper.drop();
                    actionTimer.resetTimer();

                    setPathState(PathState.SPECIMEN_TOOL_COLLECT_POS_3);
                }
                break;
            case SPECIMEN_TOOL_COLLECT_POS_3: // delay for candy cane and move to collect1 pos
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.AFTER_DROP_DELAY) {
                    specimenTool.rightAutoSpecimenCollect();
                    actionTimer.resetTimer();
                    setPathState(PathState.MOVE_TO_COLLECT_3);

                }
                break;
            case MOVE_TO_COLLECT_3:
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_TOOL_COLLECT_DELAY) {
                    follower.followPath(collect3Traj, true);
                    
                    // Start arm movement immediately
                    specimenTool.arm.setArmPos(DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP);
                    setPathState(PathState.GET_TO_DROP_3);
                }
                break;
            case GET_TO_DROP_3: // continue to drop position
                if(!follower.isBusy()) {
                    follower.followPath(drop3Traj, true);
                    specimenTool.gripper.preRightAutoSpecimenDropPos();

                    actionTimer.resetTimer();
                    setPathState(PathState.SPECIMEN_TOOL_DROP_POS_3);
                }
                break;
            case SPECIMEN_TOOL_DROP_POS_3: // delay and set specimen tool pos
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_HANG_DELAY) {
                    specimenTool.rightAutoSpecimenHangPos();

                    actionTimer.resetTimer();
                    setPathState(PathState.GRIPPER_DROP_3);

                }
                break;
            case GRIPPER_DROP_3:
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.GRIPPER_DROP_DELAY) {
                    specimenTool.gripper.drop();
                    actionTimer.resetTimer();

                    setPathState(PathState.SPECIMEN_TOOL_COLLECT_POS_4);
                }
                break;
            case SPECIMEN_TOOL_COLLECT_POS_4: // delay for candy cane and move to collect1 pos
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.AFTER_DROP_DELAY) {
                    specimenTool.rightAutoSpecimenCollect();
                    actionTimer.resetTimer();
                    setPathState(PathState.MOVE_TO_COLLECT_4);

                }
                break;
            case MOVE_TO_COLLECT_4:
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_TOOL_COLLECT_DELAY) {
                    follower.followPath(collect4Traj, true);
                    setPathState(PathState.PARK);

                }
                break;
            case GET_TO_DROP_4: // set arm pos and move to drop1 pos
                if(!follower.isBusy()) {
                    specimenTool.arm.setArmPos(DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP);
                    actionTimer.resetTimer();

                    setPathState(PathState.WAIT_FOR_ARM_UP_4);
                }
                break;
            case WAIT_FOR_ARM_UP_4: // wait for arm and reset action timer
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_DROP_DELAY_ARM) {
                    follower.followPath(drop4Traj, true);

                    specimenTool.gripper.preRightAutoSpecimenDropPos();

                    actionTimer.resetTimer();
                    setPathState(PathState.SPECIMEN_TOOL_DROP_POS_4);
                }
                break;
            case SPECIMEN_TOOL_DROP_POS_4: // delay and set specimen tool pos
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_HANG_DELAY) {
                    specimenTool.rightAutoSpecimenHangPos();

                    actionTimer.resetTimer();
                    setPathState(PathState.GRIPPER_DROP_4);

                }
                break;
            case GRIPPER_DROP_4:
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.GRIPPER_DROP_DELAY) {
                    specimenTool.gripper.drop();
                    actionTimer.resetTimer();

                    setPathState(PathState.SPECIMEN_TOOL_COLLECT_POS_5);
                }
                break;
            case SPECIMEN_TOOL_COLLECT_POS_5: // delay for candy cane and move to collect1 pos
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.AFTER_DROP_DELAY) {
                    specimenTool.rightAutoSpecimenCollect();
                    actionTimer.resetTimer();
                    setPathState(PathState.PARK);

                }
                break;
            case PARK:
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_TOOL_COLLECT_DELAY) {
                    follower.followPath(park);
                    setPathState(PathState.END);
                }
                break;



            case END:
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

//        sleep(AutoConstants.STARTUP_DELAY_MS);

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

        while(!gamepad1.x) {
            Thread.yield();
        }

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

