package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SimpleMafia extends Character{


    public SimpleMafia(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);

        mayorTimeBehaviour = new NonMayorTreat(this);
        mafiasVoteTimeBehaviour = new MafiasVoteTreat(this);
        godFatherTimeBehaviour = new NonGodFatherTreat(this);
        doctorLecterTimeBehaviour = new NonDoctorLecterTreat(this);
        doctorTimeBehaviour = new NonDoctorTreat(this);
        detectiveTimeBehaviour = new NonDetectiveTreat(this);
        sniperTimeBehaviour = new NonSniperTreat(this);
        psychologistTimeBehaviour = new NonPsychologistTreat(this);
        diehardTimeBehaviour = new NonDiehardTreat(this);

    }


}
