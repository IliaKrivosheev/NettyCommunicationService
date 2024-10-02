package com.netty.communication.eventloop;

import com.netty.communication.specification.channel.FileClientSpec;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Manages the lifecycle of Netty's {@link EventLoopGroup} instances used for handling client file operations.
 * This component sets up two event loop groups: one for I/O operations and another for file storage operations.
 */
@Component
@Accessors(fluent = true)
@RequiredArgsConstructor
public class ClientEventLoopGroupManager {

    private final FileClientSpec clientSpec;

    @Getter
    private EventLoopGroup channelIo;

    @Getter
    private EventLoopGroup fireStore;

    /**
     * Initializes the event loop groups based on the configuration provided by {@link FileClientSpec}.
     * One group is initialized for handling I/O operations and the other for file storage.
     */
    @PostConstruct
    void setUp() {
        channelIo = new NioEventLoopGroup(clientSpec.nChannelIoMaxThread());
        fireStore = new DefaultEventLoopGroup(clientSpec.nFileStoreMaxThread());
    }

    /**
     * Gracefully shuts down the event loop groups when the application context is destroyed.
     * This ensures all ongoing operations are completed before the shutdown.
     *
     * @throws InterruptedException if the shutdown process is interrupted
     */
    @PreDestroy
    void tearDown() throws InterruptedException {
        channelIo.shutdownGracefully().sync();
        fireStore.shutdownGracefully().sync();
    }
}
