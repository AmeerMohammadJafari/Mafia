package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public abstract class Mafia extends Character{

    protected boolean consultDone;

    public Mafia(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);
    }

    @Override
    public void consultInNight() {
        // implement this method for mafias
    }


    public abstract void behaviour();
}
