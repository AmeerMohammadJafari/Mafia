package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * This class is used for behaviours of the Character class
 */
public abstract class Behaviour {


    /**
     * The Output.
     */
    protected ObjectOutputStream output;
    /**
     * The Input.
     */
    protected ObjectInputStream input;
    /**
     * The Client.
     */
    protected ClientHandler client;
    /**
     * The Game.
     */
    protected Game game;
    /**
     * The Behaviour done.
     */
    protected boolean behaviourDone;

    /**
     * Instantiates a new Behaviour.
     *
     * @param character the character
     */
    public Behaviour(Character character){
         this.output = character.getOutput();
         this.input = character.getInput();
         this.client = character.getClient();
         this.game = character.getGame();
         behaviourDone = false;
    }

    /**
     * Sets behaviour done.
     *
     * @param behaviourDone the behaviour done
     */
    public void setBehaviourDone(boolean behaviourDone) {
        this.behaviourDone = behaviourDone;
    }

    /**
     * Send message.
     *
     * @param message the message
     */
    protected void sendMessage(Message message) {
        try {
            output.writeObject(message);
        } catch (SocketException e) {
            System.out.println("socket exception");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // send all the messages for dead clients to
        /*for(ClientHandler c : game.getClients()){
            if(!c.isAliveClient() && c != client){
                try {
                    c.getOutput().writeObject(message);
                }catch (IOException e){

                }
            }
        }*/
    }

    /**
     * Receive message message.
     *
     * @return the message
     */
    protected Message receiveMessage() {
        Message message = null;
        try {
            message = (Message) input.readObject();
        } catch (SocketException e) {
            System.out.println("Socket exception");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert message != null;
        message.setName(client.getClientName());
        // send this message for the player which is dead
        for(ClientHandler c : game.getClients()){
            if(!c.isAliveClient()){
                try {
                    c.getOutput().writeObject(message);
                }catch (IOException e){

                }
            }
        }
        return message;
    }

    /**
     * Sleep thread.
     *
     * @param time the time
     */
    protected void sleepThread(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run.
     */
    public abstract void run();
}
