package com.netty.communication.handler.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.RequiredArgsConstructor;

/**
 * An outbound handler that appends a line separator to the message being sent.
 * <p>
 * This handler is useful for ensuring that messages conform to a specific format
 * by appending a designated line separator (such as a newline character) to each
 * message before it is sent out.
 * </p>
 */
@RequiredArgsConstructor
public class LineAppender extends ChannelOutboundHandlerAdapter {
    private final String lineSeparator;

    /**
     * Writes the message to the next handler in the pipeline, appending a
     * line separator to the message.
     *
     * @param ctx     the context of the channel
     * @param msg     the message to write, which will be appended with the line separator
     * @param promise a promise to notify when the operation is complete
     * @throws Exception if an error occurs while writing the message
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg + lineSeparator, promise);
    }
}
