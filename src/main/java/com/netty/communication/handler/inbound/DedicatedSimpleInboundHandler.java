package com.netty.communication.handler.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * An abstract base class for a Netty inbound handler that processes messages of type T.
 * This handler ensures that the channel is registered in the correct executor context.
 *
 * @param <T> the type of messages handled by this handler
 */
public abstract class DedicatedSimpleInboundHandler<T> extends SimpleChannelInboundHandler<T> {

    /**
     * Called when the channel is registered.
     * This method asserts that the executor used by this context is not the same as the event loop
     * associated with the channel, ensuring proper thread management.
     *
     * @param ctx the ChannelHandlerContext for this handler
     * @throws Exception if an error occurs during the registration process
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        assert ctx.executor() != ctx.channel().eventLoop() :
                "Channel's event loop should not be the same as the handler's executor.";
        super.channelRegistered(ctx);
    }
}
