package com.netty.communication.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Data Transfer Object (DTO) for specifying the details of a file upload operation.
 * Contains information about the local source file and the destination on the remote server.
 */
@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public class FileUploadDto {
    private final LocalFileDto source;
    private final RemoteFileDto destination;
}
