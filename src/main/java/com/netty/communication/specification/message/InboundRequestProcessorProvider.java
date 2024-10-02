package com.netty.communication.specification.message;

import com.netty.communication.message.ProtocolMessage;
import com.netty.communication.processor.InboundRequestProcessor;

/**
 * A functional interface that provides a method to retrieve an inbound request processor
 * based on the class of a protocol message.
 */
@FunctionalInterface
public interface InboundRequestProcessorProvider {

    /**
     * Retrieves the inbound request processor associated with the specified protocol message class.
     *
     * @param clazz the class of the protocol message for which the processor is needed
     * @return the corresponding InboundRequestProcessor instance
     */
    InboundRequestProcessor getInboundRequestProcessor(Class<? extends ProtocolMessage> clazz);
}
