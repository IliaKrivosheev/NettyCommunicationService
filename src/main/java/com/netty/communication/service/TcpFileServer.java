package com.netty.communication.service;

import com.netty.communication.eventloop.ServerEventLoopGroupManager;
import com.netty.communication.pipeline.PipelineFactory;
import com.netty.communication.tcp.server.DefaultTcpServer;
import com.netty.communication.tcp.server.TcpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 * Implementation of the FilerServer interface for managing a TCP file server.
 */
@Component
public class TcpFileServer implements FilerServer {

    private final ServerEventLoopGroupManager eventLoopGroupManager;
    private final PipelineFactory pipelineFactory;
    private final TcpServer server;

    /**
     * Constructs a TcpFileServer with the specified event loop group manager and pipeline factory.
     *
     * @param eventLoopGroupManager the manager for server event loop groups
     * @param pipelineFactory       the factory for creating TCP channel pipelines
     */
    @Autowired
    public TcpFileServer(ServerEventLoopGroupManager eventLoopGroupManager,
                         @Qualifier("tcpFileServerPipelineFactory") PipelineFactory pipelineFactory) {
        this.eventLoopGroupManager = eventLoopGroupManager;
        this.pipelineFactory = pipelineFactory;
        this.server = new DefaultTcpServer();
    }

    /**
     * Starts the TCP file server on the specified port.
     *
     * @param bindPort the port to bind the server to
     * @throws InterruptedException if the operation is interrupted
     * @throws ExecutionException if an error occurs during server startup
     */
    @Override
    public void start(int bindPort) throws InterruptedException, ExecutionException {
        server.init(eventLoopGroupManager.boss(),
                eventLoopGroupManager.channelIo(),
                pipelineFactory.get());

        server.start(bindPort).get();
    }
}
