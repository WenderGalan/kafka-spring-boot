package io.github.wendergalan.uploadfiles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type My file not found exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends RuntimeException {

    /**
     * Instantiates a new My file not found exception.
     *
     * @param message the message
     */
    public MyFileNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new My file not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public MyFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}