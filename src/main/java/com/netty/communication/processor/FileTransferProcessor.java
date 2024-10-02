package com.netty.communication.processor;

import com.netty.communication.message.ProtocolMessage;

import java.util.List;

/**
 * Interface representing a file transfer processor.
 * Implementations of this interface handle the processing of file transfer requests,
 * converting them into protocol messages.
 */
@FunctionalInterface
public interface FileTransferProcessor {

    /**
     * Processes a file transfer request.
     *
     * @param srcPath   the source path of the file to be transferred
     * @param dstPath   the destination path where the file should be transferred
     * @param chunkSize the size of the chunks in which the file will be transferred
     * @return a list of protocol messages representing the file transfer process
     * @throws Exception if an error occurs during the processing of the file transfer
     */
    List<ProtocolMessage> process(String srcPath, String dstPath, int chunkSize) throws Exception;
}
