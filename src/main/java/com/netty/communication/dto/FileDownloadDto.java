package com.netty.communication.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Data Transfer Object (DTO) for specifying the details of a file download operation.
 * Contains information about the source file on the remote server and the destination
 * file on the local system.
 */
@RequiredArgsConstructor
@Getter
@ToString
@Accessors(fluent = true)
public class FileDownloadDto {
    private final RemoteFileDto source;
    private final LocalFileDto destination;
}
