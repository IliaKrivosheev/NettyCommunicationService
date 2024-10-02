package com.netty.communication.processor;

import com.netty.communication.message.ProtocolMessage;
import com.netty.communication.message.UserRequest;

import java.util.List;

/**
 * A functional interface for processing outbound requests.
 * Implementations of this interface will define the logic for handling
 * user requests and generating corresponding protocol messages.
 */
@FunctionalInterface
public interface OutboundRequestProcessor {

    /**
     * Processes a user request and returns a list of protocol messages.
     *
     * @param message the user request to process
     * @return a list of protocol messages generated as a result of processing the input request
     * @throws Exception if an error occurs during processing
     */
    List<ProtocolMessage> process(UserRequest message) throws Exception;
}
