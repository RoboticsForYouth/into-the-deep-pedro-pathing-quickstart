package pedroPathing.constants;

/**
 * Constants for autonomous operation timing and configuration
 * Centralizes magic numbers for easier tuning and maintenance
 */
public class AutoConstants {

    // Timing Constants (in seconds)
    public static final double SPECIMEN_DROP_DELAY = 0.6;
    public static final double CANDY_CANE_ACTION_DELAY = 0.17;
    public static final double SPECIMEN_HANG_DELAY = 0.95;
    public static final double SPECIMEN_HANG_DELAY_MS = 950; // milliseconds version
    public static final double MOTOR_WAIT_DELAY = 3;

    // Startup and Timeout
    public static final int STARTUP_DELAY_MS = 2000;
    public static final double AUTO_TIMEOUT_SECONDS = 29.5;

    // State Machine
    public static final int INITIAL_STATE = 0;
    public static final int FINAL_STATE = -1;

    // Action Delays (in milliseconds)
    public static final int GRIPPER_DROP_DELAY_MS = 80;
    public static final int GRIPPER_COLLECT_DELAY_MS = 600;
    public static final int FIRST_DROP_DELAY_MS = 220;
    public static final int ACTION_DELAY_MS = 100;

    // Path Following
    public static final double PATH_END_TIMEOUT_MS = 500;
    public static final double PATH_END_T_VALUE = 0.995;
    public static final double PATH_END_VELOCITY = 0.1;
    public static final double PATH_END_TRANSLATIONAL = 0.1;
    public static final double PATH_END_HEADING = 0.007;

    // Motor tolerance
    public static final int WAIT_TOLERANCE = 45;
}