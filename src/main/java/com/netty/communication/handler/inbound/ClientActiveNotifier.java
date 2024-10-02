package com.netty.communication.handler.inbound;

import com.netty.communication.tcp.server.ClientActiveListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;

/**
 * Notifies a listener when a client becomes active or inactive.
 * This handler is responsible for notifying the provided {@link ClientActiveListener}
 * when a channel becomes active or inactive.
 */
@RequiredArgsConstructor
public class ClientActiveNotifier extends ChannelInboundHandlerAdapter {
    private final ClientActiveListener clientActiveListener;


    /**
     * Invoked when a channel is activated.
     * Notifies the listener that a client is now active.
     *
     * @param ctx the ChannelHandlerContext for the current channel
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        clientActiveListener.onActive(ctx.channel().remoteAddress(), ctx.channel());
        ctx.fireChannelActive();
    }

    /**
     * Invoked when a channel is deactivated.
     * Notifies the listener that a client is no longer active.
     *
     * @param ctx the ChannelHandlerContext for the current channel
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        clientActiveListener.onInactive(ctx.channel().remoteAddress());
        ctx.fireChannelInactive();
    }
}
