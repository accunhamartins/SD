public class InvalidLoginExcpetion extends Exception{
    /**
     * Creates an instance of InvalidLoginException without detail message
     */
    public InvalidLoginExcpetion() {}

    /**
     * Constructs an instance of InvalidLoginException with the specified message
     *
     * @param msg - the detail message
     */
    public InvalidLoginExcpetion(String msg) {
        super(msg);
    }
}
