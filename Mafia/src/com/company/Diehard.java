package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Diehard extends Character{

    private int treat;

    public Diehard(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);
        client.setHealth(2);
        treat = 2;
    }


    @Override
    public void consultInNight() {

    }


    @Override
    public void behaviour() {

    }
}
