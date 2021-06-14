package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DoctorLecter extends Character{

    public DoctorLecter(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);

        mayorTimeBehaviour = new NonMayorTreat(this);
        mafiasVoteTimeBehaviour = new MafiasVoteTreat(this);
        godFatherTimeBehaviour = new NonGodFatherTreat(this);
        doctorLecterTimeBehaviour = new DoctorLecterTreat(this);
        doctorTimeBehaviour = new NonDoctorTreat(this);

    }



}
