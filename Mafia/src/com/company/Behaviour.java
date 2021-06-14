package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

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

    public void setBehaviourDone(boolean behaviourDone) {
        this.behaviourDone = behaviourDone;
    }

    protected void sendMessage(Message message) {
        try {
            output.writeObject(message);
        } catch (SocketException e) {
            System.out.println("socket exception");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // send all the messages for dead clients to
        for(ClientHandler c : game.getClients()){
            if(!c.isAliveClient() && c != client){
                try {
                    c.getOutput().writeObject(message);
                }catch (IOException e){

                }
            }
        }
    }

    protected Message receiveMessage() {
        Message message = null;
        try {
            message = (Message) input.readObject();
        } catch (SocketException e) {
            System.out.println("Socket exception");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // send this message for the player which is dead
        for(ClientHandler c : game.getClients()){
            if(!c.isAliveClient() && c != client){
                try {
                    c.getOutput().writeObject(message);
                }catch (IOException e){

                }
            }
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
