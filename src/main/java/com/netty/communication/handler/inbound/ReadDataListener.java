package com.netty.communication.handler.inbound;

import java.net.SocketAddress;

/**
 * A functional interface for listening to available read data from a remote socket address.
 * Implementations of this interface can define how to handle the incoming data.
 */
@FunctionalInterface
public interface ReadDataListener {
    /**
     * Called when data is available to read from the remote address.
     *
     * @param remoteAddress the socket address of the remote endpoint
     * @param data the data that has been read
     * @throws InterruptedException if the thread is interrupted while processing the data
     */
    void onReadAvailable(SocketAddress remoteAddress, Object data) throws InterruptedException;
}
