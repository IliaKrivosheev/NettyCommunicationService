package com.netty.communication.service;

import java.util.concurrent.ExecutionException;

/**
 * Functional interface representing a file server that can be started on a specified port.
 */
@FunctionalInterface
public interface FilerServer {

    /**
     * Starts the file server and binds it to the specified port.
     *
     * @param bindPort the port number to which the server will be bound
     * @throws InterruptedException if the thread executing the start operation is interrupted
     * @throws ExecutionException if an error occurs during the server startup process
     */
    void start(int bindPort) throws InterruptedException, ExecutionException;
}
