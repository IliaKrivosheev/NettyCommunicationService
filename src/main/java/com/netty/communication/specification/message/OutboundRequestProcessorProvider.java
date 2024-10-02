package com.netty.communication.specification.message;

import com.netty.communication.message.UserRequest;
import com.netty.communication.processor.OutboundRequestProcessor;

/**
 * Functional interface for providing {@link OutboundRequestProcessor} instances.
 * <p>
 * This interface is used to retrieve an outbound request processor based on the
 * class type of the {@link UserRequest}. Implementations should provide the logic
 * to map a specific request class to its corresponding processor.
 */
@FunctionalInterface
public interface OutboundRequestProcessorProvider {

    /**
     * Retrieves the {@link OutboundRequestProcessor} associated with the given {@link UserRequest} class.
     *
     * @param clazz the class of the user request
     * @return the outbound request processor corresponding to the provided class
     */
    OutboundRequestProcessor getOutboundRequestProcessor(Class<? extends UserRequest> clazz);
}
