package com.netty.communication.exception;

import java.io.Serial;

/**
 * Exception thrown when an operation is attempted on a resource that is not a file.
 * This runtime exception indicates that the expected file input is invalid or missing.
 */
public class NotFileException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5961718089138605963L;

    public NotFileException(String message) {
        super(message);
    }
}
