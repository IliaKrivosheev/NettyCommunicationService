package com.netty.communication.processor;

import com.netty.communication.message.FileDownloadRequest;
import com.netty.communication.message.ProtocolMessage;
import com.netty.communication.message.UserFileDownloadRequest;
import com.netty.communication.message.UserRequest;
import lombok.Builder;

import java.util.List;

/**
 * Processes outbound file download requests.
 * This processor converts a user file download request into a protocol message
 * for file download.
 */
@Builder
public class FileDownloadOutboundRequestProcessor implements OutboundRequestProcessor {

    /**
     * Processes a user file download request and creates a corresponding
     * file download request protocol message.
     *
     * @param message the user request representing the file download request
     * @return a list containing the created file download request protocol message
     */
    @Override
    public List<ProtocolMessage> process(UserRequest message) {
        var userRequest = (UserFileDownloadRequest) message;

        return List.of(FileDownloadRequest.builder()
                .sourceFilePath(userRequest.srcFile())
                .destFilePath(userRequest.dstFile())
                .build());
    }
}
