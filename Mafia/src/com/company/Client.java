package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private String name; // TODO
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private static String type;
    private boolean isLoggedIn;

    public Client(int port) {

        try {

            this.socket = new Socket("localhost", port);
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startClient() {

        // enter the name
        enterName();

        // make ready
        ready();

        try {
            new Thread(new ClientReadOnly(this.input)).start();
            while (true) {
                Scanner in = new Scanner(System.in);
                String text = in.nextLine();
                if (text.equals("quit")) {
                    socket.close();
                }
                Message message = new Message(name, text);
                output.writeObject(message);
            }
        } catch (IOException e) {

        }

    }

    private void ready(){
        try {
            while (true) {
                Message message = (Message) input.readObject();
                System.out.println(message.getName() + " : " + message.getText());
                Scanner in = new Scanner(System.in);
                String s = in.nextLine();
                output.writeObject(new Message(s));
                message = (Message) input.readObject();
                System.out.println(message.getName() + " : " + message.getText());
                if (s.equals("ready")) {
                    isLoggedIn = true;
                    break;
                }
            }
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void enterName() {
        try {
            while (true) {
                Message message = (Message) input.readObject();
                System.out.println(message.getName() + " : " + message.getText());
                Scanner in = new Scanner(System.in);
                String name = in.nextLine();
                output.writeObject(new Message(name));
                message = (Message) input.readObject();
                System.out.println(message.getName() + " : " + message.getText());
                if (message.getName().equals("God") &&
                        message.getText().equals("Done")) {

                    this.name = name;
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        System.out.print("Please enter the port : ");
        Scanner in = new Scanner(System.in);
        int port = in.nextInt();
        Client client = new Client(port);
        client.startClient();
    }
}
