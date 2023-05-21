package main.exception;

public class InvalidURIException extends RuntimeException {
    public InvalidURIException(Throwable cause) {
        super("The provided String does not form a valid URI.", cause);
    }
}
