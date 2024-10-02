package com.netty.communication.specification.message;

import com.netty.communication.message.ProtocolMessage;
import com.netty.communication.processor.InboundRequestProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the mapping between protocol message classes and their corresponding
 * inbound request processors.
 * This class allows for the registration and retrieval of processors based
 * on the protocol message class.
 */
class InboundRequestProcessorManager {
    private final Map<Class<? extends ProtocolMessage>, InboundRequestProcessor> classToProcessorMap;

    InboundRequestProcessorManager() {
        classToProcessorMap = new HashMap<>();
    }

    /**
     * Retrieves the inbound request processor associated with the specified
     * protocol message class.
     *
     * @param clazz the class of the protocol message
     * @return the corresponding InboundRequestProcessor
     * @throws IllegalStateException if the class is not registered in the manager
     */
    InboundRequestProcessor get(Class<? extends ProtocolMessage> clazz) {
        if (!classToProcessorMap.containsKey(clazz)) {
            throw new IllegalStateException("This class is not registered in MessageSpecProvider: " + clazz);
        }
        return classToProcessorMap.get(clazz);
    }

    /**
     * Registers a new inbound request processor for the specified protocol message class.
     *
     * @param clazz the class of the protocol message
     * @param processor the inbound request processor to be associated with the class
     */
    void put(Class<? extends ProtocolMessage> clazz, InboundRequestProcessor processor) {
        classToProcessorMap.put(clazz, processor);
    }
}
