package com.netty.communication.message;

/**
 * Represents a message that can be identified by a unique ID.
 * Implementing classes must provide their own ID retrieval logic.
 */
@FunctionalInterface
public interface MessageIdentifiable {

    /**
     * Retrieves the unique identifier for the message.
     *
     * @return the unique identifier as an integer
     */
    int getId();
}
