package com.netty.communication.handler.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;

/**
 * A channel handler that updates data read from a channel by notifying a
 * {@link ReadDataListener} when data is available.
 */
@RequiredArgsConstructor
public class ReadDataUpdater extends SimpleChannelInboundHandler<Object> {
    private final ReadDataListener readDataListener;

    /**
     * Called when a message is received. This method forwards the message
     * to the registered {@link ReadDataListener} and then propagates the
     * message to the next handler in the pipeline.
     *
     * @param ctx the {@link ChannelHandlerContext} which this
     *            {@link ReadDataUpdater} belongs to
     * @param msg the message read from the channel
     * @throws Exception if an error occurs while processing the message
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        readDataListener.onReadAvailable(ctx.channel().remoteAddress(), msg);
        ctx.fireChannelRead(msg);
    }
}
