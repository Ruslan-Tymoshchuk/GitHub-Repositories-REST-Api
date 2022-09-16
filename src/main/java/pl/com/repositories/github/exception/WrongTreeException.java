package pl.com.repositories.github.exception;

@SuppressWarnings("serial")
public class WrongTreeException extends RuntimeException {

    public WrongTreeException(String message) {
        super(message);
      }
}