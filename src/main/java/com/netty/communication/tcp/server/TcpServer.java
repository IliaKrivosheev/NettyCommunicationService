package com.netty.communication.tcp.server;

import com.netty.communication.handler.inbound.ReadDataListener;
import com.netty.communication.pipeline.HandlerFactory;
import io.netty.channel.EventLoopGroup;

import java.util.List;

/**
 * Interface representing a TCP server that handles client connections and communication.
 * <p>
 * This server interface defines methods for initializing the server, managing client
 * connections, and handling incoming data. It extends various interfaces to provide
 * functionalities for handling server requests, listening to read data, managing client
 * activity, and accessing multiple channels.
 */
public interface TcpServer extends TcpServerRequest, ReadDataListener, ClientActiveListener, UnsafeMultiChannelAccess {

    /**
     * Initializes the TCP server with the specified event loop groups and handler factories.
     *
     * @param bossGroup    the event loop group for handling incoming connections
     * @param workGroup    the event loop group for handling channel events
     * @param childHandlers the list of handler factories to be added to the channel pipeline
     */
    void init(EventLoopGroup bossGroup, EventLoopGroup workGroup, List<HandlerFactory> childHandlers);
}
