package config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigTest {

    @BeforeEach
    void setUp() {
        Config.load();
    }

    @Test
    void getProperServerPort() {

        assertEquals(2002, Config.serverPort());

    }
}