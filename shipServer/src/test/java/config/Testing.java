package config;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Testing {


    @Test
    void general() {
        String outMsg = "some";

        String[] request = outMsg.split(";");
        System.out.println(request.length);
        System.out.println(request[0]);
    }

}