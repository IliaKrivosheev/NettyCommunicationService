package com.netty.communication.handler.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * A channel handler that releases received {@link ByteBuf} messages.
 * <p>
 * This handler is intended to manage the lifecycle of incoming
 * ByteBuf messages by releasing them after processing.
 * </p>
 */
public class ReleaseHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /**
     * Processes the incoming {@link ByteBuf} message and releases it.
     *
     * @param ctx the {@link ChannelHandlerContext} which this handler belongs to
     * @param msg the incoming {@link ByteBuf} message
     * @throws Exception if an error occurs while processing the message
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        try {
            // Process the message here if needed
        } finally {
            // Release the ByteBuf message to prevent memory leaks
            msg.release();
        }
    }
}
