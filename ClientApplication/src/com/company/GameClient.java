package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class GameClient {


    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String getValidRequestFromKeyboard() throws IOException {
        // reading data using readLine
        String request;
        while (true) {
            request = reader.readLine();
            switch (request) {
                case "exit":
                case "stop":
                case "create game":
                case "join game":
                case "board":
                    return request;
            }
            if (request.contains("submit move"))
                if (request.length() == 15)
                    if (request.charAt(11) == ' ' && request.charAt(13) == ' ')
                        if (request.charAt(12) >= '0' && request.charAt(12) <= '9')
                            if (request.charAt(14) >= '0' && request.charAt(14) <= '9')
                                return request;
            System.out.println("Wrong input");
        }
    }


    public GameClient() {
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 8100; // The server's port
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))) {
            while (true) {
                String request = getValidRequestFromKeyboard();

                // Send a request to the server
                out.println(request);

                // iesim din bucla while(true) ceea ce va duce la inchiderea clientului
                if (request.equals("exit"))
                    break;

                // altfel:
                // Wait the response from the server (Server received the request)
                String response = in.readLine();
                System.out.println(response);

                //iesim din bucla while(true)
                if (request.equals("stop"))
                    break;


                if (request.equals("create game")) {
                    response = in.readLine();
                    switch (response) {
                        case "-1":
                            System.out.println("You are already in a game");
                            break;
                        case "1":
                            System.out.println("game created");
                            break;
                    }
                }
                if (request.equals("join game")) {
                    response = in.readLine();
                    switch (response) {
                        case "-1":
                            System.out.println("You are already in a game");
                            break;
                        case "0":
                            System.out.println("no game found");
                            break;
                        case "1":
                            System.out.println("game found");
                            break;
                    }
                }
                if (request.equals("board"))
                {
                    response = in.readLine();
                    if (response.equals("1"))
                        for (int i = 0; i < 10; i++)
                            System.out.println(in.readLine());
                    else
                        System.out.println("inactive game");
                }

                if (request.contains("submit move")) {
                    response = in.readLine();

                    if (response.charAt(0) >= '0')
                        for (int i = 0; i < 10; i++)
                            System.out.println(in.readLine());

                    switch (response) {
                        case "-3":
                            System.out.println("but you are not in any game");
                            break;
                        case "-2":
                            System.out.println("but second player not found yet");
                            break;
                        case "-1":
                            System.out.println("but is not your turn yet");
                            break;
                        case "0":
                            System.out.println("invalid move");
                            break;
                        case "1":
                            System.out.println("valid move");
                            break;
                        case "2":
                            System.out.println("you lost");
                            break;
                        case "3":
                            System.out.println("you won");
                            break;
                    }
                }
            }
            System.out.println("App closed!");
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
