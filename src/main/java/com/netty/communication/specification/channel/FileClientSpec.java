package com.netty.communication.specification.channel;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Specifications for the file client configuration.
 * This class holds various settings related to file client operations.
 */
@Getter
@Accessors(fluent = true)
@Component
public class FileClientSpec {

    @Value("${file.client.root}")
    private String rootPath;

    private final int idleDetectionSeconds = 3;

    private final int chunkSize = 1024 * 1024 * 5;

    private final int nChannelIoMaxThread = 0;

    private final int nFileStoreMaxThread = 0;
}
