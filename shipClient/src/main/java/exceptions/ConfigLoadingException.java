package exceptions;

public class ConfigLoadingException extends RuntimeException {

    public ConfigLoadingException(Throwable cause) {
        super(cause);
    }
}