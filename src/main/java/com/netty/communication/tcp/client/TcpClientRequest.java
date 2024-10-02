package com.netty.communication.tcp.client;

import io.netty.channel.ChannelFuture;
import org.springframework.lang.Nullable;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Interface representing the request functionalities of a TCP client.
 * <p>
 * This interface defines methods for connecting, disconnecting, sending data,
 * reading data, and checking the client's status.
 */
public interface TcpClientRequest {

    /**
     * Establishes a connection to a specified IP address and port.
     *
     * @param ip   the IP address of the server to connect to
     * @param port the port number of the server to connect to
     * @return a {@link ChannelFuture} representing the connection result
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    ChannelFuture connect(String ip, int port) throws InterruptedException;

    /**
     * Disconnects from the server.
     *
     * @return a {@link ChannelFuture} representing the disconnection result
     */
    ChannelFuture disconnect();

    /**
     * Sends data to the connected server.
     *
     * @param data the data to be sent
     * @return a {@link ChannelFuture} representing the result of the send operation
     */
    ChannelFuture send(Object data);

    /**
     * Reads data from the server synchronously.
     *
     * @return the data received from the server
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    Object readSync() throws InterruptedException;

    /**
     * Reads data from the server with a specified timeout.
     *
     * @param timeout the timeout duration
     * @param unit    the time unit of the timeout duration
     * @return the data received from the server, or null if no data is received within the timeout
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    @Nullable
    Object read(int timeout, TimeUnit unit) throws InterruptedException;

    /**
     * Retrieves the local address of the client.
     *
     * @return the local {@link SocketAddress} of the client
     */
    SocketAddress localAddress();

    /**
     * Checks if the client is currently active and connected to a server.
     *
     * @return true if the client is active, false otherwise
     */
    boolean isActive();
}
