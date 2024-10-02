package com.netty.communication.pipeline;

import com.netty.communication.eventloop.ClientEventLoopGroupManager;
import com.netty.communication.specification.channel.ChannelSpecProvider;
import com.netty.communication.specification.message.MessageSpecProvider;
import com.netty.communication.handler.duplex.RequestResultChecker;
import com.netty.communication.handler.inbound.FileStoreHandler;
import com.netty.communication.handler.inbound.InboundMessageValidator;
import com.netty.communication.handler.inbound.MessageDecoder;
import com.netty.communication.handler.outbound.MessageEncoder;
import com.netty.communication.handler.outbound.OutboundMessageValidator;
import com.netty.communication.handler.outbound.UserRequestHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for creating a TCP file client pipeline.
 * This class is responsible for configuring the Netty pipeline
 * with various handlers for inbound and outbound message processing.
 */
@RequiredArgsConstructor
@Component("tcpFileClientPipelineFactory")
public class TcpFileClientPipelineFactory implements PipelineFactory {
    private final ClientEventLoopGroupManager eventLoopGroupManager;
    private final MessageSpecProvider messageSpecProvider;
    private final ChannelSpecProvider channelSpecProvider;

    /**
     * Retrieves a list of {@link HandlerFactory} instances that
     * define the TCP file client pipeline configuration.
     *
     * @return a list of {@link HandlerFactory} instances used to create handlers
     */
    @Override
    public List<HandlerFactory> get() {
        return new ArrayList<>(List.of(
                HandlerFactory.of(() -> new IdleStateHandler(0, 0, channelSpecProvider.client().idleDetectionSeconds())),

                HandlerFactory.of(() -> new MessageEncoder(messageSpecProvider, channelSpecProvider.header())),
                HandlerFactory.of(OutboundMessageValidator::new),
                HandlerFactory.of(() -> new UserRequestHandler(messageSpecProvider)),

                HandlerFactory.of(() -> new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4)),
                HandlerFactory.of(() -> new MessageDecoder(messageSpecProvider, channelSpecProvider.header())),
                HandlerFactory.of(InboundMessageValidator::new),
                HandlerFactory.of(eventLoopGroupManager.fireStore(), () -> new FileStoreHandler(channelSpecProvider.client().rootPath())), // Dedicated EventLoopGroup

                HandlerFactory.of(RequestResultChecker::new)));
    }
}
