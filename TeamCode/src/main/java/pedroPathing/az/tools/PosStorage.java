package pedroPathing.az.tools;

/**
 * Shared storage for robot position values
 * Made thread-safe with volatile keywords to prevent race conditions
 */
public class PosStorage {

    public static volatile int initialSlidesPos = 0;
    public static volatile int initialArmPos = 0;


}
