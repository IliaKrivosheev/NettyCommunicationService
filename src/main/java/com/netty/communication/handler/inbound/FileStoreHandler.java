package com.netty.communication.handler.inbound;

import com.netty.communication.message.ChunkTransferOk;
import com.netty.communication.message.InboundFileChunk;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

/**
 * Handler for processing inbound file chunks and storing them in the specified directory.
 */
@RequiredArgsConstructor
@Getter
public class FileStoreHandler extends DedicatedSimpleInboundHandler<InboundFileChunk> {
    private final String rootPath;

    /**
     * Processes the received inbound file chunk, stores it in the specified path,
     * and sends an acknowledgment upon successful storage.
     *
     * @param ctx   the ChannelHandlerContext which provides various operations on the channel
     * @param chunk the InboundFileChunk to be processed and stored
     * @throws Exception if an error occurs during processing or storage
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InboundFileChunk chunk) throws Exception {
        var targetPath = Path.of(rootPath, chunk.storePath()).normalize().toString();
        FileStoreAction.store(chunk, targetPath);
        ctx.writeAndFlush(ChunkTransferOk.builder().build());
    }
}
