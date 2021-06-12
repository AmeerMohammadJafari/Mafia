package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Detective extends Character{

    public Detective(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);
    }


    @Override
    public void behaviour() {

    }

    @Override
    public void consultInNight() {

    }


}
