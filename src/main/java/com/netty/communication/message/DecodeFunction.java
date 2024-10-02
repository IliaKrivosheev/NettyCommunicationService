package com.netty.communication.message;

import io.netty.buffer.ByteBuf;

/**
 * A functional interface representing a decoding function that converts a
 * {@link ByteBuf} message into a {@link ProtocolMessage}.
 */
@FunctionalInterface
public interface DecodeFunction {

    /**
     * Applies this decoding function to the given message.
     *
     * @param message the {@link ByteBuf} message to be decoded
     * @return the decoded {@link ProtocolMessage}
     */
    ProtocolMessage apply(ByteBuf message);
}
