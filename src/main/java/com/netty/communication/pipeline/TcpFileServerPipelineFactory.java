package com.netty.communication.pipeline;

import com.netty.communication.specification.channel.ChannelSpecProvider;
import com.netty.communication.specification.message.MessageSpecProvider;
import com.netty.communication.eventloop.ServerEventLoopGroupManager;
import com.netty.communication.handler.inbound.FileStoreHandler;
import com.netty.communication.handler.inbound.InboundMessageValidator;
import com.netty.communication.handler.inbound.InboundRequestHandler;
import com.netty.communication.handler.inbound.MessageDecoder;
import com.netty.communication.handler.outbound.MessageEncoder;
import com.netty.communication.handler.outbound.OutboundMessageValidator;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for configuring the pipeline for a TCP file server.
 * This factory creates and provides the necessary handlers for
 * processing inbound and outbound messages in the TCP communication.
 */
@RequiredArgsConstructor
@Component("tcpFileServerPipelineFactory")
public class TcpFileServerPipelineFactory implements PipelineFactory {
    private final ServerEventLoopGroupManager eventLoopGroupManager;
    private final MessageSpecProvider messageSpecProvider;
    private final ChannelSpecProvider channelSpecProvider;

    /**
     * Constructs the pipeline by providing a list of handler factories.
     * The handlers include those for encoding and decoding messages,
     * validating inbound messages, and handling file storage.
     *
     * @return a list of {@link HandlerFactory} instances configured for
     *         the TCP file server pipeline.
     */
    @Override
    public List<HandlerFactory> get() {
        return new ArrayList<>(List.of(
                HandlerFactory.of(() -> new IdleStateHandler(0, 0, channelSpecProvider.server().idleDetectionSeconds())),

                HandlerFactory.of(() -> new MessageEncoder(messageSpecProvider, channelSpecProvider.header())),
                HandlerFactory.of(OutboundMessageValidator::new),

                HandlerFactory.of(() -> new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4)),
                HandlerFactory.of(() -> new MessageDecoder(messageSpecProvider, channelSpecProvider.header())),
                HandlerFactory.of(InboundMessageValidator::new),

                HandlerFactory.of(eventLoopGroupManager.fireStore(), () -> new FileStoreHandler(channelSpecProvider.server().rootPath())),

                HandlerFactory.of(() -> new InboundRequestHandler(messageSpecProvider))
        ));
    }
}
