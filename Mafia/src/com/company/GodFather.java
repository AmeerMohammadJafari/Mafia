package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GodFather extends Character{


    public GodFather(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){

        super(output, input, client, game);

        mayorTimeBehaviour = new NonMayorTreat(this);
        mafiasVoteTimeBehaviour = new MafiasVoteTreat(this);
    }



}
