package com.netty.communication.processor;

/**
 * A functional interface for mapping request types to their corresponding
 * inbound request processors.
 */
@FunctionalInterface
public interface RequestProcessorMapper {

    /**
     * Retrieves the appropriate inbound request processor for the given request type.
     *
     * @param requestType the type of the request for which to retrieve the processor
     * @return the corresponding InboundRequestProcessor for the specified request type,
     *         or null if no processor is found
     */
    InboundRequestProcessor getProcessor(String requestType);
}
