package controller;

import config.Config;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerConnectionController {

    static HashMap<Integer, Socket> playsersSockets = new HashMap<>();

    public static void main(String[] args) throws Exception {
        int numOfPlayers = 0;


        ServerSocket serverSocket = new ServerSocket(Config.serverPort(), 100,
                InetAddress.getByName(Config.serverHost()));
        System.out.println("Server started  at:  " + serverSocket);

        while (true && numOfPlayers < 2) {
            System.out.println("Waiting for a  connection...");
            final Socket activeSocket = serverSocket.accept();
            System.out.println("Received a  connection from  " + activeSocket);
            numOfPlayers++;
            Runnable runnable = () -> handleClientRequest(activeSocket);
            new Thread(runnable).start(); // start a new thread
        }
        System.out.println("Both players are ready ");
    }

    public static void handleClientRequest(Socket socket) {
        try{
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            playsersSockets.put(socket.getPort(), socket);

            String inMsg;

            while ((inMsg = socketReader.readLine()) != null) {
                System.out.println("Received msg: [" + inMsg + "], from client: " + socket.getPort());
                sendToPlayer(inMsg, socket.getPort());
            }
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void sendToPlayer(String outMsg, int port) throws IOException {

        Integer destinationPort = playsersSockets.keySet().stream().filter(integer -> integer != port).findFirst().get();
        Socket socket = playsersSockets.get(destinationPort);
        BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(
        socket.getOutputStream()));
        socketWriter.write(outMsg);
        socketWriter.write("\n");
        socketWriter.flush();
        System.out.println("wrote message to port: " + destinationPort);
    }
}