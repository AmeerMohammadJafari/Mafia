package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The GodFather class
 */
public class GodFather extends Character{


    /**
     * Instantiates a new God father.
     *
     * @param output the output
     * @param input  the input
     * @param client the client
     * @param game   the game
     */
    public GodFather(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){

        super(output, input, client, game);

        mayorTimeBehaviour = new NonMayorTreat(this);
        mafiasVoteTimeBehaviour = new NonMafiasTreat(this);
        godFatherTimeBehaviour = new GodFatherTreat(this);
        doctorLecterTimeBehaviour = new NonDoctorLecterTreat(this);
        doctorTimeBehaviour = new NonDoctorTreat(this);
        detectiveTimeBehaviour = new NonDetectiveTreat(this);
        sniperTimeBehaviour = new NonSniperTreat(this);
        psychologistTimeBehaviour = new NonPsychologistTreat(this);
        diehardTimeBehaviour = new NonDiehardTreat(this);

    }



}
