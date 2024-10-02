package com.netty.communication.processor;

import com.netty.communication.message.FileDownloadRequest;
import com.netty.communication.message.ProtocolMessage;
import lombok.Builder;

import java.nio.file.Path;
import java.util.List;

/**
 * Processes inbound file download requests.
 * This processor handles a file download request by constructing the source
 * file path from the root path and the request details, and then processes
 * the file transfer.
 */
@Builder
public class FileDownloadInboundRequestProcessor implements InboundRequestProcessor {
    private final int chunkSize;
    private final String rootPath;
    private final FileTransferProcessor fileTransferProcessor;

    /**
     * Processes a file download request by extracting the source and
     * destination file paths, then delegating the file transfer to
     * the specified processor.
     *
     * @param message the protocol message representing the file download request
     * @return a list of protocol messages generated during the file transfer process
     * @throws Exception if an error occurs during file processing or transfer
     */
    @Override
    public List<ProtocolMessage> process(ProtocolMessage message) throws Exception {
        var request = (FileDownloadRequest) message;
        var srcFilePath = Path.of(rootPath, request.getSourceFilePath()).normalize().toString();
        var dstFilePath = request.getDestFilePath();

        return fileTransferProcessor.process(srcFilePath, dstFilePath, chunkSize);
    }
}
