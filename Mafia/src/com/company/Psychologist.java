package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Psychologist extends Character{


    public Psychologist(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);

        mayorTimeBehaviour = new NonMayorTreat(this);
        mafiasVoteTimeBehaviour = new NonMafiasTreat(this);
    }



}
