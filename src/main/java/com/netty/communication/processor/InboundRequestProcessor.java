package com.netty.communication.processor;

import com.netty.communication.message.ProtocolMessage;

import java.util.List;

/**
 * A functional interface for processing inbound requests.
 * Implementations of this interface will define the logic for handling
 * protocol messages received from clients.
 */
@FunctionalInterface
public interface InboundRequestProcessor {

    /**
     * Processes an inbound protocol message and returns a list of response messages.
     *
     * @param message the protocol message to process
     * @return a list of protocol messages generated as a result of processing the input message
     * @throws Exception if an error occurs during processing
     */
    List<ProtocolMessage> process(ProtocolMessage message) throws Exception;
}
