package com.netty.communication.handler.inbound;

import com.netty.communication.message.MessageValidatable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * InboundMessageValidator is a handler that validates inbound messages
 * implementing the MessageValidatable interface.
 */
public class InboundMessageValidator extends ChannelInboundHandlerAdapter {

    /**
     * Validates the incoming message and passes it to the next handler in the pipeline.
     *
     * @param ctx the ChannelHandlerContext which provides various operations on the channel
     * @param msg the message to be validated
     * @throws Exception if the message is not valid or if an error occurs during validation
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageValidatable validatable = (MessageValidatable) msg;
        validatable.validate();
        ctx.fireChannelRead(msg);
    }
}
