package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Sniper extends Character{


    public Sniper(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);
    }



    @Override
    public void consultInNight() {

    }

    @Override
    public void behaviour() {

    }
}
