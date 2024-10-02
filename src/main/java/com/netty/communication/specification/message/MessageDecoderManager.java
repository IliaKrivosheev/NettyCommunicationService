package com.netty.communication.specification.message;

import com.netty.communication.message.DecodeFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the association between message IDs and their corresponding decode functions.
 * Provides methods to register and retrieve decode functions based on their message IDs.
 */
class MessageDecoderManager {
    private final Map<Integer, DecodeFunction> idToDecoderMap;

    MessageDecoderManager() {
        idToDecoderMap = new HashMap<>();
    }

    /**
     * Retrieves the decode function associated with the specified message ID.
     *
     * @param id the ID of the message for which the decode function is needed
     * @return the corresponding DecodeFunction instance
     * @throws IllegalStateException if the specified ID is not registered
     */
    DecodeFunction get(int id) {
        if (!idToDecoderMap.containsKey(id)) {
            throw new IllegalStateException("This ID is not registered in MessageDecoderProvider: " + id);
        }
        return idToDecoderMap.get(id);
    }

    /**
     * Registers a decode function for the specified message ID.
     *
     * @param id the ID of the message to register the decode function for
     * @param decodeFunction the decode function to register
     */
    void put(int id, DecodeFunction decodeFunction) {
        idToDecoderMap.put(id, decodeFunction);
    }
}
