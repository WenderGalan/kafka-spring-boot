package io.github.wendergalan.uploadfiles.exceptions;

/**
 * The type File storage exception.
 */
public class FileStorageException extends RuntimeException {

    /**
     * Instantiates a new File storage exception.
     *
     * @param message the message
     */
    public FileStorageException(String message) {
        super(message);
    }

    /**
     * Instantiates a new File storage exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
