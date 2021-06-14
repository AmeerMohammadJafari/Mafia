package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Psychologist extends Character{


    public Psychologist(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);

        mayorTimeBehaviour = new NonMayorTreat(this);
        mafiasVoteTimeBehaviour = new NonMafiasTreat(this);
        godFatherTimeBehaviour = new NonGodFatherTreat(this);
        doctorLecterTimeBehaviour = new NonDoctorLecterTreat(this);
        doctorTimeBehaviour = new NonDoctorTreat(this);
        detectiveTimeBehaviour = new NonDetectiveTreat(this);
        sniperTimeBehaviour = new NonSniperTreat(this);
        psychologistTimeBehaviour = new PsychologistTreat(this);
        diehardTimeBehaviour = new NonDiehardTreat(this);
    }



}
