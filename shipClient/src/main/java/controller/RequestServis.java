package main.java.controller;

import main.java.model.Field;
import main.java.model.State;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RequestServis {

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
            System.out.println("can't establish connection");
        }
        System.out.println("Started client  socket at " + socket.getLocalSocketAddress());
    }

    public void sendText(String message) {

        try {
            socketWriter.write(message);
            socketWriter.write("\n");
            socketWriter.flush();
            System.out.println("Message sent: " + message);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't write message");
        }
    }

    public String getAnswer(){
        String response = null;
        System.out.println("Start getting answer");
        while (response == null || "".equals(response)) {
            try {
                response = socketReader.readLine();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Can't read message");
            }
        }
        System.out.println("Got answer: " + response);
        return response;
    }

    public String getHitData(){
        String response = null;
        System.out.println("Start getting info which field enemy is hitting");
        while (response == null) {
            try {
                response = socketReader.readLine();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Can't read message");
            }
        }
        System.out.println("Got hit data from enemy: " + response);
        return response;
    }


    public String getfirstResponse() throws InterruptedException {

        System.out.println("Start getting first response");
        StringBuilder result = new StringBuilder();
        boolean interrupted = false;
        int chr = -1;

            try {
                do {
                    if (socketReader.ready()) chr = socketReader.read();
                    if (chr > -1) result.append((char) chr);
                    interrupted = Thread.interrupted(); // resets flag, call only once
                    System.out.println("reading char: " + chr);
                    Thread.sleep(100);
                } while (!interrupted && result.length() < 6);

                if (interrupted) {
                    System.out.println("initialThread interrupted");
                    throw new InterruptedException("initialThread interrupted");
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Can't read message");
            }
        System.out.println("returning result of first response: " + result.toString());
        return result.toString();
    }
}
