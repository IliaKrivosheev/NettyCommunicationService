package com.netty.communication.environment;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * Component responsible for managing the root paths for the client and server environments.
 * This class reads the root paths from the application properties and ensures that the directories
 * exist on the file system, creating them if necessary.
 */
@Component
@Slf4j
@Getter
@Accessors(fluent = true)
public class RootPathEnvironment {
    @Value("${file.client.root}")
    private String clientRootPath;

    @Value("${file.server.root}")
    private String serverRootPath;

    /**
     * Method that runs after the bean has been constructed. It validates the root paths
     * and ensures the directories exist by creating them if they do not.
     *
     * @throws IOException if there is an error creating the directories
     */
    @PostConstruct
    public void configure() throws IOException {
        if (StringUtils.isBlank(clientRootPath)) {
            throw new RuntimeException("You must set the client side root directory." +
                    " Like that: -Dfile.client.root=/some/path");
        }
        if (StringUtils.isBlank(serverRootPath)) {
            throw new RuntimeException("You must set the server side root directory." +
                    " Like that: -Dfile.server.root=/some/path");
        }

        // Ensure the client and server root directories exist, creating them if necessary
        FileUtils.forceMkdir(new File(clientRootPath));
        FileUtils.forceMkdir(new File(serverRootPath));
    }
}
