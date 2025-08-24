package pedroPathing.az.tools;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Command queue system for TeleOp to optimize threading
 * Replaces the pattern of creating new threads for every button press
 *
 * Features:
 * - Single background thread processes commands sequentially
 * - Thread-safe command queuing
 * - Prevents overlapping actions that could conflict
 * - Button debouncing built-in
 * - Emergency stop capability
 */
public class CommandQueue {

    private final ConcurrentLinkedQueue<Command> commandQueue = new ConcurrentLinkedQueue<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final AtomicBoolean processing = new AtomicBoolean(false);
    private final AtomicBoolean emergencyStop = new AtomicBoolean(false);

    // Button debouncing
    private long lastCommandTime = 0;
    private static final long DEBOUNCE_MS = 150; // 150ms debounce

    /**
     * Command interface for actions to be queued
     */
    public interface Command {
        void execute() throws Exception;
        String getName();
        boolean isInterruptible(); // Can this command be interrupted by emergency stop?
    }

    /**
     * Simple command implementation
     */
    public static class SimpleCommand implements Command {
        private final Runnable action;
        private final String name;
        private final boolean interruptible;

        public SimpleCommand(String name, Runnable action) {
            this(name, action, true);
        }

        public SimpleCommand(String name, Runnable action, boolean interruptible) {
            this.name = name;
            this.action = action;
            this.interruptible = interruptible;
        }

        @Override
        public void execute() {
            action.run();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isInterruptible() {
            return interruptible;
        }
    }

    /**
     * High priority command that skips debouncing
     */
    public static class PriorityCommand extends SimpleCommand {
        public PriorityCommand(String name, Runnable action) {
            super(name, action, false);
        }
    }

    /**
     * Start the command processor
     */
    public void start() {
        executor.submit(this::processCommands);
    }

    /**
     * Add a command to the queue with debouncing
     */
    public boolean addCommand(Command command) {
        return addCommand(command, true);
    }

    /**
     * Add a command to the queue
     * @param command The command to execute
     * @param useDebounce Whether to apply debouncing
     * @return true if command was added, false if debounced
     */
    public boolean addCommand(Command command, boolean useDebounce) {
        if (emergencyStop.get()) {
            return false; // Don't accept commands during emergency stop
        }

        long currentTime = System.currentTimeMillis();

        // Apply debouncing unless it's a priority command
        if (useDebounce && !(command instanceof PriorityCommand)) {
            if (currentTime - lastCommandTime < DEBOUNCE_MS) {
                return false; // Command debounced
            }
        }

        lastCommandTime = currentTime;
        commandQueue.offer(command);
        return true;
    }

    /**
     * Add a simple command using a lambda
     */
    public boolean addCommand(String name, Runnable action) {
        return addCommand(new SimpleCommand(name, action));
    }

    /**
     * Add a priority command that bypasses debouncing
     */
    public boolean addPriorityCommand(String name, Runnable action) {
        return addCommand(new PriorityCommand(name, action), false);
    }

    /**
     * Clear all pending commands
     */
    public void clearQueue() {
        commandQueue.clear();
    }

    /**
     * Emergency stop - clear queue and set emergency flag
     */
    public void emergencyStop() {
        emergencyStop.set(true);
        clearQueue();
    }

    /**
     * Resume normal operation after emergency stop
     */
    public void resume() {
        emergencyStop.set(false);
    }

    /**
     * Check if currently processing a command
     */
    public boolean isProcessing() {
        return processing.get();
    }

    /**
     * Get number of pending commands
     */
    public int getQueueSize() {
        return commandQueue.size();
    }

    /**
     * Check if emergency stop is active
     */
    public boolean isEmergencyStopped() {
        return emergencyStop.get();
    }

    /**
     * Main command processing loop
     */
    private void processCommands() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Command command = commandQueue.poll();

                if (command != null) {
                    // Check emergency stop
                    if (emergencyStop.get() && command.isInterruptible()) {
                        continue; // Skip interruptible commands during emergency
                    }

                    processing.set(true);

                    try {
                        command.execute();
                    } catch (Exception e) {
                        // Log error but continue processing
                        System.err.println("Error executing command '" + command.getName() + "': " + e.getMessage());
                    }

                    processing.set(false);
                } else {
                    // No commands, sleep briefly to avoid busy waiting
                    Thread.sleep(10);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Shutdown the command queue
     */
    public void shutdown() {
        clearQueue();
        executor.shutdown();
    }

    /**
     * Get status information for telemetry
     */
    public String getStatusInfo() {
        return String.format("Queue: %d commands, Processing: %s, Emergency: %s",
                getQueueSize(),
                isProcessing() ? "Yes" : "No",
                isEmergencyStopped() ? "STOP" : "OK");
    }
}