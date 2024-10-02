package com.netty.communication.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Data Transfer Object (DTO) for specifying the details of a file transfer operation.
 * This object contains information about the local file, as well as the remote server's IP address, port, and file.
 */
@Getter
@RequiredArgsConstructor
@ToString
public class FileTransferDto {
    private final String localFile;
    private final String remoteIp;
    private final int remotePort;
    private final String remoteFile;
}
