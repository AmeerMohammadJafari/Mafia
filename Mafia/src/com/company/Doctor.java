package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Doctor extends Character{



    public Doctor(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);

        mayorTimeBehaviour = new NonMayorTreat(this);
        mafiasVoteTimeBehaviour = new NonMafiasTreat(this);
    }



}
