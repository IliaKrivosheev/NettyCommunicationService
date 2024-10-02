package com.netty.communication.handler.inbound;

import com.netty.communication.message.ProtocolMessage;
import io.netty.buffer.ByteBufAllocator;

import java.util.List;

/**
 * A functional interface representing a response function that processes a
 * {@link ProtocolMessage} request and returns a list of response messages.
 * <p>
 * Implementations of this interface are expected to handle the logic for generating
 * responses based on the incoming request message.
 * </p>
 */
@FunctionalInterface
public interface ResponseFunction {
    /**
     * Processes a request and generates a list of response messages.
     *
     * @param request  the incoming {@link ProtocolMessage} request
     * @param allocator the {@link ByteBufAllocator} used for allocating buffers
     * @return a list of response {@link ProtocolMessage} instances
     * @throws Exception if an error occurs during processing
     */
    List<ProtocolMessage> response(ProtocolMessage request, ByteBufAllocator allocator) throws Exception;
}
