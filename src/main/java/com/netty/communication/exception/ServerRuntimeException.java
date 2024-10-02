package com.netty.communication.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * Exception thrown when a runtime error occurs on the server.
 * This runtime exception includes an error number and a message to provide more context
 * about the error condition.
 */
@Getter
public class ServerRuntimeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1375832192051626471L;

    private final Integer errorNo;

    public ServerRuntimeException(Integer errorNo, String message) {
        super(message);
        this.errorNo = errorNo;
    }
}
