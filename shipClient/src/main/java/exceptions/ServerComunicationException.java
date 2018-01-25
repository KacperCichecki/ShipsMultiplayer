package exceptions;

// niepoprawen dane zwrócoe przez server albo brak danych
public class ServerComunicationException extends RuntimeException {

    public ServerComunicationException(String msg) {
        super(msg);
    }

    public ServerComunicationException(Throwable cause) {
        super(cause);
    }
}