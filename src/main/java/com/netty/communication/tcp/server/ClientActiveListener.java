package com.netty.communication.tcp.server;

import io.netty.channel.Channel;

import java.net.SocketAddress;

/**
 * Listener interface for monitoring the active and inactive states of a client connection.
 * <p>
 * Implementations of this interface can react to client connection events, such as when a client
 * becomes active (connected) or inactive (disconnected).
 */
public interface ClientActiveListener {

    /**
     * Invoked when a client becomes active (connected).
     *
     * @param remoteAddress the remote address of the connected client
     * @param workingChannel the channel through which the client is connected
     */
    void onActive(SocketAddress remoteAddress, Channel workingChannel);

    /**
     * Invoked when a client becomes inactive (disconnected).
     *
     * @param remoteAddress the remote address of the disconnected client
     */
    void onInactive(SocketAddress remoteAddress);
}
