package com.netty.communication.message;

import com.netty.communication.handler.outbound.EncodedBodyPiece;
import io.netty.buffer.ByteBuf;
import lombok.Builder;

import java.util.Collections;
import java.util.List;

/**
 * Represents a protocol message indicating that a chunk transfer was successful.
 * This message can be encoded for transmission over a network and can be decoded
 * from a received message.
 */
@Builder
public class ChunkTransferOk implements ProtocolMessage {

    /**
     * Decodes a ChunkTransferOk message from the provided ByteBuf.
     *
     * @param message the ByteBuf containing the encoded message
     * @return a new instance of ChunkTransferOk
     */
    public static ChunkTransferOk decode(ByteBuf message) {
        return builder().build();
    }

    /**
     * Encodes this ChunkTransferOk message into a list of EncodedBodyPiece objects.
     * Since this message has no body, it returns an empty EncodedBodyPiece.
     *
     * @param buffer the ByteBuf to encode the message into
     * @return a list containing an EncodedBodyPiece representing the empty body
     */
    @Override
    public List<EncodedBodyPiece> encode(ByteBuf buffer) {
        return Collections.singletonList(new EncodedBodyPiece(buffer, buffer.readableBytes()));
    }
}
