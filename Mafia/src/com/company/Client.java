package com.company;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is used for the Client side program
 */
public class Client {

    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;


    /**
     * Instantiates a new Client.
     *
     * @param port the port
     */
    public Client(int port) {

        try {

            this.socket = new Socket("localhost", port);
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());


        } catch (IOException e) {
            e.printStackTrace();
            // TODO handling when a client tries to connect server when it is not accepting
        }

    }

    private void showMessage(Message message){
        System.out.println(message.getName() + " : " + message.getText());
    }


    private Message receiveMessage() {
        Message message = null;
        try {
            message = (Message) input.readObject();
        } catch (EOFException e) {
            System.exit(0);
        } catch (IOException | ClassNotFoundException | ClassCastException e) {

        }
        return message;
    }

    private Message sendMessage() {
        Message message = null;
        try {
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            message = new Message(s);
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    private void justSendMessage() {
        try {
            output.writeObject(new Message(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void startClient() {


        class GetMessageOnly extends Thread {
            @Override
            public void run() {
                    while (true) {
                        try {
                            Message message = receiveMessage();

                            if (message.getName().equals("God") && message.getText().equals("enter")) {
                                justSendMessage();
                            } else {
                                showMessage(message);
                            }
                        }catch (NullPointerException e){

                        }
                    }
            }
        }

        class SendMessageOnly extends Thread {
            @Override
            public void run() {
                while (true) {
                    Message message = sendMessage();
                }
            }
        }

        Thread readOnly = new GetMessageOnly();
        Thread sendOnly = new SendMessageOnly();
        readOnly.start();
        sendOnly.start();
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        int port;
        while(true) {
            try {
                System.out.print("Please enter the port : ");
                Scanner in = new Scanner(System.in);
                port = in.nextInt();
                break;
            }catch (InputMismatchException e){
                System.out.println("Invalid input");
            }
        }
        Client client = new Client(port);
        client.startClient();
    }
}
