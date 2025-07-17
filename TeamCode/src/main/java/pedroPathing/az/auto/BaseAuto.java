package pedroPathing.az.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import pedroPathing.az.tools.AZUtil;
import pedroPathing.az.tools.CandyCane;
import pedroPathing.az.tools.SpecimenTool;
import pedroPathing.constants.AutoConstants;
import pedroPathing.constants.AutoState;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

/**
 * Base class for autonomous OpModes providing common functionality
 * 
 * Features:
 * - State machine management with timeout protection
 * - Error handling and recovery mechanisms
 * - Common initialization and cleanup
 * - Telemetry logging system
 * - Pedro Pathing integration
 */
public abstract class BaseAuto extends LinearOpMode {
    
    // Core Components
    protected Follower follower;
    protected SpecimenTool specimenTool;
    protected CandyCane candyCane;
    
    // Timing Management
    protected Timer pathTimer, actionTimer, opmodeTimer, stateTimer;
    
    // State Management
    protected AutoState currentState = AutoState.RIGHT_SCORE_PRELOAD; // Default, override in subclasses
    protected AutoState previousState = null;
    protected boolean recoveryMode = false;
    
    // Configuration
    protected static final double STATE_TIMEOUT_SECONDS = 5.0;
    protected static final double STUCK_THRESHOLD_SECONDS = 2.0;
    
    // Telemetry and Logging
    protected int stateChangeCount = 0;
    protected double totalAutoTime = 0;
    
    /**
     * Abstract methods that must be implemented by subclasses
     */
    protected abstract void buildPaths();
    protected abstract void autonomousPathUpdate();
    protected abstract AutoState getInitialState();
    
    /**
     * Main autonomous execution loop
     */
    @Override
    public void runOpMode() throws InterruptedException {
        initializeAuto();
        waitForStart();
        
        if (opModeIsActive()) {
            executeAutonomous();
        }
        
        cleanupAuto();
    }
    
    /**
     * Initialize autonomous - common setup for all autonomous programs
     */
    protected void initializeAuto() {
        // Initialize subsystems
        specimenTool = new SpecimenTool(this);
        candyCane = new CandyCane(this);
        
        // Initialize timers
        pathTimer = new Timer();
        actionTimer = new Timer();
        opmodeTimer = new Timer();
        stateTimer = new Timer();
        
        // Initialize Pedro Pathing
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        
        // Set initial state
        currentState = getInitialState();
        
        // Build paths
        buildPaths();
        
        // Reset subsystems to starting positions
        resetSubsystems();
        
        telemetry.addLine("BaseAuto Initialized");
        telemetry.addData("Starting State", currentState.getDescription());
        telemetry.update();
    }
    
    /**
     * Main autonomous execution with error handling
     */
    protected void executeAutonomous() {
        sleep(AutoConstants.STARTUP_DELAY_MS);
        
        opmodeTimer.resetTimer();
        stateTimer.resetTimer();
        setAutoState(getInitialState());
        
        while (opModeIsActive() && !currentState.isFinalState() && 
               opmodeTimer.getElapsedTimeSeconds() < AutoConstants.AUTO_TIMEOUT_SECONDS) {
            
            try {
                // Check for state timeout
                checkStateTimeout();
                
                // Update follower
                follower.update();
                
                // Run autonomous logic
                autonomousPathUpdate();
                
                // Update telemetry
                updateTelemetry();
                
            } catch (Exception e) {
                // Handle any runtime errors
                handleError("Runtime error in autonomous: " + e.getMessage());
                break;
            }
        }
        
        // Final telemetry
        totalAutoTime = opmodeTimer.getElapsedTimeSeconds();
        telemetry.addLine("Autonomous Completed");
        telemetry.addData("Final State", currentState.getDescription());
        telemetry.addData("Total Time", "%.2f seconds", totalAutoTime);
        telemetry.addData("State Changes", stateChangeCount);
        telemetry.update();
    }
    
