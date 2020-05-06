package com.company;

import com.company.game.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class ClientThread extends Thread {
    private final ServerSocket serverSocket;
    private final Socket socket;
    Player player;

    //Pentru a putea inchide serverSocketul transmitem serverSocketul ca parametru
    public ClientThread(ServerSocket serverSocket, Socket socket) {
        this.serverSocket = serverSocket;
        this.socket = socket;
    }

    public void closeServerSocket() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (Throwable ignored) {
            }
        }
    }

    public void run() {
        try {
            player = new Player();
            label:
            while (true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());

                // Get the request from the input stream: client → server
                String request = in.readLine();

                // Send the response to the oputput stream: server → client

                // Cazuri de inchidere conexiune
                switch (request) {
                    case "exit":
                        // atunci clientul s-a inchis deci nu mai trimitem niciun response
                        // si iesim din bucla while(true)

                        break label;
                    case "stop":
                        // incidem serverSocketul si trimitem mesajul aferent
                        // si iesim din bucla while(true)
                        closeServerSocket();
                        out.println("Server stopped");
                        out.flush();
                        break label;
                }
                // alfel trimitem confirmarea primiri mesajului si continuam bucla while(true)
                out.println("Server received the request ... " + request);
                out.flush();

                // Cazuri create game, load game, submit move
                switch (request) {
                    case "create game":
                        out.println(player.createGame());
                        out.flush();
                        break;
                    case "join game":
                        out.println(player.joinGame());
                        out.flush();
                        break;
                    case "board":
                        if (player.gameIsActive()) {
                            out.println("1");
                            out.print(player.getBoardGame().toString());
                        } else out.println("0");
                        out.flush();
                    default:
                        if (request.contains("submit move")) {
                            int i = request.charAt(12) - '0';
                            int j = request.charAt(14) - '0';
                            int submitMoveCase = player.submitMove(i, j);
                            out.println(submitMoveCase);
                            out.flush();
                            // case 0, 1, 2, 3
                            if (submitMoveCase >= 0)
                                out.print(player.getBoardGame().toString());
                            if(submitMoveCase >=2)
                                player.setGameNull();
                            out.flush();
                        }
                        break;
                }
            }
            System.out.println("Client: end!");

        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) {
                System.err.println(e.toString());
            }
        }
    }
}
