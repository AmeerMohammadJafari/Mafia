package com.company;

import javax.management.relation.Role;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

public class ClientHandler extends Thread {


    private static Vector<ClientHandler> clients;
    private static int numberOfClients;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String clientName;
    private boolean isAwake;
    private boolean isReady;
    private Roles role;


    public ClientHandler(Socket socket, Vector<ClientHandler> clientHandlers, int numberOfClients) {

        ClientHandler.numberOfClients = numberOfClients;
        this.isReady = false;

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            clients = clientHandlers;
        } catch (IOException e) {

        }
    }


    private void enterName() {
        while (true) {
            sendMessage(new Message("God", "Please enter your clientName"));
            Message message = receiveMessage();
            int flag = 1;
            for (ClientHandler c : clients) {
                if (c.getName().equals(message.getText())) {
                    sendMessage(new Message("God", "The clientName you entered is chosen by" +
                            " another client"));
                    flag = 0;
                    break;
                }
            }
            if (flag == 1) {
                sendMessage(new Message("God", "Done"));
                clientName = message.getText();
                clients.add(this);
                break;
            }
        }
    }


    private void ready() {
        while (true) {
            sendMessage(new Message("God", "Please enter" +
                    " (ready) if you are ready to start the game"));
            Message message = receiveMessage();
            if (message.getText().equals("ready")) {

                sendMessage(new Message("God", "Done"));
                isReady = true;
                break;

            } else {
                sendMessage(new Message("God", " :|"));
            }
        }

        // if all the clients are ready break
        while (true) {
            if (allReady()) {
                break;
            }
        }


    }


    // TODO this method should do the relevant task according to the message

    private void handleMessage(Message message) throws IOException {
        // send to all
        for (ClientHandler c : clients) {
            if (c.equals(this))
                continue;
            c.getOutput().writeObject(message);
        }
    }

    public static boolean allReady() {

        if (clients.size() < numberOfClients) {
            return false;
        }

        for (ClientHandler c : clients) {
            if (!c.isReady) {
                return false;
            }
        }
        return true;
    }


    public void setRole(Roles role) {
        this.role = role;
    }

    private void sendMessage(Message message) {
        try {
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Message receiveMessage() {
        Message message = null;
        try {
            message = (Message) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }


    private void sendRole() {
        try {
            output.writeObject(this.role);
            output.writeObject(new Message("God", "You are " + this.role.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitClientHandler() {
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Roles getRole() {
        return role;
    }

    public String getClientName() {
        return clientName;
    }

    private void introduce() {
        try {
            Thread.sleep(2000);
            sendMessage(new Message("God", "The introduction night starts."));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String text = "";
        // if the client is mafia
        if (Roles.isMafia(role)) {
            for (ClientHandler c : clients) {
                if (c != this && Roles.isMafia(c.getRole())) {
                    text += c.getClientName() + " is " + c.getRole() + '\n';
                }
            }
            sendMessage(new Message("God", text));
        }

        // if client is mayor
        if (role == Roles.Mayor) {
            for (ClientHandler c : clients) {
                if (c.getRole() == Roles.Doctor) {
                    sendMessage(new Message("God", c.getClientName() + " is " + c.getRole()));
                    break;
                }
            }
        }
    }

    @Override
    public void run() {

        enterName();
        ready();
        // waiting for setting roles by game
        waitClientHandler();
        // now a message should be sent to the clients and also the role of each client as an object
        sendRole();
        // the introduction night
        introduce();
        System.out.println("out of the introduction");
        // waiting for next step in the game


        // this part is for chatroom between player and should be a method i guess
        /*try {
            while (true) {
                Message message = (Message) input.readObject();
                handleMessage(message);
            }
        } catch (IOException | ClassNotFoundException e) {

        }*/
    }

    public ObjectOutputStream getOutput() {
        return output;
    }
}