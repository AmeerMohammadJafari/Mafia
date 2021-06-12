package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Doctor extends Character{

    private int treat;

    public Doctor(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);

        treat = 1;
    }


    @Override
    public void behaviour() {

    }

    @Override
    public void consultInNight() {

    }


}
