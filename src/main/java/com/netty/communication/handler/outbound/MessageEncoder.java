package com.netty.communication.handler.outbound;

import com.netty.communication.specification.channel.HeaderSpecProvider;
import com.netty.communication.specification.message.ProtocolIdProvider;
import com.netty.communication.message.MessageEncodable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;

/**
 * A Netty outbound handler that encodes messages by creating a header and
 * body pieces, then sending them through the channel.
 * <p>
 * The body of the message is split into multiple pieces, particularly to
 * accommodate the way {@link FileRegion} is handled by Netty when sending
 * file content.
 * </p>
 */
@RequiredArgsConstructor
public class MessageEncoder extends ChannelOutboundHandlerAdapter {
    private final ProtocolIdProvider idProvider;
    private final HeaderSpecProvider headerSpecProvider;

    /**
     * Encodes the message by creating a header and body pieces, and sends
     * them through the channel.
     *
     * @param ctx     the context of the channel
     * @param msg     the message to encode and send, must implement
     *                {@link MessageEncodable}
     * @param promise a promise to notify when the operation is complete
     * @throws Exception if an error occurs during encoding or sending the
     *                   message
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        MessageEncodable encodable = (MessageEncodable) msg;

        List<EncodedBodyPiece> bodyPieces = encodable.encode(ctx.alloc().buffer());

        ByteBuf header = buildHeader(ctx.alloc().buffer(), bodyPieces, encodable.getClass());
        ctx.write(header);

        if (isMultiplePieces(bodyPieces)) {
            IntStream.rangeClosed(0, lastIndex(bodyPieces) - 1)
                    .forEach(i -> ctx.write(bodyPieces.get(i).contents()));
        }

        ctx.write(bodyPieces.get(lastIndex(bodyPieces)).contents(), promise);
    }

    /**
     * Builds the message header using the specified body pieces and
     * the class of the encodable message.
     *
     * @param header      the ByteBuf to write the header to
     * @param bodyPieces  the list of body pieces
     * @param clazz       the class of the message being encoded
     * @return the populated header ByteBuf
     */
    private ByteBuf buildHeader(ByteBuf header, List<EncodedBodyPiece> bodyPieces, Class<? extends MessageEncodable> clazz) {
        var length = bodyPieces.stream().mapToInt(EncodedBodyPiece::length).sum() + headerSpecProvider.id().length();
        var id = idProvider.getProtocolId(clazz);

        headerSpecProvider.length().writeFunc(header, length);
        headerSpecProvider.id().writeFunc(header, id);
        return header;
    }

    /**
     * Retrieves the index of the last body piece.
     *
     * @param body the list of body pieces
     * @return the index of the last piece
     */
    private static int lastIndex(List<EncodedBodyPiece> body) {
        return body.size() - 1;
    }

    /**
     * Checks if the body contains multiple pieces.
     *
     * @param body the list of body pieces
     * @return true if there are multiple pieces, false otherwise
     */
    private static boolean isMultiplePieces(List<EncodedBodyPiece> body) {
        return body.size() > 1;
    }
}
