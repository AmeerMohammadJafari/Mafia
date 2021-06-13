package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Mayor extends Character{


    public Mayor(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);

        mayorTimeBehaviour = new MayorTreat(this);
        mafiasVoteTimeBehaviour = new NonMafiasTreat(this);
        godFatherTimeBehaviour = new NonGodFatherTreat(this);
    }




}
