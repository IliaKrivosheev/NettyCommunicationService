package com.netty.communication.advice;

import com.netty.communication.exception.ServerRuntimeException;
import com.netty.communication.specification.response.ResponseSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.ExecutionException;

/**
 * Global exception handler for web controllers, designed to capture and process all exceptions
 * within the "com.competitive.controller" package. It logs the error details and returns a
 * custom response based on the type of exception.
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.competitive.controller")
public class WebControllerExceptionAdvisor {

    /**
     * Centralized method to handle exceptions thrown within web controllers.
     *
     * @param exception the caught exception
     * @return a {@link ResponseEntity} containing the error status and message
     */
    @ExceptionHandler
    public ResponseEntity<String> handleException(Throwable exception) {
        log.error("Exception caught: {}", exception.getMessage(), exception);
        var unwrappedException = unwrap(exception);
        var responseSpec = matchSpec(unwrappedException);
        return buildResponseEntity(responseSpec);
    }

    /**
     * Unwraps the original cause of an exception, particularly for {@link ExecutionException}.
     * If the exception is of type {@link ExecutionException}, returns its cause.
     * Otherwise, returns the original exception.
     *
     * @param exception the thrown exception
     * @return the root cause of the exception
     */
    private static Throwable unwrap(Throwable exception) {
        return (exception instanceof ExecutionException) ? exception.getCause() : exception;
    }

    /**
     * Matches the type of exception to a corresponding {@link ResponseSpec} object,
     * which contains response status and error details.
     *
     * @param exception the unwrapped exception
     * @return the matched {@link ResponseSpec} based on the exception type
     */
    private static ResponseSpec matchSpec(Throwable exception) {
        if (exception instanceof ServerRuntimeException serverRuntimeException) {
            return ResponseSpec.match(serverRuntimeException.getErrorNo());
        }
        return ResponseSpec.match(exception);
    }

    /**
     * Builds a {@link ResponseEntity} based on the {@link ResponseSpec} provided.
     *
     * @param responseSpec the specification containing response details
     * @return a properly formatted {@link ResponseEntity} with HTTP status and headers
     */
    private static ResponseEntity<String> buildResponseEntity(ResponseSpec responseSpec) {
        return ResponseEntity.status(responseSpec.getHttpResponseStatus())
                .header("Error-No", responseSpec.getErrorNo().toString())
                .header("Error-Message", responseSpec.getErrorMessage())
                .build();
    }
}
