package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {

    private String name; // TODO
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private static String type;
    private Roles role;
    private boolean isSilent;
    private boolean isAwake;
    private int health;
    private boolean isLoggedIn;


    public Client(int port) {
        isSilent = false;
        isAwake = false;
        isLoggedIn = false;

        try {

            this.socket = new Socket("localhost", port);
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());


        } catch (IOException e) {
            e.printStackTrace();
            // TODO handling when a client tries to connect server when it is not accepting
        }

    }

    private void introduction() { // TODO implement this method
        receiveMessage();
        if (Roles.isMafia(role)) {
            receiveMessage();
        }
        if(role == Roles.Mayor)
            receiveMessage();
    }

    private void dayChatroom(){

        isAwake = true;

        receiveMessage();

        if(!canChat()){
            receiveMessage();
        }

        // create a new thread for only read
        Thread readOnly = new Thread(new ClientReadOnly(this.input));
        readOnly.start();

        while(readOnly.isAlive()){
            Message message = sendMessage();
            if(message.getText().equals("ready"))
                break;
        }



    }

    private boolean canChat(){
        return !isSilent && isLoggedIn && isAwake;
    }

    public void startClient() {

        // enter the name
        enterName();
        // get ready
        ready();
        // receiving role
        assignRole(); // TODO probably you should assign the behaviour field in the future
        // introduction night
        introduction();
        // start the day
        // TODO as i said in the clientHandler, this parts should be in a loop i guess
        dayChatroom();


        /*try {
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

        }*/
    }

    private Roles receiveRole() {
        Roles role = null;
        try {
            role = (Roles) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return role;
    }

    private void assignRole() {
        // TODO have to assign a behaviour i guess
        this.role = receiveRole();
        if(role == Roles.Diehard)
            health = 2;
        receiveMessage();
    }

    private Message receiveMessage() {
        Message message = null;
        try {
            message = (Message) input.readObject();
            System.out.println(message.getName() + " : " + message.getText());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }


    private Message sendMessage() {
        Message message = null;
        try {
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            message = new Message(name, s);
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }


    private void ready() {
        while (true) {
            receiveMessage();
            Message m = sendMessage();
            receiveMessage();
            if (m.getText().equals("ready")) {
                break;
            }
        }
    }

    private void enterName() {
        while (true) {
            receiveMessage();
            Message m = sendMessage();
            Message message = receiveMessage();
            if (message.getName().equals("God") &&
                    message.getText().equals("Done")) {

                this.name = m.getText();
                break;
            }
        }
        isLoggedIn = true;
    }


    public static void main(String[] args) {
        System.out.print("Please enter the port : ");
        Scanner in = new Scanner(System.in);
        int port = in.nextInt();
        Client client = new Client(port);
        client.startClient();
    }
}
