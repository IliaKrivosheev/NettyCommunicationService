package com.netty.communication.tcp.client;

import com.netty.communication.handler.inbound.ReadDataListener;
import com.netty.communication.pipeline.HandlerFactory;
import io.netty.channel.EventLoopGroup;

import java.util.List;

/**
 * Represents a TCP client that can send requests, listen for data, and provide
 * access to a single channel.
 * <p>
 * This interface extends {@link TcpClientRequest}, {@link ReadDataListener}, and
 * {@link UnsafeSingleChannelAccess}, providing a comprehensive set of functionalities
 * for TCP communication.
 */
public interface TcpClient extends TcpClientRequest, ReadDataListener, UnsafeSingleChannelAccess {

    /**
     * Initializes the TCP client with the specified event loop group and pipeline factories.
     *
     * @param eventLoopGroup the event loop group to use for handling channel events
     * @param pipelineFactory a list of handler factories to configure the channel pipeline
     */
    void init(EventLoopGroup eventLoopGroup, List<HandlerFactory> pipelineFactory);
}
