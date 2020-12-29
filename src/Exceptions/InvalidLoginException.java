package Exceptions;

public class InvalidLoginException extends Exception{
    /**
     * Creates an instance of InvalidLoginException without detail message
     */
    public InvalidLoginException() {}

    /**
     * Constructs an instance of InvalidLoginException with the specified message
     *
     * @param msg - the detail message
     */
    public InvalidLoginException(String msg) {
        super(msg);
    }
}
