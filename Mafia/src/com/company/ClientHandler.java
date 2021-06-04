package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public class ClientHandler implements Runnable {


    private static Vector<ClientHandler> clients;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String name;
    private boolean isLoggedIn;

    public ClientHandler(Socket socket, Vector<ClientHandler> clientHandlers) {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            clients = clientHandlers;
        } catch (IOException e) {

        }
    }

    private void enterName(){
        try {
            while (true) {
                output.writeObject(new Message("God", "Please enter your name"));
                Message message = (Message) input.readObject();
                int flag = 1;
                for (ClientHandler c : clients) {
                    if (c.getName().equals(message.getText())) {
                        output.writeObject(new Message("God", "The name you entered is chosen by" +
                                " another client"));
                        flag = 0;
                        break;
                    }
                }
                if (flag == 1) {
                    output.writeObject(new Message("God","Done"));
                    name = message.getText();
                    clients.add(this);
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void ready(){

        try {
            while (true) {
                output.writeObject(new Message("God", "Please enter (ready) if you are ready to start the game"));
                Message message = (Message) input.readObject();
                if (message.getText().equals("ready")) {
                    output.writeObject(new Message("God", "Done"));
                    isLoggedIn = true;
                    break;
                }
                else{
                    output.writeObject(new Message("God"," :|"));
                }
            }
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public String getName() {
        return name;
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





    @Override
    public void run() {

        enterName();
        ready();

        try {
            while (true) {
                Message message = (Message) input.readObject();
                handleMessage(message);
            }
        } catch (IOException | ClassNotFoundException e) {

        }
    }

    public ObjectOutputStream getOutput() {
        return output;
    }
}