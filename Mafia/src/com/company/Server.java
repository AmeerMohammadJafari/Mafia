package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

/**
 * The Server class, just used for connecting clients and start their threads and instantiate a Game object
 * and start its thread too
 */
public class Server {

    private static Vector<ClientHandler> clients;
    private ServerSocket serverSocket;
    private int numberOfClients;

    /**
     * Instantiates a new Server.
     *
     * @param port the port
     * @param num  the num
     */
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

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        int port;
        while(true) {
            Scanner s = new Scanner(System.in);
            System.out.print("Please enter the port : ");
            try {
                port = s.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
            }
        }
        int num;
        while(true) {
            Scanner s = new Scanner(System.in);
            System.out.print("Please enter the number of players : ");
            try {
                num = s.nextInt();
                if (num >= 10) {
                    break;
                } else
                    System.out.println("The number of players must be at least 10");
            }catch (InputMismatchException e){
                System.out.println("Invalid input");
            }
        }
        Server server = new Server(port, num);
        server.startServer();
    }
}
