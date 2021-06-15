package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The Diehard class
 */
public class Diehard extends Character{


    /**
     * Instantiates a new Diehard.
     *
     * @param output the output
     * @param input  the input
     * @param client the client
     * @param game   the game
     */
    public Diehard(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);
        client.setHealth(2);

        mayorTimeBehaviour = new NonMayorTreat(this);
        mafiasVoteTimeBehaviour = new NonMafiasTreat(this);
        godFatherTimeBehaviour = new NonGodFatherTreat(this);
        doctorLecterTimeBehaviour = new NonDoctorLecterTreat(this);
        doctorTimeBehaviour = new NonDoctorTreat(this);
        detectiveTimeBehaviour = new NonDetectiveTreat(this);
        sniperTimeBehaviour = new NonSniperTreat(this);
        psychologistTimeBehaviour = new NonPsychologistTreat(this);
        diehardTimeBehaviour = new DiehardTreat(this);

    }

}
