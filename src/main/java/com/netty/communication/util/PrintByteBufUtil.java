package com.netty.communication.util;

import io.netty.buffer.ByteBuf;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Utility class for printing the contents of a Netty {@link ByteBuf} as a byte array.
 * <p>
 * This class provides methods for extracting bytes from a ByteBuf and logging them.
 */
@Slf4j
@UtilityClass
public class PrintByteBufUtil {

    /**
     * Logs the contents of the given {@link ByteBuf} as a byte array.
     *
     * @param buf the ByteBuf to be printed
     */
    public void printByteArray(ByteBuf buf) {
        if (buf.readableBytes() == 0) {
            log.info("The ByteBuf is empty.");
            return;
        }

        byte[] bytes = new byte[buf.readableBytes()];
        buf.getBytes(buf.readerIndex(), bytes);

        log.info("ByteBuf contents: {}", Arrays.toString(bytes));
    }
}
