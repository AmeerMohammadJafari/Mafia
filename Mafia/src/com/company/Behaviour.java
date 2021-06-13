package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Behaviour {

    protected ObjectOutputStream output;
    protected ObjectInputStream input;
    protected ClientHandler client;
    protected Game game;
    protected boolean behaviourDone;

    public Behaviour(Character character){
         this.output = character.getOutput();
         this.input = character.getInput();
         this.client = character.getClient();
         this.game = character.getGame();
         behaviourDone = false;
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

    protected void sleepThread(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void run();
}
