package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public abstract class Character {

    protected ObjectOutputStream output;
    protected ObjectInputStream input;
    protected ClientHandler client;
    protected Game game;
    protected boolean behaviourDone;

    public Character(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client, Game game){

        this.output = output;
        this.input = input;
        this.client = client;
        behaviourDone = false;
        this.game = game;
        client.setHealth(1);
    }

    protected void sendMessage(Message message) {
        try {
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Message receiveMessage() {
        Message message = null;
        try {
            message = (Message) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }


    public abstract void behaviour();

    public abstract void consultInNight();

}

