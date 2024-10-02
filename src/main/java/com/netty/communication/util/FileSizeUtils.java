package com.netty.communication.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class for file size conversions.
 * <p>
 * This class provides constants for common file size units (KB, MB, GB)
 * and a method to convert megabytes to bytes.
 */
@UtilityClass
public class FileSizeUtils {
    public static final int KB = 1024;
    public static final int MB = KB * KB;
    public static final int GB = KB * KB * KB;

    /**
     * Converts the specified size in megabytes to bytes.
     *
     * @param mb the size in megabytes
     * @return the equivalent size in bytes
     */
    public static int megaToByte(int mb) {
        return mb * KB * KB;
    }
}
