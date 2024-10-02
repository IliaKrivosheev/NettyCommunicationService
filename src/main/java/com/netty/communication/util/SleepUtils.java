package com.netty.communication.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class for sleep operations.
 * <p>
 * This class provides methods to pause the execution of the current thread for a specified duration.
 */
@UtilityClass
public class SleepUtils {

    /**
     * Pauses the current thread for the specified duration.
     *
     * @param millis the duration to sleep in milliseconds
     * @throws RuntimeException if the thread is interrupted while sleeping
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted during sleep", e);
        }
    }
}
