package exceptions;

// nieprawidłowe dane odczytane z api dataservice.accuweather.com
public class ApiException extends RuntimeException {

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}