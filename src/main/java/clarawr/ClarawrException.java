package clarawr;

/**
 * Represents a custom exception for the Clarawr application.
 * This exception can be thrown to indicate specific errors within the application.
 */
public class ClarawrException extends Exception {

    /**
     * Constructs a new ClarawrException with the specified detail message.
     *
     * @param message The detail message, which provides more information about the error.
     */
    public ClarawrException(String message) {
        super(message);
    }
}
