package com.netty.communication.specification.message;

import com.netty.communication.message.MessageEncodable;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the mapping of {@link MessageEncodable} classes to their corresponding protocol IDs.
 * <p>
 * This class provides methods to register a class with a protocol ID and to retrieve the protocol ID
 * for a given class. It ensures that each class is registered before attempting to retrieve its ID.
 */
class ProtocolIdManager {
    private final Map<Class<? extends MessageEncodable>, Integer> classToIdMap;

    /**
     * Constructs a new instance of {@link ProtocolIdManager} with an empty mapping.
     */
    ProtocolIdManager() {
        classToIdMap = new HashMap<>();
    }

    /**
     * Registers the specified class with its corresponding protocol ID.
     *
     * @param clazz the class of the {@link MessageEncodable} to register
     * @param id the protocol ID associated with the specified class
     */
    void put(Class<? extends MessageEncodable> clazz, int id) {
        classToIdMap.put(clazz, id);
    }

    /**
     * Retrieves the protocol ID associated with the specified {@link MessageEncodable} class.
     *
     * @param clazz the class of the {@link MessageEncodable} for which to retrieve the ID
     * @return the protocol ID associated with the specified class
     * @throws IllegalStateException if the class is not registered in the manager
     */
    int get(Class<? extends MessageEncodable> clazz) {
        if (!classToIdMap.containsKey(clazz)) {
            throw new IllegalStateException("This class is not registered in EncodingIdProvider: " + clazz);
        }
        return classToIdMap.get(clazz);
    }
}
