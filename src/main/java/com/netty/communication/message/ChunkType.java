package com.netty.communication.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Represents the type of a chunk in a file transfer protocol.
 * This enum defines the possible chunk types and their corresponding integer values.
 */
@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public enum ChunkType {
    START_OF_FILE(1),
    MIDDLE_OF_FILE(2),
    END_OF_FILE(3);

    private final int value;

    /**
     * Returns the ChunkType corresponding to the specified integer value.
     *
     * @param value the integer value representing a chunk type
     * @return the corresponding ChunkType
     * @throws IllegalArgumentException if the value does not correspond to a known chunk type
     */
    public static ChunkType of(int value) {
        return switch (value) {
            case 1 -> START_OF_FILE;
            case 2 -> MIDDLE_OF_FILE;
            case 3 -> END_OF_FILE;
            default -> throw new IllegalArgumentException("Unknown chunk type: " + value);
        };
    }
}
