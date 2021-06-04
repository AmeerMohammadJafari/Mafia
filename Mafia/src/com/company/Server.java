package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public void startServer() {
        try {

            while (true) { // TODO until num
                Socket s = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(s, clients);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {

        }

    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter the port : ");
        int port = in.nextInt();
        System.out.print("Please enter the number of players : ");
        int num = in.nextInt();
        Server server = new Server(port, num);
        server.startServer();
    }
}
