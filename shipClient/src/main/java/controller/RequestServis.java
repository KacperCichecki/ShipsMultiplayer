package main.java.controller;

import main.java.model.Field;
import main.java.model.State;

public class RequestServis {

    public final static RequestServis INSTANCE = new RequestServis();
    private RequestServis() {
    }

    public void sendRequest(String request) {

    }

    public String getRequest(){
        return "TestResponse";
    }

}
