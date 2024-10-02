package com.netty.communication.handler.outbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.FileRegion;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Represents a piece of encoded body content that can either be a
 * {@link ByteBuf} or a {@link FileRegion}.
 * <p>
 * This class is used to encapsulate the content and its length,
 * ensuring that the contents are of a valid type.
 * </p>
 */
@Getter
@Accessors(fluent = true)
public class EncodedBodyPiece {

    private final Object contents;
    private final int length;

    /**
     * Constructs an instance of {@link EncodedBodyPiece} with the specified contents and length.
     *
     * @param contents the contents of the body piece, must be an instance of
     *                 {@link ByteBuf} or {@link FileRegion}
     * @param length   the length of the contents
     * @throws IllegalArgumentException if contents is not an instance of
     *                                  {@link ByteBuf} or {@link FileRegion}
     */
    public EncodedBodyPiece(Object contents, int length) {
        if (!(contents instanceof ByteBuf || contents instanceof FileRegion)) {
            throw new IllegalArgumentException("contents must be ByteBuf or FileRegion");
        }
        this.contents = contents;
        this.length = length;
    }
}
