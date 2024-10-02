package com.netty.communication.handler.inbound;

import com.netty.communication.message.ProtocolMessage;
import com.netty.communication.message.ResponseMessage;
import com.netty.communication.specification.message.InboundRequestProcessorProvider;
import com.netty.communication.specification.response.ResponseSpec;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * InboundRequestHandler processes incoming ProtocolMessage requests and sends appropriate responses.
 * It handles exceptions and idle state events in the channel.
 */
@Slf4j
@RequiredArgsConstructor
public class InboundRequestHandler extends SimpleChannelInboundHandler<ProtocolMessage> {
    private final InboundRequestProcessorProvider processorProvider;

    /**
     * Handles the incoming ProtocolMessage and processes it using the appropriate request processor.
     *
     * @param ctx the ChannelHandlerContext which provides various operations on the channel
     * @param message the incoming ProtocolMessage to be processed
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtocolMessage message) {
        try {
            var requestProcessor = processorProvider.getInboundRequestProcessor(message.getClass());
            var responses = requestProcessor.process(message);

            responses.add(new ResponseMessage(ResponseSpec.OK));
            responses.forEach(response -> {
                ctx.writeAndFlush(response).addListener(future -> {
                    if (!future.isSuccess()) {
                        log.error("Failed to send a response.", future.cause());
                    }
                });
            });
        } catch (Throwable throwable) {
            handleException(ctx, throwable);
        }
    }

    /**
     * Handles exceptions that occur during message processing.
     *
     * @param ctx the ChannelHandlerContext which provides various operations on the channel
     * @param cause the throwable that caused the exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        handleException(ctx, cause);
    }

    /**
     * Handles exceptions by logging the error and sending an appropriate response back to the client.
     *
     * @param ctx the ChannelHandlerContext which provides various operations on the channel
     * @param cause the throwable that caused the exception
     */
    private static void handleException(ChannelHandlerContext ctx, Throwable cause) {
        log.error("An exception was thrown while processing the request on the server side.", cause);
        ctx.writeAndFlush(new ResponseMessage(ResponseSpec.match(cause)));
        ctx.close();
    }

    /**
     * Handles user events, specifically idle state events.
     * Closes the channel if it is idle for too long.
     *
     * @param ctx the ChannelHandlerContext which provides various operations on the channel
     * @param evt the user event triggered
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent e && e.state() == IdleState.ALL_IDLE) {
                log.error("The client({}) channel is idle.", ctx.channel().remoteAddress());
                ctx.close();
            }

    }
}
