package com.netty.communication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Data Transfer Object (DTO) representing a file located on the local system.
 * This DTO is typically used to specify the source file for operations like upload or transfer.
 */
@Getter
@Accessors(fluent = true)
@ToString
public class LocalFileDto {
    private final String file;

    /**
     * Constructs a {@link LocalFileDto} with the specified file path.
     *
     * @param file the path or name of the local file
     */
    public LocalFileDto(@JsonProperty("file") String file) {
        this.file = file;
    }
}
