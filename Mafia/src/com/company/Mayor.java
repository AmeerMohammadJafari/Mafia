package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The Mayor class
 */
public class Mayor extends Character{


    /**
     * Instantiates a new Mayor.
     *
     * @param output the output
     * @param input  the input
     * @param client the client
     * @param game   the game
     */
    public Mayor(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);

        mayorTimeBehaviour = new MayorTreat(this);
        mafiasVoteTimeBehaviour = new NonMafiasTreat(this);
        godFatherTimeBehaviour = new NonGodFatherTreat(this);
        doctorLecterTimeBehaviour = new NonDoctorLecterTreat(this);
        doctorTimeBehaviour = new NonDoctorTreat(this);
        detectiveTimeBehaviour = new NonDetectiveTreat(this);
        sniperTimeBehaviour = new NonSniperTreat(this);
        psychologistTimeBehaviour = new NonPsychologistTreat(this);
        diehardTimeBehaviour = new NonDiehardTreat(this);

    }




}
