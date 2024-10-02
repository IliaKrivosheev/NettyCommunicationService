package com.netty.communication.specification.message;

import com.netty.communication.message.DecodeFunction;

/**
 * Provides a mechanism to retrieve decode functions based on message IDs.
 * This functional interface defines a single method to get the decoder associated with a specific ID.
 */
@FunctionalInterface
public interface MessageDecoderProvider {

    /**
     * Retrieves the decode function for the specified message ID.
     *
     * @param id the ID of the message for which the decode function is needed
     * @return the corresponding DecodeFunction instance
     */
    DecodeFunction getDecoder(int id);
}
