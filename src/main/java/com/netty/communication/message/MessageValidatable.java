package com.netty.communication.message;

/**
 * An interface for messages that require validation.
 * Implementing classes can provide their own validation logic.
 */
public interface MessageValidatable {

    /**
     * Validates the message.
     * This default implementation does nothing,
     * allowing implementing classes to override it with specific validation logic.
     *
     * @throws Exception if the validation fails
     */
    default void validate() throws Exception {
        // Empty implementation
    }
}
