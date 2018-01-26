package config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigTest {


    @Test
    void getProperServerPort() {

        assertEquals(2002, Config.serverPort());

    }
}