package com.netty.communication.tcp.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;

import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;

/**
 * Interface for accessing multiple channels in an unsafe manner.
 * <p>
 * This interface provides methods to retrieve the channel pipeline, channel,
 * event loop, and the associated thread for a given remote address. The methods
 * do not perform any checks to ensure that the specified address is valid or
 * that the channel is active, hence the 'unsafe' designation.
 */
public interface UnsafeMultiChannelAccess {

    /**
     * Retrieves the channel pipeline associated with the specified remote address.
     *
     * @param remoteAddress the address of the remote client
     * @return the ChannelPipeline associated with the remote address
     */
    ChannelPipeline pipeline(SocketAddress remoteAddress);

    /**
     * Retrieves the channel associated with the specified remote address.
     *
     * @param remoteAddress the address of the remote client
     * @return the Channel associated with the remote address
     */
    Channel channel(SocketAddress remoteAddress);

    /**
     * Retrieves the event loop associated with the specified remote address.
     *
     * @param remoteAddress the address of the remote client
     * @return the EventLoop associated with the remote address
     */
    EventLoop eventLoop(SocketAddress remoteAddress);

    /**
     * Retrieves the thread associated with the event loop for the specified remote address.
     *
     * @param remoteAddress the address of the remote client
     * @return the thread associated with the event loop
     * @throws ExecutionException if the thread cannot be retrieved
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    Thread eventLoopThread(SocketAddress remoteAddress) throws ExecutionException, InterruptedException;
}
