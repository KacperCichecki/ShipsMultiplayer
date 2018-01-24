package controller.services;

import model.State;
import model.XY;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommunicationService {
    private static final Logger logger = LogManager.getLogger();

    RequestServis requestServis = RequestServis.INSTANCE;

    public final static CommunicationService INSTANCE = new CommunicationService();

    private CommunicationService() {
    }

    public State hitAndReturnState(XY xy) {

        int x = xy.getX();
        int y = xy.getY();

        requestServis.sendText("hit;" + x + y);

        String answer = requestServis.getAnswer();

        if("hit".equals(answer)){
            return State.ENEMYHIT;
        }else if ("miss".equals(answer)) {
            return State.MISSED;
        } else {
            logger.error("got wrong anser, should be miss/hit and was: " + answer);
            throw new RuntimeException("Got wrong anser, should be miss/hit and was: " + answer);
        }
    }

    public String getHit() {
        String[] response = requestServis.getHitData().split(";");

        if("hit".equals(response[0])) {
            return response[1];
        } else {
            logger.error("Got wrong response, should be: hit, and is: " + response[0]);
            throw new RuntimeException("Got wrong response, should be: hit, and is: " + response[0]);
        }
    }

    public String getHitFirstTime() {
        String[] response;
        try {
            response = requestServis.getfirstResponse().split(";");
        } catch (InterruptedException e) {
            logger.warn("cought initial thread interruption exception");
            return "";
        }

        if("hit".equals(response[0])) {
            return response[1];
        } else {
            throw new RuntimeException("Got wrong response, should be: hit, and is: " + response[0]);
        }
    }

    public void sendAnswer(State state) {
        String answer = "";
        if (state == State.MISSED) {
            answer = "miss";
        } else if (state == State.HIT) {
            answer = "hit";
        }
        requestServis.sendText(answer);
    }
}

