package com.netty.communication.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Utility class for accessing Netty Channel properties and methods.
 * <p>
 * This class provides static methods for retrieving the pipeline and event loop
 * associated with a Netty channel, as well as for obtaining the thread
 * associated with the channel's event loop.
 */
public final class ChannelAccessUtils {

    /**
     * Retrieves the ChannelPipeline associated with the specified channel.
     *
     * @param channel the Netty channel
     * @return the ChannelPipeline associated with the channel
     */
    public static ChannelPipeline pipeline(Channel channel) {
        return channel.pipeline();
    }

    /**
     * Retrieves the EventLoop associated with the specified channel.
     *
     * @param channel the Netty channel
     * @return the EventLoop associated with the channel
     */
    public static EventLoop eventLoop(Channel channel) {
        return channel.eventLoop();
    }

    /**
     * Retrieves the thread associated with the EventLoop of the specified channel.
     *
     * @param channel the Netty channel
     * @return the thread associated with the channel's EventLoop
     * @throws ExecutionException if the operation to retrieve the thread fails
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public static Thread eventLoopThread(Channel channel) throws ExecutionException, InterruptedException {
        AtomicReference<Thread> thread = new AtomicReference<>();
        channel.eventLoop().schedule(() ->
            thread.set(Thread.currentThread())
                , 0, TimeUnit.MILLISECONDS).get();
        return thread.get();
    }

    // Private constructor to prevent instantiation of utility class
    private ChannelAccessUtils() {}
}
