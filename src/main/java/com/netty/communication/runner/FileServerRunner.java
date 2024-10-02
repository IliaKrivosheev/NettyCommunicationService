package com.netty.communication.runner;

import com.netty.communication.service.FilerServer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * A Spring component responsible for starting the file server on application startup.
 */
@Component
@RequiredArgsConstructor
public class FileServerRunner {

    @Value("${file.server.port}")
    private Integer port;

    private final FilerServer server;

    /**
     * Starts the file server after the bean is constructed.
     *
     * @throws ExecutionException if the server fails to start due to an execution error
     * @throws InterruptedException if the server starting process is interrupted
     * @throws IOException if an I/O error occurs while starting the server
     */
    @PostConstruct
    protected void start() throws ExecutionException, InterruptedException, IOException {
        server.start(port);
    }
}
