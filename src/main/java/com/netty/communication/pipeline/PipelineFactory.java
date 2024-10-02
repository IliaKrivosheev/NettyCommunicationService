package com.netty.communication.pipeline;

import java.util.List;

/**
 * Functional interface for creating a list of {@link HandlerFactory} instances.
 */
@FunctionalInterface
public interface PipelineFactory {

    /**
     * Retrieves a list of {@link HandlerFactory} instances.
     *
     * @return a list of {@link HandlerFactory} instances used in the pipeline
     */
    List<HandlerFactory> get();
}
