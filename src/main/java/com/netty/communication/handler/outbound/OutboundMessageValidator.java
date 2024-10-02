package com.netty.communication.handler.outbound;

import com.netty.communication.message.MessageValidatable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * An outbound handler that validates outgoing messages before sending them.
 * This ensures that messages conform to the expected format or criteria
 * before being sent over the network.
 */
public class OutboundMessageValidator extends ChannelOutboundHandlerAdapter {

    /**
     * Validates the outgoing message and then passes it down the pipeline.
     *
     * @param ctx     the context of the channel
     * @param msg     the message to be validated and sent
     * @param promise a promise to notify when the write operation is complete
     * @throws Exception if the message validation fails or any error occurs during writing
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof MessageValidatable message) {
            message.validate();
        } else {
            throw new IllegalArgumentException("Message must implement MessageValidatable.");
        }
        ctx.write(msg, promise);
    }
}
