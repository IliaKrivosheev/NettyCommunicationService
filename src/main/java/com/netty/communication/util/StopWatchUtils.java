package com.netty.communication.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StopWatch;

/**
 * Utility class for managing StopWatch instances.
 * <p>
 * This class provides static methods to create and manipulate StopWatch objects, allowing
 * for easy timing and performance measurement of code execution.
 */
@UtilityClass
public class StopWatchUtils {

    /**
     * Starts a new StopWatch instance.
     *
     * @return a new {@link StopWatch} that is already started
     */
    public static StopWatch start() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        return stopWatch;
    }

    /**
     * Stops the given StopWatch.
     *
     * @param stopWatch the StopWatch to stop
     * @throws IllegalArgumentException if the stopWatch is not started
     */
    public static void stop(StopWatch stopWatch) {
        if (!stopWatch.isRunning()) {
            throw new IllegalArgumentException("StopWatch must be started before stopping.");
        }
        stopWatch.stop();
    }

    /**
     * Stops the given StopWatch and prints the total time in seconds.
     *
     * @param stopWatch the StopWatch to stop and measure
     * @param tag       a label to describe the timing output
     * @throws IllegalArgumentException if the stopWatch is not started
     */
    public static void stopAndPrintSeconds(StopWatch stopWatch, String tag) {
        stop(stopWatch); // Reuse the stop method for consistency
        System.out.printf("%s: %,f seconds%n", tag, stopWatch.getTotalTimeSeconds());
    }
}
