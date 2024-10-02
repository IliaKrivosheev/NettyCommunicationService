package com.netty.communication.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

/**
 * A utility class for advanced file operations.
 * <p>
 * This class provides methods for creating files, comparing file contents,
 * deleting files, and obtaining file metadata. All methods are static and
 * handle exceptions by throwing a runtime exception.
 */
@UtilityClass
public final class AdvancedFileUtils {

    /**
     * Creates a new text file with the specified content.
     *
     * @param path    the path where the file will be created
     * @param content the content to write into the file
     * @return the created File object
     * @throws RuntimeException if an I/O error occurs
     */
    public static File newTextFile(String path, String content) {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(content);
            return new File(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create text file: " + path, e);
        }
    }

    /**
     * Creates a new file with random byte contents of the specified size.
     *
     * @param path the path where the file will be created
     * @param size the size of the random content in bytes
     * @return the created File object
     * @throws RuntimeException if an I/O error occurs
     */
    public static File newRandomContentsFile(String path, int size) {
        byte[] randomContents = new byte[size];
        Random random = new Random();
        random.nextBytes(randomContents);

        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path))) {
            outputStream.write(randomContents);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create random contents file: " + path, e);
        }

        return new File(path);
    }

    /**
     * Compares the contents of two files for equality.
     *
     * @param filePath1 the path of the first file
     * @param filePath2 the path of the second file
     * @return true if the contents are equal, false otherwise
     * @throws RuntimeException if an I/O error occurs
     */
    public static boolean contentEquals(String filePath1, String filePath2) {
        try {
            File file1 = new File(filePath1);
            File file2 = new File(filePath2);
            return FileUtils.contentEquals(file1, file2);
        } catch (IOException e) {
            throw new RuntimeException("Failed to compare file contents: " + filePath1 + " and " + filePath2, e);
        }
    }

    /**
     * Compares the lengths of two files for equality.
     *
     * @param filePath1 the path of the first file
     * @param filePath2 the path of the second file
     * @return true if the lengths are equal, false otherwise
     */
    public static boolean lengthEquals(String filePath1, String filePath2) {
        File file1 = new File(filePath1);
        File file2 = new File(filePath2);
        return FileUtils.sizeOf(file1) == FileUtils.sizeOf(file2);
    }

    /**
     * Deletes the file at the specified path if it exists.
     *
     * @param path the path of the file to delete
     * @return true if the file was deleted, false if it did not exist
     * @throws RuntimeException if an I/O error occurs
     */
    public static boolean deleteIfExists(String path) {
        try {
            return Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + path, e);
        }
    }

    /**
     * Retrieves the last modified time of the file at the specified path.
     *
     * @param path the path of the file
     * @return the last modified time as a LocalDateTime object
     * @throws RuntimeException if an I/O error occurs
     */
    public static LocalDateTime getLastModifiedTime(String path) {
        try {
            return Files.getLastModifiedTime(Path.of(path))
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get last modified time for file: " + path, e);
        }
    }

    /**
     * Creates directories for the specified path if they do not already exist.
     *
     * @param targetPath the path for which to create directories
     * @throws RuntimeException if an I/O error occurs
     */
    public static void makeDirectoriesIfNotExist(String targetPath) {
        var lastDirSeparatorIndex = targetPath.lastIndexOf(File.separator);
        if (lastDirSeparatorIndex == -1) {
            return;
        }
        var directoryPath = targetPath.substring(0, lastDirSeparatorIndex);
        if (!directoryPath.isEmpty()) {
            try {
                FileUtils.forceMkdir(new File(directoryPath));
            } catch (IOException e) {
                throw new RuntimeException("Failed to create directories for path: " + targetPath, e);
            }
        }
    }
}
