package database;

public class DatabaseConnectionException extends Throwable {
    public DatabaseConnectionException(Throwable cause) {
        super(cause);
    }

    public DatabaseConnectionException(String s) {
        super(s);
    }
}
