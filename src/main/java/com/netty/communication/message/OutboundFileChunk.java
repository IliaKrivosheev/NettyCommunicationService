package com.netty.communication.message;

import com.netty.communication.handler.outbound.EncodedBodyPiece;
import io.netty.buffer.ByteBuf;
import io.netty.channel.DefaultFileRegion;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Represents an outbound file chunk message in the protocol.
 * This message contains information about a file being sent, including its type, source path, destination path,
 * and the chunk's index and length.
 */
public class OutboundFileChunk implements ProtocolMessage {
    private final ChunkType type;
    private final String dstPath;
    private final String srcPath;
    private final long index;
    private final int length;

    public OutboundFileChunk(ChunkType type, String srcPath, String dstPath, long index, int length) {
        this.type = type;
        this.dstPath = dstPath;
        this.srcPath = srcPath;
        this.index = index;
        this.length = length;
    }

    @Override
    public List<EncodedBodyPiece> encode(ByteBuf buffer) {
        return List.of(encodeHeader(buffer), encodeFile());
    }

    private EncodedBodyPiece encodeHeader(ByteBuf buffer) {
        buffer.writeInt(type.value());
        buffer.writeInt(dstPath.length());
        buffer.writeCharSequence(dstPath, StandardCharsets.UTF_8);
        return new EncodedBodyPiece(buffer, buffer.readableBytes());
    }

    private EncodedBodyPiece encodeFile() {
        var fileRegion = new DefaultFileRegion(new File(srcPath), index, length);
        return new EncodedBodyPiece(fileRegion, length);
    }

    /**
     * Validates the outbound file chunk.
     * Checks if the source file exists and its size does not exceed Integer.MAX_VALUE.
     *
     * @throws IllegalArgumentException if the file is too large or does not exist.
     */
    @Override
    public void validate() throws Exception {
        File file = new File(srcPath);
        if (!file.exists() || file.length() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Too large or non-existent file: " + file.length() + " bytes");
        }
    }
}
