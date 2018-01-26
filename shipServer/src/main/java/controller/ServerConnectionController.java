package controller;

import config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerConnectionController {

    private static final Logger logger = LogManager.getLogger();
    private HashMap<Integer, Socket> playersSockets = new HashMap<>();
    private int numOfPlayers = 0;

    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket(Config.serverPort(), 100,
                InetAddress.getByName(Config.serverHost()));

        System.out.println("Server started  at:  " + serverSocket);

        while (numOfPlayers < 2) {
            System.out.println("Waiting for a  connection...");
            final Socket activeSocket = serverSocket.accept();
            System.out.println("Received a  connection from  " + activeSocket);
            numOfPlayers++;

            //wyrażenie lambda (java 8)
            Runnable runnable = () -> handleClientRequest(activeSocket);

            new Thread(runnable).start(); // start a new thread
        }
        System.out.println("Both players are ready ");
    }

    private void handleClientRequest(Socket socket) {
        try{
            // obiekt czytający tekst z socketu klienta
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            playersSockets.put(socket.getPort(), socket);

            String inMsg;

            while ((inMsg = socketReader.readLine()) != null) {
                System.out.println("Received msg: [" + inMsg + "], from client: " + socket.getPort());
                // wysyła wiadomośc do drugiego klienta
                sendToPlayer(inMsg, socket.getPort());
            }
            socket.close();
            System.out.println("Closed connection");
        }catch(IOException e){
            logger.warn(e.getMessage());
            e.printStackTrace();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendToPlayer(String outMsg, int port) throws IOException {
        // szukamy potu drugiego klienta
        Integer destinationPort = playersSockets.keySet()
                .stream()
                .filter(integer -> integer != port).findFirst()
                .orElseThrow(() -> new IOException("can't find"));

        Socket socket = playersSockets.get(destinationPort);
        // obiekt do pisania do socketu
        BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(
        socket.getOutputStream()));
        socketWriter.write(outMsg);
        socketWriter.write("\n");
        socketWriter.flush();

        System.out.println("wrote message : [" + outMsg + "]to socket: " + socket);
    }
}