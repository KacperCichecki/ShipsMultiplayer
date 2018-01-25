package exceptions;
// błędy związane z logiką gry
public class GameplayException extends RuntimeException {

    public GameplayException(String msg) {
        super(msg);
    }

    public GameplayException(Throwable cause) {
        super(cause);
    }
}