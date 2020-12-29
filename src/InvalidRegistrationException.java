public class InvalidRegistrationException extends Exception{

    /**
     * Creates a new instance of InvalidRegistrationException without detail message.
     */

    public InvalidRegistrationException() {}

    /**
     * Constructs a new instance of InvalidRegistrationException with the specified message.
     *
     * @param msg - the detail message.
     */

    public InvalidRegistrationException(String msg) {
        super(msg);
    }
}
