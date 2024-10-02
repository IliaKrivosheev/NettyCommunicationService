package com.netty.communication.tcp.server;

import org.springframework.lang.Nullable;

import java.net.SocketAddress;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Interface representing the request handling capabilities of a TCP server.
 * <p>
 * This interface defines methods for starting the server, sending messages to
 * connected clients, and reading data from clients. It provides functionality to
 * check the active status of client connections.
 */
public interface TcpServerRequest {

    /**
     * Starts the TCP server, binding it to the specified port.
     *
     * @param bindPort the port number to bind the server to
     * @return a Future representing the completion of the server startup
     * @throws InterruptedException if the thread is interrupted while waiting to start
     */
    Future<Void> start(int bindPort) throws InterruptedException;

    /**
     * Sends a message to all connected clients.
     *
     * @param message the message to be sent
     * @return a Future representing the completion of the send operation
     */
    Future<Void> sendAll(Object message);

    /**
     * Reads data from a specified client address with a timeout.
     *
     * @param clientAddress the address of the client to read from
     * @param timeout       the maximum time to wait for data
     * @param unit          the time unit of the timeout
     * @return the data read from the client, or null if no data was received within the timeout
     * @throws InterruptedException if the thread is interrupted while waiting for data
     */
    @Nullable
    Object read(SocketAddress clientAddress, int timeout, TimeUnit unit) throws InterruptedException;

    /**
     * Synchronously reads data from a specified client address.
     *
     * @param clientAddress the address of the client to read from
     * @return the data read from the client
     * @throws InterruptedException if the thread is interrupted while waiting for data
     */
    Object readSync(SocketAddress clientAddress) throws InterruptedException;

    /**
     * Checks whether the specified client connection is active.
     *
     * @param clientAddress the address of the client to check
     * @return true if the client connection is active, false otherwise
     */
    boolean isActive(SocketAddress clientAddress);
}
