package com.netty.communication.util;

import io.netty.buffer.ByteBuf;
import lombok.experimental.UtilityClass;

import java.io.*;

/**
 * Utility class for file operations with Netty's ByteBuf.
 * <p>
 * This class provides methods for reading files and writing their contents
 * into a Netty {@link ByteBuf} instance.
 */
@UtilityClass
public class NettyFileUtils {

    /**
     * Reads the entire contents of a file and writes it to the given {@link ByteBuf}.
     *
     * @param srcFile   the source file to read from
     * @param dstBuffer the destination ByteBuf where the file content will be written
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static void readAllBytes(File srcFile, ByteBuf dstBuffer) throws IOException {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(srcFile))) {
            dstBuffer.writeBytes(inputStream, (int) srcFile.length());
        }
    }

    /**
     * Reads a specific range of bytes from a file and writes them to the given {@link ByteBuf}.
     *
     * @param srcFile the source file to read from
     * @param start   the starting position (offset) in the file to begin reading
     * @param length  the number of bytes to read from the starting position
     * @param dstBuffer the destination ByteBuf where the read bytes will be written
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static void readRandomAccess(File srcFile, long start, int length, ByteBuf dstBuffer) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(srcFile, "r")) {
            raf.seek(start);
            try (InputStream inputStream = new BufferedInputStream(new FileInputStream(raf.getFD()))) {
                dstBuffer.writeBytes(inputStream, length);
            }
        }
    }
}
