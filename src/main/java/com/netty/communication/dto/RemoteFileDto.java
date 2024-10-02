package com.netty.communication.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Data Transfer Object (DTO) representing a file located on a remote server.
 * Contains information about the server's IP address, port, and the file's location.
 */
@Getter
@Accessors(fluent = true)
@ToString
@RequiredArgsConstructor
public class RemoteFileDto {
    private final String ip;
    private final int port;
    private final String file;
}
