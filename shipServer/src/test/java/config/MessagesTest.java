package config;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessagesTest {


    @Test
    void getNumberOfGames() {
        assertTrue(!Messages.getMessage("server.started").isEmpty());
    }

}