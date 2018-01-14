package config;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

    private static final Messages instance = new Messages();

    private final ResourceBundle resourceBundle;

    private Messages() {
        //todo 3. internacjonalizacja - wczytanie wiadomosci dla konkretnego kraju
        //jak chcesz zmienic to zamiast Locale.getDefault daj Locale.US
        //jak mu brakuje pliku dla jakegos jezyka to bierze to co by wzial jako default
        resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    }

    public static String getMessage(String messageIdentifier) {
        return instance.resourceBundle.getString(messageIdentifier);
    }
}
