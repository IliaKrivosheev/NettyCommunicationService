package com.netty.communication.message;

/**
 * Represents a protocol message that can be encoded and validated.
 * This interface extends {@link MessageEncodable} for encoding messages to a ByteBuf
 * and {@link MessageValidatable} for validating the messages before sending them.
 */
@FunctionalInterface
public interface ProtocolMessage extends MessageEncodable, MessageValidatable {
    // No additional methods defined; serves as a marker interface for protocol messages.
}
