package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    // Define the port on which the server is listening
    public static final int PORT = 8100;


    public GameServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!serverSocket.isClosed()) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                // Execute the client's request in a new thread
                new ClientThread(serverSocket, socket).start();
            }
        } catch (IOException e) {
            if (e.getMessage().equals("socket closed"))
                System.out.println("Server socket closed");
            else
                System.err.println(e.toString());
        }
    }


}
