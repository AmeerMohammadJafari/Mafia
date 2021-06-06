package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
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
            System.out.println(i + "clients connected");
        } catch (IOException e) {

        }
    }
    // TODO refactor the method
    private void moreClientsMessage(){
        try {
            while (true) {
                Socket s = serverSocket.accept();
                ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
                output.writeObject(new Message("God", numberOfClients + " clients has connected to" +
                        " the server" +
                        "so you can not connect"));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }




    public void startServer() {

        // accepting clients
        accepting();
        // TODO refactoring the moreClientsMessage method
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
            if (num >= 8) {
                break;
            } else
                System.out.println("The number of players must be at least 8");
        }
        Server server = new Server(port, num);
        server.startServer();
    }
}
