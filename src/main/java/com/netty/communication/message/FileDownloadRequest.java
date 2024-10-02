package com.netty.communication.message;

import com.netty.communication.handler.outbound.EncodedBodyPiece;
import io.netty.buffer.ByteBuf;
import lombok.Builder;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Represents a request to download a file.
 * This message contains the source and destination file paths.
 */
@Builder
@Getter
public class FileDownloadRequest implements ProtocolMessage {

    private final String sourceFilePath;
    private final String destFilePath;

    /**
     * Decodes a {@link ByteBuf} message into a {@link FileDownloadRequest}.
     *
     * @param message the {@link ByteBuf} containing the encoded request
     * @return the decoded {@link FileDownloadRequest}
     */
    public static FileDownloadRequest decode(ByteBuf message) {
        return builder()
                .sourceFilePath(message.readSlice(message.readInt()).toString(StandardCharsets.UTF_8))
                .destFilePath(message.readSlice(message.readInt()).toString(StandardCharsets.UTF_8))
                .build();
    }

    /**
     * Encodes the {@link FileDownloadRequest} into a {@link ByteBuf}.
     *
     * @param buffer the {@link ByteBuf} to write the encoded request into
     * @return a list of {@link EncodedBodyPiece} representing the encoded message
     */
    @Override
    public List<EncodedBodyPiece> encode(ByteBuf buffer) {
        buffer.writeInt(sourceFilePath.length());
        buffer.writeCharSequence(sourceFilePath, StandardCharsets.UTF_8);
        buffer.writeInt(destFilePath.length());
        buffer.writeCharSequence(destFilePath, StandardCharsets.UTF_8);
        return List.of(new EncodedBodyPiece(buffer, buffer.readableBytes()));
    }

    /**
     * Validates the contents of the {@link FileDownloadRequest}.
     *
     * @throws Exception if validation fails
     */
    @Override
    public void validate() throws Exception {
    }
}
