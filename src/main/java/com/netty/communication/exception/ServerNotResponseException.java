package com.netty.communication.exception;

import java.io.Serial;

/**
 * Exception thrown when the server is not responding to requests.
 * This runtime exception indicates that a server communication issue has occurred.
 */
public class ServerNotResponseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1559816505620023965L;

    public ServerNotResponseException() {
        super("The server is not responding.");
    }
}
