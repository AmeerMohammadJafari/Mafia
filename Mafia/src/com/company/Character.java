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
    protected Behaviour mayorTimeBehaviour;
    protected MafiasVoteTimeBehaviour mafiasVoteTimeBehaviour;
    protected Behaviour godFatherTimeBehaviour;
    protected DoctorTimeBehaviour doctorLecterTimeBehaviour;
    protected DoctorTimeBehaviour doctorTimeBehaviour;


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

    public DoctorTimeBehaviour getDoctorLecterTimeBehaviour() {
        return doctorLecterTimeBehaviour;
    }

    public DoctorTimeBehaviour getDoctorTimeBehaviour() {
        return doctorTimeBehaviour;
    }
}

