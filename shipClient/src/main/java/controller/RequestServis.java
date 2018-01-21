package controller;

import model.Field;
import model.State;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RequestServis {

    private static final Logger logger = LogManager.getLogger();

    private Socket socket;
    private BufferedReader socketReader;
    private BufferedWriter socketWriter;

    // singletone
    public final static RequestServis INSTANCE = new RequestServis();

    private RequestServis() {

        socket = new Socket();
        //todo propertisy
        try {
            socket.bind(new InetSocketAddress("localhost",  7777));
            socket.connect(new InetSocketAddress("localhost",  2002));
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("can't establish connection");
        }
        logger.info("Started client  socket at " + socket.getLocalSocketAddress());
    }

    public void sendText(String message) {

        try {
            socketWriter.write(message);
            socketWriter.write("\n");
            socketWriter.flush();
            logger.info("Message sent: " + message);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Can't write message" + message);
        }
    }

    public String getAnswer(){
        String response = null;
        logger.info("Start getting answer");
        while (response == null || "".equals(response)) {
            try {
                response = socketReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Can't get answer");
            }
        }
        logger.info("Got answer: " + response);
        return response;
    }

    public String getHitData(){
        String response = null;
        logger.info("Start getting info which field enemy is hitting");
        while (response == null) {
            try {
                response = socketReader.readLine();

            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Can't read which field enemy is hitting");
            }
        }
        logger.info("Got hit data from enemy: " + response);
        return response;
    }


    public String getfirstResponse() throws InterruptedException {

        logger.info("Start getting first response");
        StringBuilder result = new StringBuilder();
        boolean interrupted = false;
        int chr = -1;

            try {
                do {
                    if (socketReader.ready()) chr = socketReader.read();
                    if (chr > -1) result.append((char) chr);
                    interrupted = Thread.interrupted(); // resets flag, call only once
                    //System.out.println("reading char: " + chr);
                    Thread.sleep(100);
                } while (!interrupted && result.length() < 6);

                if (interrupted) {
                    logger.warn("initialThread interrupted");
                    throw new InterruptedException("initialThread interrupted");
                }

            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Can't read message");
            }
        logger.info("returning result of first response: " + result.toString());
        return result.toString();
    }
}
