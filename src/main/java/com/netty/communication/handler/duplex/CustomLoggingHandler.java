package com.netty.communication.handler.duplex;

import com.netty.communication.message.ProtocolMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Custom logging handler for Netty that logs information about read and written messages.
 * This handler extends the {@link LoggingHandler} to provide additional logging for
 * {@link ByteBuf} and {@link ProtocolMessage} instances, including their sizes and types.
 */
@Slf4j
public class CustomLoggingHandler extends LoggingHandler {
    private final String nextHandler;

    public CustomLoggingHandler(String nextHandler) {
        super(LogLevel.INFO);
        this.nextHandler = nextHandler;
    }

    /**
     * Logs the information about the read message before passing it to the next handler.
     * Logs the number of readable bytes if the message is a {@link ByteBuf},
     * or the simple name of the class if the message is a {@link ProtocolMessage}.
     *
     * @param ctx the {@link ChannelHandlerContext} for the current channel
     * @param msg the message read from the channel
     * @throws Exception if an error occurs while processing the message
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf buf) {
            log.info("channelRead nBytes: {} ({} before)", buf.readableBytes(), nextHandler);
        } else if (msg instanceof ProtocolMessage protocolMessage) {
            log.info("channelRead: {} ({} before)", protocolMessage.getClass().getSimpleName(), nextHandler);
        }
        ctx.fireChannelRead(msg);
    }

    /**
     * Logs the information about the written message before passing it to the next handler.
     * Logs the number of readable bytes if the message is a {@link ByteBuf},
     * or the simple name of the class if the message is a {@link ProtocolMessage}.
     *
     * @param ctx     the {@link ChannelHandlerContext} for the current channel
     * @param msg     the message to write to the channel
     * @param promise a {@link ChannelPromise} to notify when the write operation is complete
     * @throws Exception if an error occurs while processing the message
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof ByteBuf buf) {
            log.info("write nBytes: {} ({} before)", buf.readableBytes(), nextHandler);
        } else if (msg instanceof ProtocolMessage protocolMessage) {
            log.info("write: {} ({} before)", protocolMessage.getClass().getSimpleName(), nextHandler);
        }
        ctx.write(msg, promise);
    }
}
