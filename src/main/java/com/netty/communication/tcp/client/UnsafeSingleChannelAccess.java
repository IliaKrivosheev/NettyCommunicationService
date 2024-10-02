package com.netty.communication.tcp.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;

import java.util.concurrent.ExecutionException;

/**
 * Interface providing unsafe access to a single channel's attributes and functionalities.
 * <p>
 * This interface exposes methods for accessing the channel's pipeline, the channel itself,
 * the event loop associated with the channel, and the thread executing the event loop.
 */
public interface UnsafeSingleChannelAccess {

    /**
     * Retrieves the channel's pipeline.
     *
     * @return the {@link ChannelPipeline} associated with the channel
     */
    ChannelPipeline pipeline();

    /**
     * Retrieves the underlying channel.
     *
     * @return the {@link Channel} instance
     */
    Channel channel();

    /**
     * Retrieves the event loop associated with the channel.
     *
     * @return the {@link EventLoop} instance managing the channel's I/O operations
     */
    EventLoop eventLoop();

    /**
     * Retrieves the thread executing the event loop.
     *
     * @return the {@link Thread} managing the event loop
     * @throws ExecutionException   if the thread cannot be obtained due to an execution error
     * @throws InterruptedException  if the current thread is interrupted while waiting
     */
    Thread eventLoopThread() throws ExecutionException, InterruptedException;
}
