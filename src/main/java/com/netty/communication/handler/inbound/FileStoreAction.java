package com.netty.communication.handler.inbound;

import com.netty.communication.message.ChunkType;
import com.netty.communication.message.InboundFileChunk;
import com.netty.communication.util.AdvancedFileUtils;
import io.netty.buffer.ByteBuf;
import lombok.experimental.UtilityClass;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utility class for storing file chunks to a specified target path.
 */
@UtilityClass
public class FileStoreAction {

    /**
     * Stores a given file chunk to the specified target path.
     * If the chunk is of type START_OF_FILE, any existing file at the target path will be deleted,
     * and necessary directories will be created.
     *
     * @param chunk     the inbound file chunk to be stored
     * @param targetPath the path where the chunk should be stored
     * @throws IOException if an I/O error occurs while storing the chunk
     */
    public static void store(InboundFileChunk chunk, String targetPath) throws IOException {
        if (chunk.type() == ChunkType.START_OF_FILE) {
            AdvancedFileUtils.deleteIfExists(targetPath);
            AdvancedFileUtils.makeDirectoriesIfNotExist(targetPath);
        }

        final ByteBuf chunkContents = chunk.contents();
        final int nBytesToStore = chunkContents.readableBytes();

        if (nBytesToStore > 0) {
            try (var fileOutputStream = new FileOutputStream(targetPath, true);
                 var bufferedOutputStream = new BufferedOutputStream(fileOutputStream, nBytesToStore)) {
                chunkContents.readBytes(bufferedOutputStream, nBytesToStore);
            }
        }
    }
}
