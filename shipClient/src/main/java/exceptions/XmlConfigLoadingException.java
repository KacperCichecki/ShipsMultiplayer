package exceptions;

// źle odczytane dane bądż nie odczytane dane
public class XmlConfigLoadingException extends RuntimeException {

    public XmlConfigLoadingException(Throwable cause) {
        super(cause);
    }

    public XmlConfigLoadingException(String msg) {
        super(msg);
    }
}