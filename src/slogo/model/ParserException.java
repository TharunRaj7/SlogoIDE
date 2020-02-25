package slogo.model;

public class ParserException extends RuntimeException {

    public ParserException(String message) { super(message); }
    /**
     * Throws generic exception error, maintaining the previous error log
     * @param e
     */
    public ParserException(Throwable e) { super(e); }

    /**
     * Does the same as the other constructor, but has a custom message
     * @param e
     * @param message
     */
    public ParserException(Throwable e, String message) { super(message, e); }
}
