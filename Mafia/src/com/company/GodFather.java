package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class GodFather extends Mafia{


    public GodFather(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){

        super(output, input, client, game);
    }


    @Override
    public void behaviour() {

    }

}
