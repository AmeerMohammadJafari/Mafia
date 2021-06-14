package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Character {


    protected ObjectOutputStream output;
    protected ObjectInputStream input;
    protected ClientHandler client;
    protected Game game;

    protected Behaviour mayorTimeBehaviour;
    protected MafiasVoteTimeBehaviour mafiasVoteTimeBehaviour;
    protected Behaviour godFatherTimeBehaviour;
    protected LimitedBehaviour doctorLecterTimeBehaviour;
    protected LimitedBehaviour doctorTimeBehaviour;
    protected Behaviour detectiveTimeBehaviour;
    protected Behaviour sniperTimeBehaviour;
    protected Behaviour psychologistTimeBehaviour;
    protected LimitedBehaviour diehardTimeBehaviour;


    public Character(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client, Game game){

        this.output = output;
        this.input = input;
        this.client = client;
        this.game = game;
        client.setHealth(1);
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public ClientHandler getClient() {
        return client;
    }

    public Game getGame() {
        return game;
    }

    public Behaviour getMayorTimeBehaviour() {
        return mayorTimeBehaviour;
    }

    public MafiasVoteTimeBehaviour getMafiasVoteTimeBehaviour() {
        return mafiasVoteTimeBehaviour;
    }

    public void setGodFatherTimeBehaviour(Behaviour godFatherTimeBehaviour) {
        this.godFatherTimeBehaviour = godFatherTimeBehaviour;
    }

    public Behaviour getGodFatherTimeBehaviour() {
        return godFatherTimeBehaviour;
    }

    public LimitedBehaviour getDoctorLecterTimeBehaviour() {
        return doctorLecterTimeBehaviour;
    }

    public LimitedBehaviour getDoctorTimeBehaviour() {
        return doctorTimeBehaviour;
    }

    public Behaviour getDetectiveTimeBehaviour() {
        return detectiveTimeBehaviour;
    }

    public Behaviour getSniperTimeBehaviour() {
        return sniperTimeBehaviour;
    }

    public Behaviour getPsychologistTimeBehaviour() {
        return psychologistTimeBehaviour;
    }

    public LimitedBehaviour getDiehardTimeBehaviour() {
        return diehardTimeBehaviour;
    }

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

