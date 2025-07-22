package pedroPathing.constants;

/**
 * Constants for autonomous operation timing and configuration
 * Centralizes magic numbers for easier tuning and maintenance
 */
public class AutoConstants {

    // Timing Constants (in seconds)
    public static final double SPECIMEN_DROP_DELAY_ARM_0 = 0.5;
    public static final double SPECIMEN_DROP_DELAY_ARM = 0.3;

    public static final double SPECIMEN_DROP_0_DELAY_EJECT = 0.8;
    public static final double PRE_CANDY_CANE_DELAY = 0.8;

    public static final double SPECIMEN_DROP_0_RESET_GRIPPER_DELAY = 0.22;
    public static final double CANDY_CANE_ACTION_DELAY = 0.15;
    public static final double AFTER_DROP_0_DELAY = 0.1;

    public static final double AFTER_DROP_DELAY = 0.3;
    public static final double SPECIMEN_TOOL_COLLECT_DELAY = 0.3;



    public static final double SPECIMEN_HANG_DELAY = 1.2;


    public static final double SPECIMEN_HANG_DELAY_MS = 950; // milliseconds version
    public static final double MOTOR_WAIT_DELAY = 3;

    // Startup and Timeout
    public static final int STARTUP_DELAY_MS = 1000;
    public static final double AUTO_TIMEOUT_SECONDS = 29.5;

    // State Machine
//    public static final int INITIAL_STATE = 0;
//    public static final int FINAL_STATE = -1;

    // Action Delays (in milliseconds)
    public static final double GRIPPER_DROP_DELAY = 0.85;
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
    public static final int HIGH_WAIT_TOLERANCE = 120;

}