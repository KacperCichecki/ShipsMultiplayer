package main.java.controller;

import main.java.model.Field;
import main.java.model.State;

public class RequestController {

    public void sendRequest(String request) {

    }

    public String getRequest(){
        return "TestResponse";
    }

    public static State hitAndGettState(Field enemyField) {

        //strzel w pole o takich współrzędnych jak te w enemyFiel i sprawdź jaką dostaniesz odpowedź
        // w zależności od odpowiedzi zwróć odpowieni stan

        return State.HIT;
    }
}
