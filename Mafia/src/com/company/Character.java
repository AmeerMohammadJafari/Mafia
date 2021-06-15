package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class is used inside all the clientHandlers
 */
public abstract class Character {


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
     * The Mayor time behaviour.
     */
    protected Behaviour mayorTimeBehaviour;
    /**
     * The Mafias vote time behaviour.
     */
    protected MafiasVoteTimeBehaviour mafiasVoteTimeBehaviour;
    /**
     * The God father time behaviour.
     */
    protected Behaviour godFatherTimeBehaviour;
    /**
     * The Doctor lecter time behaviour.
     */
    protected LimitedBehaviour doctorLecterTimeBehaviour;
    /**
     * The Doctor time behaviour.
     */
    protected LimitedBehaviour doctorTimeBehaviour;
    /**
     * The Detective time behaviour.
     */
    protected Behaviour detectiveTimeBehaviour;
    /**
     * The Sniper time behaviour.
     */
    protected Behaviour sniperTimeBehaviour;
    /**
     * The Psychologist time behaviour.
     */
    protected Behaviour psychologistTimeBehaviour;
    /**
     * The Diehard time behaviour.
     */
    protected LimitedBehaviour diehardTimeBehaviour;


    /**
     * Instantiates a new Character.
     *
     * @param output the output
     * @param input  the input
     * @param client the client
     * @param game   the game
     */
    public Character(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client, Game game){

        this.output = output;
        this.input = input;
        this.client = client;
        this.game = game;
        client.setHealth(1);
    }

    /**
     * Gets output.
     *
     * @return the output
     */
    public ObjectOutputStream getOutput() {
        return output;
    }

    /**
     * Gets input.
     *
     * @return the input
     */
    public ObjectInputStream getInput() {
        return input;
    }

    /**
     * Gets client.
     *
     * @return the client
     */
    public ClientHandler getClient() {
        return client;
    }

    /**
     * Gets game.
     *
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Gets mayor time behaviour.
     *
     * @return the mayor time behaviour
     */
    public Behaviour getMayorTimeBehaviour() {
        return mayorTimeBehaviour;
    }

    /**
     * Gets mafias vote time behaviour.
     *
     * @return the mafias vote time behaviour
     */
    public MafiasVoteTimeBehaviour getMafiasVoteTimeBehaviour() {
        return mafiasVoteTimeBehaviour;
    }

    /**
     * Sets god father time behaviour.
     *
     * @param godFatherTimeBehaviour the god father time behaviour
     */
    public void setGodFatherTimeBehaviour(Behaviour godFatherTimeBehaviour) {
        this.godFatherTimeBehaviour = godFatherTimeBehaviour;
    }

    /**
     * Gets god father time behaviour.
     *
     * @return the god father time behaviour
     */
    public Behaviour getGodFatherTimeBehaviour() {
        return godFatherTimeBehaviour;
    }

    /**
     * Gets doctor lecter time behaviour.
     *
     * @return the doctor lecter time behaviour
     */
    public LimitedBehaviour getDoctorLecterTimeBehaviour() {
        return doctorLecterTimeBehaviour;
    }

    /**
     * Gets doctor time behaviour.
     *
     * @return the doctor time behaviour
     */
    public LimitedBehaviour getDoctorTimeBehaviour() {
        return doctorTimeBehaviour;
    }

    /**
     * Gets detective time behaviour.
     *
     * @return the detective time behaviour
     */
    public Behaviour getDetectiveTimeBehaviour() {
        return detectiveTimeBehaviour;
    }

    /**
     * Gets sniper time behaviour.
     *
     * @return the sniper time behaviour
     */
    public Behaviour getSniperTimeBehaviour() {
        return sniperTimeBehaviour;
    }

    /**
     * Gets psychologist time behaviour.
     *
     * @return the psychologist time behaviour
     */
    public Behaviour getPsychologistTimeBehaviour() {
        return psychologistTimeBehaviour;
    }

    /**
     * Gets diehard time behaviour.
     *
     * @return the diehard time behaviour
     */
    public LimitedBehaviour getDiehardTimeBehaviour() {
        return diehardTimeBehaviour;
    }

    /**
     * Reset.
     */
    public void reset(){
        mayorTimeBehaviour.setBehaviourDone(false);
        mafiasVoteTimeBehaviour.setBehaviourDone(false);
        godFatherTimeBehaviour.setBehaviourDone(false);
        doctorLecterTimeBehaviour.setBehaviourDone(false);
        doctorTimeBehaviour.setBehaviourDone(false);
        detectiveTimeBehaviour.setBehaviourDone(false);
        sniperTimeBehaviour.setBehaviourDone(false);
        psychologistTimeBehaviour.setBehaviourDone(false);
        diehardTimeBehaviour.setBehaviourDone(false);
    }
}

