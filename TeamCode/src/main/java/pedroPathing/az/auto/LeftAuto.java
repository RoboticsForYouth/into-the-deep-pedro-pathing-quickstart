package pedroPathing.az.auto;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import pedroPathing.az.tools.DoubleArm;
import pedroPathing.az.tools.EnhancedClaw;
import pedroPathing.az.tools.Slides;
import pedroPathing.constants.AutoConstants;
import pedroPathing.constants.AutoState;


@Autonomous(name = "Left Auto - Sample Strategy", preselectTeleOp = "IntoTheDeepTeleOp")
public class LeftAuto extends BaseAuto {

    private final Pose startPose = new Pose(9, -60, Math.toRadians(0)); // Left side start
    // High basket scoring position
    private final Pose highBasketPose = new Pose(55, -55, Math.toRadians(45));
    // Sample collection poses (neutral samples on left side)
    private final Pose sample1Pose = new Pose(35, -24, Math.toRadians(0));
    private final Pose sample2Pose = new Pose(45, -24, Math.toRadians(0));
    private final Pose sample3Pose = new Pose(55, -24, Math.toRadians(0));
    // Observation zone for sample pushing
    private final Pose observationZonePose = new Pose(24, -60, Math.toRadians(-90));
    // Ascent position (if time permits)
    private final Pose ascentPose = new Pose(20, -30, Math.toRadians(0));
    // Parking position
    private final Pose parkPose = new Pose(24, -12, Math.toRadians(0));
    // Paths
    private Path scorePreloadPath, parkPath;
    private PathChain collectSample1Path, scoreSample1Path, collectSample2Path,
            scoreSample2Path, collectSample3Path, scoreSample3Path,
            pushSamplesPath, ascentPath;

    @Override
    protected AutoState getInitialState() {
        return AutoState.LEFT_SCORE_PRELOAD_SAMPLE;
    }

