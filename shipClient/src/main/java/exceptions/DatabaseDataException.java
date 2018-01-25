package exceptions;

// błędne dane zapisane/odczytane bazy
public class DatabaseDataException extends RuntimeException {

    public DatabaseDataException(Throwable cause) {
        super(cause);
    }

    public DatabaseDataException(String msg) {
        super(msg);
    }
}
