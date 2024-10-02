package com.netty.communication.message;

import com.netty.communication.specification.response.ResponseSpec;
import com.netty.communication.handler.outbound.EncodedBodyPiece;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Represents a response message in the protocol.
 * This message contains a {@link ResponseSpec} which provides details
 * about the response, such as error numbers.
 */
@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public class ResponseMessage implements ProtocolMessage {
    private final ResponseSpec responseSpec;

    /**
     * Decodes a {@link ResponseMessage} from the provided ByteBuf.
     *
     * @param message the ByteBuf containing the encoded message
     * @return the decoded ResponseMessage
     */
    public static ResponseMessage decode(ByteBuf message) {
        ResponseSpec responseSpec = ResponseSpec.match(message.readInt());
        return new ResponseMessage(responseSpec);
    }

    /**
     * Encodes this ResponseMessage into the provided ByteBuf.
     *
     * @param buffer the ByteBuf to encode the message into
     * @return a list of EncodedBodyPiece representing the encoded message
     */
    @Override
    public List<EncodedBodyPiece> encode(ByteBuf buffer) {
        buffer.writeInt(responseSpec.getErrorNo());
        return List.of(new EncodedBodyPiece(buffer, buffer.readableBytes()));
    }
}