    @Override
    protected void buildPaths() {
        // Score preloaded sample
        scorePreloadPath = new Path(new BezierLine(new Point(startPose), new Point(highBasketPose)));
        scorePreloadPath.setLinearHeadingInterpolation(startPose.getHeading(), highBasketPose.getHeading());

        // Sample 1 collection and scoring
        collectSample1Path = follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Point(highBasketPose),
                        new Point(40, -40), // Control point
                        new Point(sample1Pose)
                ))
                .setLinearHeadingInterpolation(highBasketPose.getHeading(), sample1Pose.getHeading())
                .build();

        scoreSample1Path = follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Point(sample1Pose),
                        new Point(45, -40), // Control point
                        new Point(highBasketPose)
                ))
                .setLinearHeadingInterpolation(sample1Pose.getHeading(), highBasketPose.getHeading())
                .build();

        // Sample 2 collection and scoring
        collectSample2Path = follower.pathBuilder()
                .addPath(new BezierLine(new Point(highBasketPose), new Point(sample2Pose)))
                .setLinearHeadingInterpolation(highBasketPose.getHeading(), sample2Pose.getHeading())
                .build();

        scoreSample2Path = follower.pathBuilder()
                .addPath(new BezierLine(new Point(sample2Pose), new Point(highBasketPose)))
                .setLinearHeadingInterpolation(sample2Pose.getHeading(), highBasketPose.getHeading())
                .build();

        // Sample 3 collection and scoring
        collectSample3Path = follower.pathBuilder()
                .addPath(new BezierLine(new Point(highBasketPose), new Point(sample3Pose)))
                .setLinearHeadingInterpolation(highBasketPose.getHeading(), sample3Pose.getHeading())
                .build();

        scoreSample3Path = follower.pathBuilder()
                .addPath(new BezierLine(new Point(sample3Pose), new Point(highBasketPose)))
                .setLinearHeadingInterpolation(sample3Pose.getHeading(), highBasketPose.getHeading())
                .build();

        // Push samples to observation zone
        pushSamplesPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(highBasketPose), new Point(observationZonePose)))
                .setLinearHeadingInterpolation(highBasketPose.getHeading(), observationZonePose.getHeading())
                .build();

        // Level 1 ascent attempt
        ascentPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(observationZonePose), new Point(ascentPose)))
                .setLinearHeadingInterpolation(observationZonePose.getHeading(), ascentPose.getHeading())
                .build();

        // Final parking
        parkPath = new Path(new BezierLine(new Point(ascentPose), new Point(parkPose)));
        parkPath.setLinearHeadingInterpolation(ascentPose.getHeading(), parkPose.getHeading());
    }

    @Override
    protected void autonomousPathUpdate() {
        switch (currentState) {

            case LEFT_SCORE_PRELOAD_SAMPLE:
                if (!follower.isBusy()) {
                    // Drop preloaded sample in parallel
                    new Thread(() -> {
                        specimenTool.leftAutoDropHighBasket(); // TODO implement
                    }).start();

                    // Move to collect first sample
                    follower.followPath(collectSample1Path);

                    currentState = AutoState.LEFT_SCORE_SAMPLE_1;
                }
                break;

            case LEFT_SCORE_SAMPLE_1:
                if (!follower.isBusy()) {
                    // Collect first sample while moving to score
                    new Thread(() -> {
                        specimenTool.leftAutoCollect(
                                Slides.SlidesPos.LEFT_AUTO_PICKUP_FIRST,
                                EnhancedClaw.WRIST_POS.LEFT_AUTO_PICKUP_FIRST,
                                DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_FIRST
                        ); // TODO implement
                    }).start();

                    follower.followPath(scoreSample1Path);
                    currentState = AutoState.LEFT_COLLECT_SAMPLE_2;
                }
                break;

            case LEFT_COLLECT_SAMPLE_2:
                if (!follower.isBusy()) {
                    // Score first sample while moving to collect second
                    new Thread(() -> {
                        specimenTool.leftAutoLaterDropsHighBasket(); // TODO implement
                    }).start();

                    follower.followPath(collectSample2Path);
                    currentState = AutoState.LEFT_SCORE_SAMPLE_2;
                }
                break;

            case LEFT_SCORE_SAMPLE_2:
                if (!follower.isBusy()) {
                    // Collect second sample while moving to score
                    new Thread(() -> {
                        specimenTool.leftAutoCollect(
                                Slides.SlidesPos.LEFT_AUTO_PICKUP_SECOND,
                                EnhancedClaw.WRIST_POS.LEFT_AUTO_PICKUP_SECOND,
                                DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_SECOND
                        ); // TODO implement
                    }).start();

                    follower.followPath(scoreSample2Path);
                    currentState = AutoState.LEFT_COLLECT_SAMPLE_3;
                }
                break;

            case LEFT_COLLECT_SAMPLE_3:
                if (!follower.isBusy()) {
                    // Score second sample while moving to collect third
                    new Thread(() -> {
                        specimenTool.leftAutoLaterDropsHighBasket(); // TODO implement
                    }).start();

                    follower.followPath(collectSample3Path);
                    currentState = AutoState.LEFT_SCORE_SAMPLE_3;
                }
                break;

            case LEFT_SCORE_SAMPLE_3:
                if (!follower.isBusy()) {
                    // Collect third sample while moving to score
                    new Thread(() -> {
                        specimenTool.leftAutoCollect(
                                Slides.SlidesPos.LEFT_AUTO_PICKUP_THIRD,
                                EnhancedClaw.WRIST_POS.LEFT_AUTO_PICKUP_THIRD,
                                DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_THIRD
                        ); // TODO implement
                    }).start();
                    follower.followPath(scoreSample3Path);
                    currentState = AutoState.LEFT_PUSH_SAMPLES_TO_ZONE;
                }
                break;
            case LEFT_PUSH_SAMPLES_TO_ZONE:
                if (!follower.isBusy()) {
                    // Score third sample while moving to observation zone
                    new Thread(() -> {
                        specimenTool.leftAutoLaterDropsHighBasket(); // TODO implement
                    }).start();

                    follower.followPath(pushSamplesPath);
                    currentState = AutoState.LEFT_ATTEMPT_ASCENT;
                }
                break;

            case LEFT_ATTEMPT_ASCENT:
                if (!follower.isBusy()) {
                    // Attempt ascent while moving along ascent path
                    new Thread(() -> {
                        candyCane.leftAutoLevelOneAscent(); // TODO implement
                    }).start();

                    follower.followPath(ascentPath);
                    currentState = AutoState.LEFT_PARK;
                }
                break;

            case LEFT_PARK:
                if (!follower.isBusy()) {
                    // Final parking
                    follower.followPath(parkPath);
                    currentState = AutoState.COMPLETED;
                }
                break;

            case COMPLETED:
                // Autonomous finished
                break;

            default:
                handleError("Unknown state: " + currentState);
                break;
        }
    }

    @Override
    protected void resetSubsystems() {
        // Reset all subsystems safely
        specimenTool.leftAutoReset(); // TODO implement
        candyCane.reset(); // TODO implement
        follower.setStartingPose(startPose);
    }
}
