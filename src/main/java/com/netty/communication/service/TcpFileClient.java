package com.netty.communication.service;

import com.netty.communication.pipeline.PipelineFactory;
import com.netty.communication.dto.FileDownloadDto;
import com.netty.communication.dto.FileUploadDto;
import com.netty.communication.eventloop.ClientEventLoopGroupManager;
import com.netty.communication.handler.duplex.RequestResultChecker;
import com.netty.communication.message.UserFileDownloadRequest;
import com.netty.communication.message.UserFileUploadRequest;
import com.netty.communication.message.UserRequest;
import com.netty.communication.tcp.client.DefaultTcpClient;
import com.netty.communication.tcp.client.TcpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Implementation of the FileClient interface for handling file upload and download operations over TCP.
 */
@Component
public class TcpFileClient implements FileClient {

    private final ClientEventLoopGroupManager eventLoopGroupManager;
    private final PipelineFactory pipelineFactory;

    /**
     * Constructs a TcpFileClient with the specified event loop group manager and pipeline factory.
     *
     * @param eventLoopGroupManager the manager for client event loop groups
     * @param pipelineFactory       the factory for creating TCP channel pipelines
     */
    @Autowired
    public TcpFileClient(ClientEventLoopGroupManager eventLoopGroupManager,
                         @Qualifier("tcpFileClientPipelineFactory") PipelineFactory pipelineFactory) {
        this.eventLoopGroupManager = eventLoopGroupManager;
        this.pipelineFactory = pipelineFactory;
    }

    /**
     * Downloads a file using the specified file download specifications.
     *
     * @param spec the file download specifications
     * @throws ExecutionException if the operation fails due to an execution error
     * @throws InterruptedException if the operation is interrupted
     * @throws TimeoutException if the operation times out
     */
    @Override
    public void downloadFile(FileDownloadDto spec) throws ExecutionException, InterruptedException, TimeoutException {
        var downloadRequest = UserFileDownloadRequest.builder()
                .srcFile(spec.source().file())
                .dstFile(spec.destination().file())
                .build();
        requestTemplate(downloadRequest, spec.source().ip(), spec.source().port());
    }

    /**
     * Uploads a file using the specified file upload specifications.
     *
     * @param spec the file upload specifications
     * @throws ExecutionException if the operation fails due to an execution error
     * @throws InterruptedException if the operation is interrupted
     * @throws TimeoutException if the operation times out
     */
    @Override
    public void uploadFile(FileUploadDto spec) throws ExecutionException, InterruptedException, TimeoutException {
        var uploadRequest = UserFileUploadRequest.builder()
                .srcFile(spec.source().file())
                .dstFile(spec.destination().file())
                .build();
        requestTemplate(uploadRequest, spec.destination().ip(), spec.destination().port());
    }

    /**
     * Template method for sending a request over TCP and waiting for a response.
     *
     * @param request the user request to be sent
     * @param ip      the IP address of the destination
     * @param port    the port number of the destination
     * @throws ExecutionException if the operation fails due to an execution error
     * @throws InterruptedException if the operation is interrupted
     * @throws TimeoutException if the operation times out
     */
    private void requestTemplate(UserRequest request, String ip, int port) throws ExecutionException, InterruptedException, TimeoutException {
        var pipelineFactory = this.pipelineFactory.get();

        TcpClient tcpClient = new DefaultTcpClient();
        tcpClient.init(eventLoopGroupManager.channelIo(), pipelineFactory);
        tcpClient.connect(ip, port).get();

        var future = tcpClient.pipeline().get(RequestResultChecker.class).completableFuture();

        tcpClient.send(request).addListener(f -> {
            if (!f.isSuccess()) {
                future.completeExceptionally(f.cause());
            }
        });

        try {
            future.get();
        } finally {
            tcpClient.disconnect();
        }
    }
}
