package com.netty.communication.handler.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.RequiredArgsConstructor;

/**
 * A Netty outbound handler that introduces a blocking delay after sending
 * a message. This can be useful for rate limiting or simulating network
 * latency.
 */
@RequiredArgsConstructor
public class OutboundBlockingHandler extends ChannelOutboundHandlerAdapter {
    private final long delayMillis;

    /**
     * Sends the outgoing message and blocks the thread for the specified
     * delay duration.
     *
     * @param ctx     the context of the channel
     * @param msg     the message to be sent
     * @param promise a promise to notify when the write operation is complete
     * @throws Exception if an error occurs during the write operation or
     *                   while sleeping
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.writeAndFlush(msg, promise);
        Thread.sleep(delayMillis);
    }
}
