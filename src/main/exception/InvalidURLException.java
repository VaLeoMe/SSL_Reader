package main.exception;

public class InvalidURLException extends RuntimeException {
    public InvalidURLException(Throwable cause) {
        super("The provided String does not form a valid URL.", cause);
    }
}
