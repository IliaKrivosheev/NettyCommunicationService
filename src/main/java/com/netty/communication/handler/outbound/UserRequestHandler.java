package com.netty.communication.handler.outbound;

import com.netty.communication.message.UserRequest;
import com.netty.communication.specification.message.OutboundRequestProcessorProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.RequiredArgsConstructor;

/**
 * A handler for processing outgoing user requests.
 * This handler uses an outbound request processor to convert a
 * UserRequest into one or more outbound messages and sends them through the channel.
 */
@RequiredArgsConstructor
public class UserRequestHandler extends ChannelOutboundHandlerAdapter {
    private final OutboundRequestProcessorProvider processorProvider;

    /**
     * Processes an outgoing UserRequest message, converts it into
     * outbound messages using the appropriate request processor,
     * and writes those messages to the channel.
     *
     * @param ctx     the context of the channel
     * @param msg     the message to be processed, expected to be of type UserRequest
     * @param promise a promise to notify when the write operation is complete
     * @throws Exception if an error occurs during message processing or writing
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof UserRequest userMessage)) {
            throw new IllegalArgumentException("Message must be of type UserRequest.");
        }

        var requestProcessor = processorProvider.getOutboundRequestProcessor(userMessage.getClass());
        var messages = requestProcessor.process(userMessage);

        for (var message : messages) {
            ctx.writeAndFlush(message);
        }

        promise.setSuccess();
    }
}
