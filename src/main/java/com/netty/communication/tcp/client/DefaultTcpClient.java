package com.netty.communication.tcp.client;

import com.netty.communication.handler.inbound.ReadDataUpdater;
import com.netty.communication.pipeline.HandlerFactory;
import com.netty.communication.util.ChannelAccessUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.lang.Nullable;

import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Default implementation of the {@link TcpClient} interface.
 * <p>
 * This class handles the TCP client operations such as connecting to a server,
 * sending and receiving data, and managing the channel pipeline.
 */
public class DefaultTcpClient implements TcpClient {
    private Bootstrap bootstrap;
    private volatile Channel channel;
    private volatile BlockingQueue<Object> readQueue;

    @Override
    public void init(EventLoopGroup eventLoopGroup, List<HandlerFactory> pipelineFactory) {
        readQueue = new LinkedBlockingQueue<>();

        pipelineFactory.add(HandlerFactory.of(() -> new ReadDataUpdater(this)));

        bootstrap = new Bootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        pipelineFactory.forEach(handlerFactory -> {
                            ch.pipeline().addLast(handlerFactory.workGroup(), handlerFactory.handler());
                        });
                    }
                });
    }

    @Override
    public ChannelFuture connect(String ip, int port) throws InterruptedException {
        bootstrap.remoteAddress(ip, port);
        var result = bootstrap.connect().sync();
        if (result.isSuccess()) {
            channel = result.channel();
        }
        return result;
    }

    @Override
    public ChannelFuture disconnect() {
        return channel.close();
    }

    @Override
    public ChannelFuture send(Object data) {
        return channel.writeAndFlush(data);
    }

    @Override
    public Object readSync() throws InterruptedException {
        return readQueue.take();
    }

    @Override
    @Nullable
    public Object read(int timeout, TimeUnit unit) throws InterruptedException {
        return readQueue.poll(timeout, unit);
    }

    @Override
    public SocketAddress localAddress() {
        return channel.localAddress();
    }

    @Override
    public boolean isActive() {
        return channel.isActive();
    }

    @Override
    public void onReadAvailable(SocketAddress remoteAddress, Object data) {
        readQueue.add(data);
    }

    @Override
    public ChannelPipeline pipeline() {
        return ChannelAccessUtils.pipeline(channel);
    }

    @Override
    public Channel channel() {
        return channel;
    }

    @Override
    public EventLoop eventLoop() {
        return ChannelAccessUtils.eventLoop(channel);
    }

    @Override
    public Thread eventLoopThread() throws ExecutionException, InterruptedException {
        return ChannelAccessUtils.eventLoopThread(channel);
    }
}
