package com.netty.communication.specification.message;

import com.netty.communication.message.MessageEncodable;

/**
 * Provides a method to retrieve the protocol ID associated with a given {@link MessageEncodable} class.
 * <p>
 * This functional interface allows for different implementations that define how protocol IDs are managed
 * and retrieved based on message classes.
 */
@FunctionalInterface
public interface ProtocolIdProvider {

    /**
     * Retrieves the protocol ID associated with the specified {@link MessageEncodable} class.
     *
     * @param clazz the class of the {@link MessageEncodable} for which to retrieve the protocol ID
     * @return the protocol ID associated with the specified class
     * @throws IllegalStateException if the class is not registered in the provider
     */
    int getProtocolId(Class<? extends MessageEncodable> clazz);
}