    /**
     * Set autonomous state with logging and timeout protection
     */
    protected void setAutoState(AutoState newState) {
        if (newState != currentState) {
            previousState = currentState;
            currentState = newState;
            stateChangeCount++;
            
            // Reset timers
            pathTimer.resetTimer();
            stateTimer.resetTimer();
            
            // Log state change
            telemetry.addLine(String.format("State: %s -> %s", 
                previousState != null ? previousState.name() : "INIT", 
                currentState.name()));
        }
    }
    
    /**
     * Check for state timeouts and handle recovery
     */
    protected void checkStateTimeout() {
        if (stateTimer.getElapsedTimeSeconds() > STATE_TIMEOUT_SECONDS) {
            handleTimeout();
        }
    }
    
    /**
     * Handle state timeout - attempt recovery
     */
    protected void handleTimeout() {
        telemetry.addLine("WARNING: State timeout in " + currentState.name());
        
        if (!recoveryMode) {
            recoveryMode = true;
            // Try to recover by advancing to next logical state
            attemptRecovery();
        } else {
            // Already in recovery, terminate autonomous
            setAutoState(AutoState.TIMEOUT_RECOVERY);
        }
    }
    
    /**
     * Attempt to recover from timeout by advancing state
     */
    protected void attemptRecovery() {
        telemetry.addLine("Attempting recovery...");
        
        // Stop current path following
        follower.breakFollowing();
        
        // Wait briefly for systems to settle
        sleep(200);
        
        // Reset timers
        stateTimer.resetTimer();
        recoveryMode = false;
        
        telemetry.addLine("Recovery attempt completed");
    }
    
    /**
     * Handle critical errors
     */
    protected void handleError(String errorMessage) {
        telemetry.addLine("ERROR: " + errorMessage);
        telemetry.update();
        
        // Stop all movement
        follower.breakFollowing();
        
        // Set error state
        setAutoState(AutoState.ERROR_RECOVERY);
        
        // Emergency stop subsystems
        emergencyStopSubsystems();
    }
    
    /**
     * Emergency stop all subsystems
     */
    protected void emergencyStopSubsystems() {
        try {
            if (specimenTool != null && specimenTool.slides != null) {
                specimenTool.slides.emergencyResetPos();
            }
            if (specimenTool != null && specimenTool.arm != null) {
                specimenTool.arm.reset();
            }
        } catch (Exception e) {
            telemetry.addLine("Error in emergency stop: " + e.getMessage());
        }
    }
    
    /**
     * Reset subsystems to starting positions
     */
    protected abstract void resetSubsystems();
    
    /**
     * Update telemetry with common information
     */
    protected void updateTelemetry() {
        telemetry.addData("State", currentState.getDescription());
        telemetry.addData("State Number", currentState.getValue());
        telemetry.addData("Auto Time", "%.1f", opmodeTimer.getElapsedTimeSeconds());
        telemetry.addData("State Time", "%.1f", stateTimer.getElapsedTimeSeconds());
        telemetry.addData("Path Time", "%.1f", pathTimer.getElapsedTimeSeconds());
        telemetry.addData("Action Time", "%.1f", actionTimer.getElapsedTimeSeconds());
        
        if (follower != null) {
            telemetry.addData("X", "%.1f", follower.getPose().getX());
            telemetry.addData("Y", "%.1f", follower.getPose().getY());
            telemetry.addData("Heading", "%.1f°", Math.toDegrees(follower.getPose().getHeading()));
            telemetry.addData("Following", follower.isBusy() ? "Yes" : "No");
        }
        
        if (recoveryMode) {
            telemetry.addLine("*** RECOVERY MODE ***");
        }
        
        telemetry.update();
    }
    
    /**
     * Cleanup autonomous resources
     */
    protected void cleanupAuto() {
        if (follower != null) {
            follower.breakFollowing();
        }
        
        telemetry.addLine("Autonomous cleanup completed");
        telemetry.update();
    }
    
    /**
     * Utility method to run actions in parallel safely
     */
    protected void runInParallel(Runnable action) {
        AZUtil.runInParallel(action);
    }
    
    /**
     * Utility method to check if follower is busy with timeout
     */
    protected boolean isFollowerBusyWithTimeout(double timeoutSeconds) {
        return follower.isBusy() && pathTimer.getElapsedTimeSeconds() < timeoutSeconds;
    }
}