package com.netty.communication.specification.channel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * Provides specifications for channels, including server and client configurations.
 */
@Component
@Accessors(fluent = true)
@Getter
@RequiredArgsConstructor
public class ChannelSpecProvider {

    private final FileServerSpec server;
    private final FileClientSpec client;
    private final HeaderSpecProvider header;

    /**
     * Retrieves the specifications for the file server.
     *
     * @return the file server specifications
     */
    public FileServerSpec server() {
        return server;
    }

    /**
     * Retrieves the specifications for the file client.
     *
     * @return the file client specifications
     */
    public FileClientSpec client() {
        return client;
    }

    /**
     * Retrieves the specifications for headers.
     *
     * @return the header specifications
     */
    public HeaderSpecProvider header() {
        return header;
    }
}
