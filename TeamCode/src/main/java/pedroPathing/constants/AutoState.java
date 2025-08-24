package pedroPathing.constants;

/**
 * Enum for autonomous state management to replace magic numbers
 * Provides better readability and type safety for state machines
 */
public enum AutoState {
    // Right Auto States (Specimen-focused)
    RIGHT_SCORE_PRELOAD(0, "Score preloaded specimen"),
    RIGHT_WAIT_FOR_DROP(1, "Wait for specimen drop timing"),
    RIGHT_MOVE_TO_SPIKE_1(2, "Move to first spike mark"),
    RIGHT_COLLECT_AT_SPIKE_1(3, "Collect at spike mark 1"),
    RIGHT_PUSH_TO_ZONE_1(4, "Push sample to observation zone 1"),
    RIGHT_MOVE_TO_SPIKE_2(5, "Move to second spike mark"),
    RIGHT_COLLECT_AT_SPIKE_2(6, "Collect at spike mark 2"),
    RIGHT_PUSH_TO_ZONE_2(7, "Push sample to observation zone 2"),
    RIGHT_MOVE_TO_SPIKE_3(8, "Move to third spike mark"),
    RIGHT_COLLECT_AT_SPIKE_3(9, "Collect at spike mark 3"),
    RIGHT_PUSH_TO_ZONE_3(10, "Push sample to observation zone 3"),
    RIGHT_MOVE_TO_COLLECT_1(11, "Move to specimen collection 1"),
    RIGHT_COLLECT_SPECIMEN_1(12, "Collect first specimen"),
    RIGHT_SCORE_SPECIMEN_1(13, "Score first collected specimen"),
    RIGHT_DROP_SPECIMEN_1(14, "Drop first specimen"),
    RIGHT_COLLECT_SPECIMEN_2(15, "Collect second specimen"),
    RIGHT_SCORE_SPECIMEN_2(16, "Score second specimen"),
    RIGHT_DROP_SPECIMEN_2(17, "Drop second specimen"),
    RIGHT_COLLECT_SPECIMEN_3(18, "Collect third specimen"),
    RIGHT_SCORE_SPECIMEN_3(19, "Score third specimen"),
    RIGHT_DROP_SPECIMEN_3(20, "Drop third specimen"),
    RIGHT_COLLECT_SPECIMEN_4(21, "Collect fourth specimen"),
    RIGHT_SCORE_SPECIMEN_4(22, "Score fourth specimen"),
    RIGHT_DROP_SPECIMEN_4(23, "Drop fourth specimen"),
    RIGHT_PARK(24, "Park in observation zone"),

    // Left Auto States (Sample-focused)
    LEFT_SCORE_PRELOAD_SAMPLE(0, "Score preloaded sample in high basket"),
    LEFT_COLLECT_SAMPLE_1(1, "Collect first neutral sample"),
    LEFT_SCORE_SAMPLE_1(2, "Score first sample in high basket"),
    LEFT_COLLECT_SAMPLE_2(3, "Collect second neutral sample"),
    LEFT_SCORE_SAMPLE_2(4, "Score second sample in high basket"),
    LEFT_COLLECT_SAMPLE_3(5, "Collect third neutral sample"),
    LEFT_SCORE_SAMPLE_3(6, "Score third sample in high basket"),
    LEFT_PUSH_SAMPLES_TO_ZONE(7, "Push remaining samples to observation zone"),
    LEFT_ATTEMPT_ASCENT(8, "Attempt level 1 ascent if time permits"),
    LEFT_PARK(9, "Park in observation zone"),

    // Common States
    COMPLETED(-1, "Autonomous completed"),
    ERROR_RECOVERY(-2, "Error recovery state"),
    TIMEOUT_RECOVERY(-3, "Timeout recovery state");

    private final int value;
    private final String description;

    AutoState(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Get AutoState by value - useful for converting from legacy magic numbers
     */
    public static AutoState fromValue(int value) {
        for (AutoState state : AutoState.values()) {
            if (state.value == value) {
                return state;
            }
        }
        return ERROR_RECOVERY; // Default to error state for unknown values
    }

    /**
     * Check if this is a final state (autonomous should stop)
     */
    public boolean isFinalState() {
        return this == COMPLETED || this == ERROR_RECOVERY || this == TIMEOUT_RECOVERY;
    }

    /**
     * Check if this is a Right Auto state
     */
    public boolean isRightAutoState() {
        return this.name().startsWith("RIGHT_");
    }

    /**
     * Check if this is a Left Auto state
     */
    public boolean isLeftAutoState() {
        return this.name().startsWith("LEFT_");
    }
}