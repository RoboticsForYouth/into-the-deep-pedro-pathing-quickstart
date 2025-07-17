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

/**
 * Left-side autonomous strategy focused on sample collection and basket scoring
 * 
 * Strategy Overview:
 * 1. Score preloaded sample in high basket (8 points)
 * 2. Collect and score 3 neutral samples (24 points)
 * 3. Push remaining samples to observation zone (6 points)
 * 4. Attempt level 1 ascent if time permits (15 points)
 * 5. Park in observation zone (3 points)
 * 
 * Target Score: 50-56 points in autonomous
 */
@Autonomous(name = "Left Auto - Sample Strategy", preselectTeleOp = "IntoTheDeepTeleOp")
public class LeftAuto extends BaseAuto {
    
    // Field Poses - Left side starting positions
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
                follower.followPath(scorePreloadPath);
                setAutoState(AutoState.LEFT_COLLECT_SAMPLE_1);
                break;
                
            case LEFT_COLLECT_SAMPLE_1:
                if (!follower.isBusy()) {
                    // Move to high basket position and drop preloaded sample
                    specimenTool.leftAutoDropHighBasket();
                    
                    runInParallel(() -> {
                        sleep(500); // Wait for drop
                        // Move to collect first sample
                        follower.followPath(collectSample1Path, true);
                    });
                    
                    setAutoState(AutoState.LEFT_SCORE_SAMPLE_1);
                }
                break;
                
            case LEFT_SCORE_SAMPLE_1:
                if (!follower.isBusy()) {
                    // Collect first sample
                    specimenTool.leftAutoCollect(
                        Slides.SlidesPos.LEFT_AUTO_PICKUP_FIRST,
                        EnhancedClaw.WRIST_POS.LEFT_AUTO_PICKUP_FIRST,
                        DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_FIRST
                    );
                    
                    runInParallel(() -> {
                        sleep(800); // Wait for collection
                        // Move to score first sample
                        follower.followPath(scoreSample1Path, true);
                    });
                    
                    setAutoState(AutoState.LEFT_COLLECT_SAMPLE_2);
                }
                break;
                
            case LEFT_COLLECT_SAMPLE_2:
                if (!follower.isBusy()) {
                    // Score first sample
                    specimenTool.leftAutoLaterDropsHighBasket();
                    
                    runInParallel(() -> {
                        sleep(600); // Wait for scoring
                        // Move to collect second sample
                        follower.followPath(collectSample2Path, true);
                    });
                    
                    setAutoState(AutoState.LEFT_SCORE_SAMPLE_2);
                }
                break;
                
            case LEFT_SCORE_SAMPLE_2:
                if (!follower.isBusy()) {
                    // Collect second sample
                    specimenTool.leftAutoCollect(
                        Slides.SlidesPos.LEFT_AUTO_PICKUP_SECOND,
                        EnhancedClaw.WRIST_POS.LEFT_AUTO_PICKUP_SECOND,
                        DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_SECOND
                    );
                    
                    runInParallel(() -> {
                        sleep(800); // Wait for collection
                        // Move to score second sample
                        follower.followPath(scoreSample2Path, true);
                    });
                    
                    setAutoState(AutoState.LEFT_COLLECT_SAMPLE_3);
                }
                break;
                
            case LEFT_COLLECT_SAMPLE_3:
                if (!follower.isBusy()) {
                    // Score second sample
                    specimenTool.leftAutoLaterDropsHighBasket();
                    
                    runInParallel(() -> {
                        sleep(600); // Wait for scoring
                        // Move to collect third sample
                        follower.followPath(collectSample3Path, true);
                    });
                    
                    setAutoState(AutoState.LEFT_SCORE_SAMPLE_3);
                }
                break;
                
            case LEFT_SCORE_SAMPLE_3:
                if (!follower.isBusy()) {
                    // Collect third sample
                    specimenTool.leftAutoCollect(
                        Slides.SlidesPos.LEFT_AUTO_PICKUP_THIRD,
                        EnhancedClaw.WRIST_POS.LEFT_AUTO_PICKUP_THIRD,
                        DoubleArm.DoubleArmPos.LEFT_AUTO_PICKUP_THIRD
                    );
                    
                    runInParallel(() -> {
                        sleep(800); // Wait for collection
                        // Move to score third sample
                        follower.followPath(scoreSample2Path, true); // Reuse path to basket
                    });
                    
                    setAutoState(AutoState.LEFT_PUSH_SAMPLES_TO_ZONE);
                }
                break;
                
            case LEFT_PUSH_SAMPLES_TO_ZONE:
                if (!follower.isBusy()) {
                    // Score third sample
                    specimenTool.leftAutoLaterDropsHighBasket();
                    
                    runInParallel(() -> {
                        sleep(600); // Wait for scoring
                        // Move to push remaining samples
                        follower.followPath(pushSamplesPath, true);
                    });
                    
                    setAutoState(AutoState.LEFT_ATTEMPT_ASCENT);
                }
                break;
                
            case LEFT_ATTEMPT_ASCENT:
                if (!follower.isBusy()) {
                    // Check if we have time for ascent (leave 5 seconds buffer)
                    if (opmodeTimer.getElapsedTimeSeconds() < AutoConstants.AUTO_TIMEOUT_SECONDS - 5.0) {
                        // Attempt level 1 ascent
                        candyCane.leftAutoLevelOneAscent();
                        
                        runInParallel(() -> {
                            sleep(1000); // Wait for ascent setup
                            follower.followPath(ascentPath, true);
                        });
                        
                        setAutoState(AutoState.LEFT_PARK);
                    } else {
                        // Skip ascent, go directly to park
                        setAutoState(AutoState.LEFT_PARK);
                    }
                }
                break;
                
            case LEFT_PARK:
                if (!follower.isBusy()) {
                    // Final parking
                    follower.followPath(parkPath, true);
                    setAutoState(AutoState.COMPLETED);
                }
                break;
                
            case COMPLETED:
            case ERROR_RECOVERY:
            case TIMEOUT_RECOVERY:
                // Autonomous finished
                break;
                
            default:
                handleError("Unknown state: " + currentState);
                break;
        }
    }
    
    @Override
    protected void resetSubsystems() {
        // Reset to starting positions for left auto
        specimenTool.leftAutoReset();
        candyCane.reset();
        
        // Set starting pose
        follower.setStartingPose(startPose);
    }
}