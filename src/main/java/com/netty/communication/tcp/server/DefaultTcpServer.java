package com.netty.communication.tcp.server;

import com.netty.communication.handler.inbound.ClientActiveNotifier;
import com.netty.communication.handler.inbound.ReadDataUpdater;
import com.netty.communication.pipeline.HandlerFactory;
import com.netty.communication.util.PropagateChannelFuture;
import com.netty.communication.util.ChannelAccessUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.lang.Nullable;

import java.net.SocketAddress;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Default implementation of a TCP server that manages client connections and communication.
 * <p>
 * This server uses Netty's networking capabilities to handle incoming connections and provides
 * methods for reading and writing messages to clients. It maintains a list of active client channels
 * and facilitates communication between the server and its clients.
 */
public class DefaultTcpServer implements TcpServer {
    private ServerBootstrap bootstrap;
    private ConcurrentHashMap<SocketAddress, ActiveChannel> activeChannels;

    /**
     * Initializes the TCP server with the specified event loop groups and handler factories.
     *
     * @param bossGroup   the event loop group for handling incoming connections
     * @param childGroup  the event loop group for handling channel events
     * @param pipelineFactory the list of handler factories to be added to the channel pipeline
     */
    @Override
    public void init(EventLoopGroup bossGroup, EventLoopGroup childGroup, List<HandlerFactory> pipelineFactory) {
        pipelineFactory.add(
                HandlerFactory.of(() -> new ClientActiveNotifier(this)));
        pipelineFactory.add(
                HandlerFactory.of(() -> new ReadDataUpdater(this)));

        bootstrap = new ServerBootstrap();
        activeChannels = new ConcurrentHashMap<>();

        bootstrap.group(bossGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        pipelineFactory.forEach(handlerFactory -> {
                            ch.pipeline().addLast(handlerFactory.workGroup(), handlerFactory.handler());
                        });
                    }
                });
    }

    /**
     * Starts the TCP server and binds it to the specified port.
     *
     * @param bindPort the port to bind the server to
     * @return a ChannelFuture representing the result of the bind operation
     * @throws InterruptedException if the operation is interrupted
     */
    @Override
    public ChannelFuture start(int bindPort) throws InterruptedException {
        bootstrap.localAddress("0.0.0.0", bindPort);
        return bootstrap.bind().sync();
    }

    /**
     * Sends a message to all active clients connected to the server.
     *
     * @param message the message to send
     * @return a Future representing the completion of the send operation
     */
    @Override
    public Future<Void> sendAll(Object message) {
        List<CompletableFuture<Void>> sendFutures = new ArrayList<>();

        activeChannels.values().forEach(activeChannel -> {
            var sendFuture = new CompletableFuture<Void>();
            activeChannel.channel().writeAndFlush(message).addListener((ChannelFutureListener) channelFuture -> {
                PropagateChannelFuture.propagate(channelFuture, sendFuture);
            });
            sendFutures.add(sendFuture);
        });

        return CompletableFuture.allOf(sendFutures.toArray(CompletableFuture[]::new));
    }

    /**
     * Reads a message from a specific client with a specified timeout.
     *
     * @param clientAddress the address of the client to read from
     * @param timeout       the maximum time to wait for a message
     * @param unit          the time unit of the timeout
     * @return the message read from the client, or null if the timeout is reached
     * @throws InterruptedException if the operation is interrupted
     */
    @Override
    @Nullable
    public Object read(SocketAddress clientAddress, int timeout, TimeUnit unit) throws InterruptedException {
        BlockingQueue<Object> readQueue = activeChannels.get(clientAddress).readQueue();
        if (readQueue == null) {
            throw new NotYetConnectedException();
        }
        return readQueue.poll(timeout, unit);
    }

    /**
     * Reads a message from a specific client synchronously.
     *
     * @param clientAddress the address of the client to read from
     * @return the message read from the client
     * @throws InterruptedException if the operation is interrupted
     */
    @Override
    public Object readSync(SocketAddress clientAddress) throws InterruptedException {
        BlockingQueue<Object> readQueue = activeChannels.get(clientAddress).readQueue();
        if (readQueue == null) {
            throw new NotYetConnectedException();
        }
        return readQueue.take();
    }

    /**
     * Checks if a client is active (connected) based on the client's address.
     *
     * @param clientAddress the address of the client to check
     * @return true if the client is active, false otherwise
     */
    @Override
    public boolean isActive(SocketAddress clientAddress) {
        return activeChannels.containsKey(clientAddress);
    }

    /**
     * Called when a new client becomes active (connected).
     *
     * @param remoteAddress   the remote address of the connected client
     * @param workingChannel   the channel through which the client is connected
     */
    @Override
    public void onActive(SocketAddress remoteAddress, Channel workingChannel) {
        ActiveChannel activeChannel = new ActiveChannel(remoteAddress, workingChannel, new LinkedBlockingQueue<>());
        activeChannels.put(remoteAddress, activeChannel);
    }

    /**
     * Called when a client becomes inactive (disconnected).
     *
     * @param remoteAddress the remote address of the disconnected client
     */
    @Override
    public void onInactive(SocketAddress remoteAddress) {
        activeChannels.remove(remoteAddress);
    }

    /**
     * Called when data is available to read from a specific client.
     *
     * @param remoteAddress the remote address of the client
     * @param data          the data to be read
     */
    @Override
    public void onReadAvailable(SocketAddress remoteAddress, Object data) {
        BlockingQueue<Object> queue = activeChannels.get(remoteAddress).readQueue();
        if (queue != null) {
            queue.add(data);
        }
    }

    /**
     * Gets the pipeline associated with the specified client's channel.
     *
     * @param remoteAddress the remote address of the client
     * @return the ChannelPipeline for the specified client's channel
     */
    @Override
    public ChannelPipeline pipeline(SocketAddress remoteAddress) {
        ActiveChannel activeChannel = getActiveChannel(remoteAddress);
        return ChannelAccessUtils.pipeline(activeChannel.channel());
    }

    /**
     * Gets the channel associated with the specified client's address.
     *
     * @param remoteAddress the remote address of the client
     * @return the Channel for the specified client's address
     */
    @Override
    public Channel channel(SocketAddress remoteAddress) {
        ActiveChannel activeChannel = getActiveChannel(remoteAddress);
        return activeChannel.channel();
    }

    /**
     * Gets the EventLoop associated with the specified client's channel.
     *
     * @param remoteAddress the remote address of the client
     * @return the EventLoop for the specified client's channel
     */
    @Override
    public EventLoop eventLoop(SocketAddress remoteAddress) {
        ActiveChannel activeChannel = getActiveChannel(remoteAddress);
        return ChannelAccessUtils.eventLoop(activeChannel.channel());
    }

    /**
     * Gets the thread associated with the EventLoop of the specified client's channel.
     *
     * @param remoteAddress the remote address of the client
     * @return the thread for the EventLoop of the specified client's channel
     * @throws ExecutionException if the operation fails
     * @throws InterruptedException if the operation is interrupted
     */
    @Override
    public Thread eventLoopThread(SocketAddress remoteAddress) throws ExecutionException, InterruptedException {
        ActiveChannel channel = getActiveChannel(remoteAddress);
        return ChannelAccessUtils.eventLoopThread(channel.channel());
    }

    /**
     * Retrieves the active channel associated with the specified remote address.
     *
     * @param remoteAddress the remote address of the client
     * @return the ActiveChannel for the specified remote address
     * @throws NotYetConnectedException if the client is not connected
     */
    private ActiveChannel getActiveChannel(SocketAddress remoteAddress) {
        if (!activeChannels.containsKey(remoteAddress)) {
            throw new NotYetConnectedException();
        }
        return activeChannels.get(remoteAddress);
    }

    /**
     * A record representing an active channel for a connected client.
     *
     * @param remoteAddress the remote address of the client
     * @param channel       the Netty channel associated with the client
     * @param readQueue     the queue for incoming messages from the client
     */
    private record ActiveChannel(SocketAddress remoteAddress, Channel channel, BlockingQueue<Object> readQueue) {}
}
