package com.netty.communication.processor;

import com.netty.communication.message.ProtocolMessage;

import java.util.Collections;
import java.util.List;

/**
 * A processor that handles inbound requests with empty bodies.
 * This processor returns an empty list of protocol messages
 * when processing any incoming message.
 */
public final class EmptyBodyRetrieveProcessor implements InboundRequestProcessor {

    /** Singleton instance of the processor. */
    public static final EmptyBodyRetrieveProcessor INSTANCE = new EmptyBodyRetrieveProcessor();

    /** Private constructor to prevent instantiation. */
    private EmptyBodyRetrieveProcessor() {}

    /**
     * Processes the given protocol message and returns an empty list.
     *
     * @param message the protocol message to process
     * @return an empty list of protocol messages
     */
    @Override
    public List<ProtocolMessage> process(ProtocolMessage message) {
        return Collections.emptyList();
    }
}
