package com.company;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;


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


    private Message receiveMessage() {
        Message message = null;
        try {
            message = (Message) input.readObject();
            System.out.println(message.getName() + " : " + message.getText());
        } catch (EOFException e){
            System.exit(0);
        }
        catch (IOException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
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
                    Message message = receiveMessage();
                    if (message.getName().equals("God") &&
                            (message.getText().equals("The chat is over.") ||
                                    message.getText().equals("The vote is over.") ||
                                    message.getText().equals("MayorTime ends."))) {

                        justSendMessage();
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


    public static void main(String[] args) {
        System.out.print("Please enter the port : ");
        Scanner in = new Scanner(System.in);
        int port = in.nextInt();
        Client client = new Client(port);
        client.startClient();
    }
}
