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
    private boolean isReady; // use at the beginning of the game and for chat ready
    private Roles role;
    private boolean isSilent; // TODO this field can be set by psycho either when a client just left and want
    // just watch
    // declare some booleans for time
    private boolean dayChat;
    private int health;
    private boolean isLoggedIn;


    public ClientHandler(Socket socket, Vector<ClientHandler> clientHandlers, int numberOfClients) {

        ClientHandler.numberOfClients = numberOfClients;
        this.isReady = false;
        this.isSilent = false;
        this.dayChat = false;
        this.isAwake = false;
        this.isLoggedIn = false;

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            clients = clientHandlers;
        } catch (IOException e) {

        }
    }

    public void setHealth(int health) {
        this.health = health;
    }

    private void enterName() {
        while (true) {
            sendMessage(new Message("God", "Please enter your name"));
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
        this.isLoggedIn = true;
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

    /*private void handleMessage(Message message) throws IOException {
        // send to all
        for (ClientHandler c : clients) {
            if (c.equals(this))
                continue;
            c.getOutput().writeObject(message);
        }
    }*/

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
            sendMessage(new Message("God", "The introduction night starts."));
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String text = "";
        // if the client is mafia
        if (Roles.isMafia(role)) {
            for (ClientHandler c : clients) {
                if (c != this && Roles.isMafia(c.getRole())) {
                    text += c.getClientName() + " is " + c.getRole() + ". ";
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




        sendMessage(new Message("God", "The night is over"));
    }

    private void dayChatroom() {
        System.out.println("Day chat is starting.");

        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }


        // make all states not ready again for using in the chat ready
        for (ClientHandler c : clients)
            c.isReady = false;


        // make every one awake
        isAwake = true;
        // make the time to dayChat
        dayChat = true;
        // TODO dayChat should set false in the game to stop chat

        sendMessage(new Message("God", "Now the day starts and it's chat time. It will take 5 min" +
                "at last, if you get ready for the voting at any time just enter (ready) and wait for " +
                "the others"));

        // the game will check if all the clients are ready or the time of the chat ends and make dayChat false

        if (!canChat()) { // TODO if a client is silent, it should be set in the client too ( as a field )
            this.isReady = true;
            sendMessage(new Message("God", "You can not chat"));
            while (dayChat) {
                if (receiveMessage() != null)
                    sendMessage(new Message("God", "As I just said, you can not chat"));
            }
        } else {
            while (dayChat) {
                Message message = receiveMessage();
                if (message.getText().equals("ready")) {
                    isReady = true;
                    break;
                }
                sendToOthers(message);
            }
        }

        // after it gets out of the while loop
        while(!chatIsEndForAll()){

        }
        sendMessage(new Message("God", "The chat is over"));

    }

    private boolean chatIsEndForAll(){
        for(ClientHandler c : clients){
            if(!c.isReady)
                return false;
        }
        return true;
    }




    public boolean isReady() {
        return isReady;
    }

    // TODO this method should be updated in the future
    private boolean canChat(){
        return !isSilent && isAwake && isLoggedIn;
    }


    // TODO this method should check booleans so carefully, every condition should be checked
    private void sendToOthers(Message message) {
        for (ClientHandler c : clients) {
            if (c != this)
                c.sendMessage(message);
        }
    }

    public void setDayChat(boolean dayChat) {
        this.dayChat = dayChat;
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
        // start the day
        // TODO i guess that this parts should be in a loop
        // wait, the game will notify
        System.out.println("before wait");
        waitClientHandler();
        // the day starts with chatting between clients
        dayChatroom();


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