package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Server {

    private static Vector<ClientHandler> clients;
    private ServerSocket serverSocket;
    private int numberOfClients;

    public Server(int port, int num) {

        clients = new Vector<>();
        numberOfClients = num;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {

        }
    }

    private void accepting() {
        try {
            int i = 0;
            while (i != numberOfClients) {
                Socket s = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(s, clients, numberOfClients);
                clientHandler.start();
                i++;
            }
        } catch (IOException e) {

        }
    }



    private void startServer(){
        // accepting clients
        accepting();
        // TODO more client coming
        // moreClientsMessage();
        // create a game object
        Game game = new Game(clients, numberOfClients);
        Thread gameThread = new Thread(game);
        gameThread.start();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter the port : ");
        int port = in.nextInt();
        int num;
        while(true) {
            System.out.print("Please enter the number of players : ");
            num = in.nextInt();
            if (num >= 10) {
                break;
            } else
                System.out.println("The number of players must be at least 8");
        }
        Server server = new Server(port, num);
        server.startServer();
    }
}
