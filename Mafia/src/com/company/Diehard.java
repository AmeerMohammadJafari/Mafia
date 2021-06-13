package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Diehard extends Character{


    public Diehard(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);
        client.setHealth(2);

        mayorTimeBehaviour = new NonMayorTreat(this);
        mafiasVoteTimeBehaviour = new NonMafiasTreat(this);
        godFatherTimeBehaviour = new NonGodFatherTreat(this);
    }

}
