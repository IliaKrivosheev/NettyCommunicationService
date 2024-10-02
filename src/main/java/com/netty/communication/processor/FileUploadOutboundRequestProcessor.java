package com.netty.communication.processor;

import com.netty.communication.message.FileUploadRequest;
import com.netty.communication.message.ProtocolMessage;
import com.netty.communication.message.UserFileUploadRequest;
import com.netty.communication.message.UserRequest;
import lombok.Builder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Processor for handling outbound file upload requests.
 * This class processes user requests to upload files,
 * converting them into protocol messages.
 */
@Builder
public class FileUploadOutboundRequestProcessor implements OutboundRequestProcessor {

    private final int chunkSize;
    private final String rootPath;
    private final FileTransferProcessor fileTransferProcessor;

    /**
     * Processes a user file upload request and generates protocol messages.
     *
     * @param message the user request containing file upload details
     * @return a list of protocol messages representing the file upload process
     * @throws Exception if an error occurs during the processing of the file upload
     */
    @Override
    public List<ProtocolMessage> process(UserRequest message) throws Exception {
        var uploadRequest = (UserFileUploadRequest) message;

        var srcFilePath = Path.of(rootPath, uploadRequest.srcFile()).normalize().toString();
        var dstFilePath = uploadRequest.dstFile();

        var fileChunks = fileTransferProcessor.process(srcFilePath, dstFilePath, chunkSize);

        var tailHeader = FileUploadRequest.builder()
                .sourceFilePath(srcFilePath)
                .destFilePath(dstFilePath)
                .build();

        var messages = new ArrayList<ProtocolMessage>();
        messages.addAll(fileChunks);
        messages.add(tailHeader);
        return messages;
    }
}
