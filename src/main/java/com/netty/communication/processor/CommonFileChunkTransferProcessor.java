package com.netty.communication.processor;

import com.netty.communication.exception.NotFileException;
import com.netty.communication.message.ChunkType;
import com.netty.communication.message.OutboundFileChunk;
import com.netty.communication.message.ProtocolMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Processor for handling file transfers in chunks.
 * This class validates the source file, divides it into
 * manageable chunks, and generates corresponding
 * protocol messages for the transfer process.
 */
public class CommonFileChunkTransferProcessor implements FileTransferProcessor {

    /**
     * Validates the specified source file path.
     *
     * @param srcPath the path of the source file to validate
     * @throws NotFileException if the path points to a directory
     * @throws FileNotFoundException if the file does not exist
     */
    private static void validate(String srcPath) throws Exception {
        Path path = Path.of(srcPath);
        if (Files.isDirectory(path)) {
            throw new NotFileException(srcPath);
        }
        if (!Files.exists(path)) {
            throw new FileNotFoundException(srcPath);
        }
    }

    /**
     * Processes the file transfer by generating protocol messages
     * for each chunk of the file.
     *
     * @param srcPath the path of the source file
     * @param dstPath the destination path for the file transfer
     * @param chunkSize the size of each chunk in bytes
     * @return a list of {@link ProtocolMessage} representing the file chunks
     * @throws Exception if an error occurs during file processing
     */
    @Override
    public List<ProtocolMessage> process(String srcPath, String dstPath, int chunkSize) throws Exception {
        List<ProtocolMessage> messages = new ArrayList<>();

        validate(srcPath);

        long start = 0;
        var startChunk = new OutboundFileChunk(ChunkType.START_OF_FILE, srcPath, dstPath, start, 0);
        messages.add(startChunk);

        File srcFile = new File(srcPath);
        long remainBytes = srcFile.length();
        while (remainBytes > 0) {
            int readBytes = (int) Math.min(remainBytes, chunkSize);
            var chunk = new OutboundFileChunk(ChunkType.MIDDLE_OF_FILE, srcPath, dstPath, start, readBytes);
            messages.add(chunk);
            remainBytes -= readBytes;
            start += readBytes;
        }

        var lastChunk = new OutboundFileChunk(ChunkType.END_OF_FILE, srcPath, dstPath, start, 0);
        messages.add(lastChunk);

        return messages;
    }
}
