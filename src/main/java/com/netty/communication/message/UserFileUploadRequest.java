package com.netty.communication.message;

import lombok.Builder;

/**
 * Represents a user request to upload a file.
 * This class contains information about the source and destination file paths.
 */
@Builder
public record UserFileUploadRequest(String srcFile, String dstFile) implements UserRequest {
    public UserFileUploadRequest {
    }
}
