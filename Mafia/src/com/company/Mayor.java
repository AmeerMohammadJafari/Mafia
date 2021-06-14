package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Mayor extends Character{


    public Mayor(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);

        mayorTimeBehaviour = new MayorTreat(this);
        mafiasVoteTimeBehaviour = new NonMafiasTreat(this);
        godFatherTimeBehaviour = new NonGodFatherTreat(this);
        doctorLecterTimeBehaviour = new NonDoctorLecterTreat(this);
        doctorTimeBehaviour = new NonDoctorTreat(this);

    }




}
