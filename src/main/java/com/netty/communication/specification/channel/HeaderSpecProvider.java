package com.netty.communication.specification.channel;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * Provides specifications for the headers used in communication.
 * This class contains specifications for the ID and length fields
 * in the protocol messages.
 */
@Getter
@Accessors(fluent = true)
@Component
public class HeaderSpecProvider {

    private final IdSpec id = new IdSpec();

    private final LengthSpec length = new LengthSpec();

    /**
     * Specification for the ID field.
     */
    @Getter
    public static class IdSpec {
        private final int length = 4;

        /**
         * Reads an integer value from the given ByteBuf as the ID.
         *
         * @param buf the ByteBuf to read from
         * @return the read integer value
         */
        public int readFunc(ByteBuf buf) {
            return buf.readInt();
        }

        /**
         * Writes an integer value to the given ByteBuf as the ID.
         *
         * @param buf the ByteBuf to write to
         * @param value the integer value to write
         */
        public void writeFunc(ByteBuf buf, int value) {
            buf.writeInt(value);
        }
    }

    /**
     * Specification for the Length field.
     */
    @Getter
    public static class LengthSpec {
        private final int length = 4;

        /**
         * Reads an integer value from the given ByteBuf as the Length.
         *
         * @param buf the ByteBuf to read from
         * @return the read integer value
         */
        public int readFunc(ByteBuf buf) {
            return buf.readInt();
        }

        /**
         * Writes an integer value to the given ByteBuf as the Length.
         *
         * @param buf the ByteBuf to write to
         * @param value the integer value to write
         */
        public void writeFunc(ByteBuf buf, int value) {
            buf.writeInt(value);
        }
    }
}
