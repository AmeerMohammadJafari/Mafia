package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class SimpleMafia extends Mafia{


    public SimpleMafia(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);
        client.setHealth(1);
    }

    @Override
    public void behaviour() {

    }

}
