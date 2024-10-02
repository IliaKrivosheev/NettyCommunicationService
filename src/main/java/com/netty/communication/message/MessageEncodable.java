package com.netty.communication.message;

import com.netty.communication.handler.outbound.EncodedBodyPiece;
import io.netty.buffer.ByteBuf;

import java.util.List;

/**
 * Represents a message that can be encoded into a {@link ByteBuf}.
 * Implementing classes must provide their own encoding logic.
 */
@FunctionalInterface
public interface MessageEncodable {

    /**
     * Encodes the message into the given {@link ByteBuf}.
     *
     * @param buffer the {@link ByteBuf} to write the encoded message into
     * @return a list of {@link EncodedBodyPiece} representing the encoded message
     */
    List<EncodedBodyPiece> encode(ByteBuf buffer);
}
