package com.netty.communication.eventloop;

import com.netty.communication.specification.channel.FileServerSpec;
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
 * Manages the lifecycle of Netty's {@link EventLoopGroup} instances used for handling server-side file operations.
 * This component sets up three event loop groups: one for managing connections (boss group),
 * one for I/O operations (worker group), and one for file storage operations.
 */
@Component
@Accessors(fluent = true)
@RequiredArgsConstructor
public class ServerEventLoopGroupManager {
    private final FileServerSpec serverSpec;

    @Getter
    private EventLoopGroup boss;

    @Getter
    private EventLoopGroup channelIo;

    @Getter
    private EventLoopGroup fireStore;

    /**
     * Initializes the event loop groups based on the configuration provided by {@link FileServerSpec}.
     * One group is for managing connections, another for I/O operations, and a third for file storage operations.
     */
    @PostConstruct
    void setUp() {
        boss = new NioEventLoopGroup(serverSpec.nBossMaxThread());
        channelIo = new NioEventLoopGroup(serverSpec.nChannelIoMaxThread());
        fireStore = new DefaultEventLoopGroup(serverSpec.nFileStoreMaxThread());
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
        boss.shutdownGracefully().sync();
        fireStore.shutdownGracefully().sync();
    }
}
