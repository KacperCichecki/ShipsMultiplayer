package main.java.controller;

import main.java.model.Field;
import main.java.model.State;
import main.java.model.XY;

public class CommunicationService {
    RequestServis requestServis = RequestServis.INSTANCE;

    public final static CommunicationService INSTANCE = new CommunicationService();

    private CommunicationService() {
    }

    public State hitAndReurnState(XY xy) {

        int x = xy.getX();
        int y = xy.getY();

        requestServis.sendRequest("hit;" + x + y);
        String answer = requestServis.getRequest();

        if("hit".equals(answer)){
            return State.ENEMYHIT;
        }else {
            return State.MISSED;
        }

    }
}

