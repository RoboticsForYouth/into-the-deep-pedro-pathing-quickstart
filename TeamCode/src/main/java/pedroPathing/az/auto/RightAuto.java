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
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;

    private final Pose startPose = new Pose(9, 60, Math.toRadians(180));

    private final Pose drop0Pose = new Pose(34, 79, Math.toRadians(180));

    private final Pose candyCane1Pose = new Pose(31, 40, Math.toRadians(-25));

    private final Pose pushInZone1Pose = new Pose(21, 38, Math.toRadians(-115));

    private final Pose candyCane2Pose = new Pose(39, 37, Math.toRadians(-40));

    private final Pose pushInZone2Pose = new Pose(16, 35, Math.toRadians(-115));

    private final Pose candyCane3Pose = new Pose(42, 26, Math.toRadians(-50));

    private final Pose pushInZone3Pose = new Pose(21, 26, Math.toRadians(-120));

    private final Pose collect1Pose = new Pose(19, 27, Math.toRadians(180));

    private final Pose drop1Pose = new Pose(37, 74, Math.toRadians(180));

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
                .addPath(new BezierLine(new Point(drop0Pose), new Point(candyCane1Pose)))
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
        switch (pathState) {
            case 0:
                follower.followPath(scorePreload);
                setPathState(1);
                break;
            case 1:
                if(pathTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_DROP_DELAY) {
                    specimenTool.gripper.rightAutoSpecimenDropPos();
                    specimenTool.firstRightAutoSpecimenDrop();


                    setPathState(2);
                }
                break;
            case 2:
                if(!follower.isBusy()) {
                    follower.followPath(spikeMark1Traj,true);
                    candyCane.rightAutoRaise();
                    setPathState(3);
                }
                break;
            case 3:
                if(!follower.isBusy()) {
                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            specimenTool.rightAutoSpecimenCollect();

                        }
                    });


                    actionTimer.resetTimer();
                    candyCane.rightAutoLower();
                    setPathState(4);

                }
                break;
            case 4:
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.SPECIMEN_DROP_DELAY) {
                    follower.followPath(pushInZone1Traj,true);
                    setPathState(5);
                }
                break;
            case 5:
                if(!follower.isBusy()) {
                    candyCane.rightAutoRaise();

                    follower.followPath(spikeMark2Traj,true);
                    setPathState(6);
                }
                break;
            case 6:
                if(!follower.isBusy()) {
                    actionTimer.resetTimer();
                    candyCane.rightAutoLower();

                    setPathState(7);
                }
                break;
            case 7:
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.CANDY_CANE_ACTION_DELAY) {

                    follower.followPath(pushInZone2Traj,true);
                    setPathState(8);
                }
                break;
            case 8:
                if(!follower.isBusy()) {
                    candyCane.rightAutoRaise();

                    follower.followPath(spikeMark3Traj,true);
                    setPathState(9);
                }
                break;
            case 9:
                if(!follower.isBusy()) {
                    actionTimer.resetTimer();
                    candyCane.rightAutoLower();

                    setPathState(10);
                }
                break;
            case 10:
                if(actionTimer.getElapsedTimeSeconds() > AutoConstants.CANDY_CANE_ACTION_DELAY) {

                    follower.followPath(pushInZone3Traj, true);
                    setPathState(11);
                }
                break;
            case 11:
                if(!follower.isBusy()) {
                    actionTimer.resetTimer();
                    candyCane.reset();

                    setPathState(12);
                }
                break;
            case 12:
                if(actionTimer.getElapsedTimeSeconds() > 0.1) { // TODO: Move to AutoConstants

                    follower.followPath(collect1Traj, true);
                    setPathState(13);
                }
                break;
            case 13:
                if(!follower.isBusy()) {
                    specimenTool.arm.setPosAndWait((int) DoubleArm.DoubleArmPos.RIGHT_AUTO_SPECIMEN_DROP_INTEMEDIATE_WAIT.getValue());

                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
                            sleep((int)AutoConstants.SPECIMEN_HANG_DELAY_MS);
                            specimenTool.rightAutoSpecimenHangPos();
                        }
                    });

                    follower.followPath(drop1Traj, true);
                    setPathState(14);
                }
                break;
            case 14:
                if(!follower.isBusy()) {
                    specimenTool.rightAutoSpecimenDrop();

                    AZUtil.runInParallel(new Runnable() {
                        @Override
                        public void run() {
//                        sleep(100);
                            specimenTool.afterDropRightAutoSpecimenCollect();
                        }
                    });

                    follower.followPath(collect2Traj, true);
                    setPathState(15);
                }
                break;
            case 15:
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
                    setPathState(16);
                }
                break;
            case 16:
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
                    setPathState(17);
                }
                break;
            case 17:
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
                    setPathState(18);
                }
                break;
            case 18:
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
                    setPathState(19);
                }
                break;
            case 19:
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
                    setPathState(20);
                }
                break;
            case 20:
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
                    setPathState(21);
                }
                break;
            case 21:
                if(!follower.isBusy()) {

                    setPathState(-1);
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
        setPathState(0);

        while (!isStopRequested() && pathState != AutoConstants.FINAL_STATE && opmodeTimer.getElapsedTimeSeconds() < AutoConstants.AUTO_TIMEOUT_SECONDS) {
            follower.update();
            autonomousPathUpdate();

            telemetry.addData("path state", pathState);
            telemetry.addData("x", follower.getPose().getX());
            telemetry.addData("y", follower.getPose().getY());
            telemetry.addData("heading", follower.getPose().getHeading());
            telemetry.addData("action timer: ", actionTimer.getElapsedTimeSeconds());
            telemetry.addData("path timer: ", pathTimer.getElapsedTimeSeconds());
            telemetry.addData("opmode timer: ", opmodeTimer.getElapsedTimeSeconds());
            telemetry.update();
        }
    }

    public void initAuto() {
        specimenTool = new SpecimenTool(this);
        specimenTool.rightAutoReset();
        candyCane = new CandyCane(this);

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

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
//        actionTimer.resetTimer();
    }



}

