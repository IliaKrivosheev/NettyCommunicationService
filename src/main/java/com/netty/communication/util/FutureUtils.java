package com.netty.communication.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Utility class for handling {@link Future} objects.
 * <p>
 * This class provides utility methods for working with Future instances,
 * primarily focused on retrieving their results in a safe manner.
 */
@UtilityClass
public class FutureUtils {

    /**
     * Retrieves the result of the given {@link Future}.
     *
     * @param future the Future to retrieve the result from
     * @throws RuntimeException if the operation is interrupted or fails to execute
     */
    public static void get(Future<?> future) {
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
