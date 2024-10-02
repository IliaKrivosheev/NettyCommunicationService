package com.netty.communication.handler.inbound;

import com.netty.communication.specification.channel.HeaderSpecProvider;
import com.netty.communication.specification.message.MessageDecoderProvider;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;

/**
 * MessageDecoder decodes incoming ByteBuf messages into application-specific message objects.
 * It uses the provided HeaderSpecProvider to extract the message ID and the MessageDecoderProvider
 * to retrieve the appropriate decoder for the given ID.
 */
@RequiredArgsConstructor
public class MessageDecoder extends SimpleChannelInboundHandler<ByteBuf> {
    private final MessageDecoderProvider decoderProvider;
    private final HeaderSpecProvider headerSpecProvider;

    /**
     * Reads the incoming ByteBuf message, extracts the message ID, and decodes it into a specific message type.
     *
     * @param ctx the ChannelHandlerContext which provides various operations on the channel
     * @param rawMessage the raw ByteBuf message received from the channel
     * @throws Exception if an error occurs during message processing
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf rawMessage) throws Exception {
        var id = headerSpecProvider.id().readFunc(rawMessage);
        var decoder = decoderProvider.getDecoder(id);
        var message = decoder.apply(rawMessage);
        ctx.fireChannelRead(message);
    }
}
