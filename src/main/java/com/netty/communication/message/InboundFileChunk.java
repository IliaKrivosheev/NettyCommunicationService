package com.netty.communication.message;

import com.netty.communication.handler.outbound.EncodedBodyPiece;
import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCounted;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Represents a chunk of an inbound file transfer.
 * This message contains metadata about the chunk, such as its type,
 * storage path, and the contents of the chunk.
 */
@Builder
@Getter
@Accessors(fluent = true)
public class InboundFileChunk implements ProtocolMessage, ReferenceCounted {

    private final ChunkType type;
    private final String storePath;
    private final ByteBuf contents;

    /**
     * Decodes a {@link ByteBuf} message into an {@link InboundFileChunk}.
     *
     * @param message the {@link ByteBuf} containing the encoded chunk
     * @return the decoded {@link InboundFileChunk}
     */
    public static InboundFileChunk decode(ByteBuf message) {
        int chunkType = message.readInt();
        String storePath = message.readCharSequence(message.readInt(), StandardCharsets.UTF_8).toString();
        ByteBuf fileContents = message.readRetainedSlice(message.readableBytes());
        return builder()
                .type(ChunkType.of(chunkType))
                .storePath(storePath)
                .contents(fileContents)
                .build();
    }

    /**
     * Encodes the {@link InboundFileChunk} into a {@link ByteBuf}.
     *
     * @param buffer the {@link ByteBuf} to write the encoded chunk into
     * @return a list of {@link EncodedBodyPiece} representing the encoded message
     */
    @Override
    public List<EncodedBodyPiece> encode(ByteBuf buffer) {
        buffer.writeInt(type.value());
        buffer.writeInt(storePath.length());
        buffer.writeCharSequence(storePath, StandardCharsets.UTF_8);
        buffer.writeBytes(contents);
        var encodedMessage = new EncodedBodyPiece(buffer, buffer.readableBytes());
        contents.release();
        return List.of(encodedMessage);
    }

    // Reference counting methods to manage the lifecycle of ByteBuf contents.

    @Override
    public int refCnt() {
        return contents.refCnt();
    }

    @Override
    public ReferenceCounted retain() {
        contents.retain();
        return this;
    }

    @Override
    public ReferenceCounted retain(int increment) {
        contents.retain(increment);
        return this;
    }

    @Override
    public ReferenceCounted touch() {
        return this;
    }

    @Override
    public ReferenceCounted touch(Object hint) {
        return this;
    }

    @Override
    public boolean release() {
        return contents.release();
    }

    @Override
    public boolean release(int decrement) {
        return contents.release(decrement);
    }
}
