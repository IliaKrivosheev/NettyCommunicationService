package com.netty.communication.message;

import lombok.Builder;

/**
 * Represents a user request to download a file.
 * This class contains information about the source and destination file paths.
 */
@Builder
public record UserFileDownloadRequest(String srcFile, String dstFile) implements UserRequest {
    public UserFileDownloadRequest {
    }
}
