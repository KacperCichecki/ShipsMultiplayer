package config;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

    private static final Messages instance = new Messages();

    private final ResourceBundle resourceBundle;

    private Messages() {
        // jeśli chcemy po polsku to trzeba to odkomentować:
        Locale.setDefault(new Locale("pl", "PL"));
        resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        // jeśli chcemy po angielsku to wystarczy to odkomentować i zakomentować to na górze
        //resourceBundle = ResourceBundle.getBundle("messages", Locale.US);
    }

    public static String getMessage(String messageIdentifier) {
        return instance.resourceBundle.getString(messageIdentifier);
    }
}
