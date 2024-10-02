package com.netty.communication.specification.message;

import com.netty.communication.message.UserRequest;
import com.netty.communication.processor.OutboundRequestProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the mapping of user request classes to their corresponding outbound request processors.
 * <p>
 * This class provides functionality to register and retrieve {@link OutboundRequestProcessor}
 * instances based on the type of {@link UserRequest} class.
 */
class OutboundRequestProcessorManager {
    private final Map<Class<? extends UserRequest>, OutboundRequestProcessor> classToProcessorMap;

    /**
     * Constructs a new {@code OutboundRequestProcessorManager} with an empty processor map.
     */
    OutboundRequestProcessorManager() {
        classToProcessorMap = new HashMap<>();
    }

    /**
     * Retrieves the {@link OutboundRequestProcessor} associated with the specified {@link UserRequest} class.
     *
     * @param clazz the class of the user request
     * @return the outbound request processor corresponding to the given request class
     * @throws IllegalStateException if the specified class is not registered
     */
    OutboundRequestProcessor get(Class<? extends UserRequest> clazz) {
        if (!classToProcessorMap.containsKey(clazz)) {
            throw new IllegalStateException("This class is not registered in MessageSpecProvider: " + clazz);
        }
        return classToProcessorMap.get(clazz);
    }

    /**
     * Registers a {@link OutboundRequestProcessor} for the specified {@link UserRequest} class.
     *
     * @param clazz     the class of the user request
     * @param processor the processor to handle outbound requests for the given request class
     */
    void put(Class<? extends UserRequest> clazz, OutboundRequestProcessor processor) {
        classToProcessorMap.put(clazz, processor);
    }
}
