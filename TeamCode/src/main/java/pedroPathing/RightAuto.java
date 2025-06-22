package pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;



@Autonomous
public class RightAuto extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;

    /** Start Pose of our robot */
    private final Pose startPose = new Pose(9, 60, Math.toRadians(180));

    private final Pose drop0Pose = new Pose(14, 60, Math.toRadians(180));

    private final Pose candyCane1Pose = new Pose(37, 121, Math.toRadians(0));

    private final Pose pushInZone1Pose = new Pose(43, 130, Math.toRadians(0));

    private final Pose candyCane2Pose = new Pose(49, 135, Math.toRadians(0));

    private final Pose pushInZone2Pose = new Pose(43, 130, Math.toRadians(0));

    private final Pose candyCane3Pose = new Pose(49, 135, Math.toRadians(0));

    private final Pose pushInZone3Pose = new Pose(43, 130, Math.toRadians(0));

    private final Pose collect1Pose = new Pose(43, 130, Math.toRadians(0));

    private final Pose drop1Pose = new Pose(43, 130, Math.toRadians(0));

    private final Pose collect2Pose = new Pose(43, 130, Math.toRadians(0));

    private final Pose drop2Pose = new Pose(43, 130, Math.toRadians(0));

    private final Pose collect3Pose = new Pose(43, 130, Math.toRadians(0));

    private final Pose drop3Pose = new Pose(43, 130, Math.toRadians(0));

    private final Pose parkPose = new Pose(60, 98, Math.toRadians(90));

    private final Pose parkControlPose = new Pose(60, 98, Math.toRadians(90));

    private Path scorePreload, park;
    private PathChain spikeMark1, spikeMark2, spikeMark3, pushInZone1, pushInZone2, pushInZone3, collect1, drop1, collect2, drop2, collect3, drop3;

    public void buildPaths() {

        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(drop0Pose)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), drop0Pose.getHeading());

        spikeMark1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(drop0Pose), new Point(candyCane1Pose)))
                .setLinearHeadingInterpolation(drop0Pose.getHeading(), candyCane1Pose.getHeading())
                .build();

        pushInZone1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(candyCane1Pose), new Point(pushInZone1Pose)))
                .setLinearHeadingInterpolation(candyCane1Pose.getHeading(), pushInZone1Pose.getHeading())
                .build();

        spikeMark2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pushInZone1Pose), new Point(candyCane2Pose)))
                .setLinearHeadingInterpolation(pushInZone1Pose.getHeading(), candyCane2Pose.getHeading())
                .build();

        pushInZone2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(candyCane2Pose), new Point(pushInZone2Pose)))
                .setLinearHeadingInterpolation(candyCane2Pose.getHeading(), pushInZone2Pose.getHeading())
                .build();

        spikeMark3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pushInZone2Pose), new Point(candyCane3Pose)))
                .setLinearHeadingInterpolation(pushInZone2Pose.getHeading(), candyCane3Pose.getHeading())
                .build();

        pushInZone3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(candyCane3Pose), new Point(pushInZone3Pose)))
                .setLinearHeadingInterpolation(candyCane3Pose.getHeading(), pushInZone3Pose.getHeading())
                .build();

        collect1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pushInZone3Pose), new Point(collect1Pose)))
                .setLinearHeadingInterpolation(pushInZone3Pose.getHeading(), collect1Pose.getHeading())
                .build();

        drop1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(collect1Pose), new Point(drop1Pose)))
                .setLinearHeadingInterpolation(collect1Pose.getHeading(), drop1Pose.getHeading())
                .build();

        collect2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(drop1Pose), new Point(collect2Pose)))
                .setLinearHeadingInterpolation(drop1Pose.getHeading(), collect2Pose.getHeading())
                .build();

        drop2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(collect2Pose), new Point(drop2Pose)))
                .setLinearHeadingInterpolation(collect2Pose.getHeading(), drop2Pose.getHeading())
                .build();

        collect3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(drop2Pose), new Point(collect3Pose)))
                .setLinearHeadingInterpolation(drop2Pose.getHeading(), collect3Pose.getHeading())
                .build();

        drop3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(collect3Pose), new Point(drop3Pose)))
                .setLinearHeadingInterpolation(collect3Pose.getHeading(), drop3Pose.getHeading())
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

                if(!follower.isBusy()) {

                    follower.followPath(spikeMark1,true);
                    setPathState(2);
                }
                break;
            case 2:
                if(!follower.isBusy()) {

                    follower.followPath(pushInZone1,true);
                    setPathState(3);
                }
                break;
            case 3:
                if(!follower.isBusy()) {

                    follower.followPath(spikeMark2,true);
                    setPathState(4);
                }
                break;
            case 4:
                if(!follower.isBusy()) {

                    follower.followPath(pushInZone2,true);
                    setPathState(5);
                }
                break;
            case 5:
                if(!follower.isBusy()) {

                    follower.followPath(spikeMark3,true);
                    setPathState(6);
                }
                break;
            case 6:
                if(!follower.isBusy()) {

                    follower.followPath(pushInZone3, true);
                    setPathState(7);
                }
                break;
            case 7:
                if(!follower.isBusy()) {

                    follower.followPath(collect1, true);
                    setPathState(8);
                }
                break;
            case 8:
                if(!follower.isBusy()) {

                    follower.followPath(drop1, true);
                    setPathState(9);
                }
                break;
            case 9:
                if(!follower.isBusy()) {

                    follower.followPath(collect2, true);
                    setPathState(10);
                }
                break;
            case 10:
                if(!follower.isBusy()) {

                    follower.followPath(drop2, true);
                    setPathState(11);
                }
                break;
            case 11:
                if(!follower.isBusy()) {

                    follower.followPath(collect3, true);
                    setPathState(12);
                }
                break;
            case 12:
                if(!follower.isBusy()) {

                    follower.followPath(drop3, true);
                    setPathState(13);
                }
                break;
            case 13:
                if(!follower.isBusy()) {

                    follower.followPath(park,true);
                    setPathState(14);
                }
                break;
            case 14:
                if(!follower.isBusy()) {

                    setPathState(-1);
                }
                break;
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {

        follower.update();
        autonomousPathUpdate();

        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        specimenTool = new SpecimenTool(this);

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

}

