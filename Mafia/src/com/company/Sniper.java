package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The Sniper class which extends the Charactrer class
 */
public class Sniper extends Character{


    /**
     * Instantiates a new Sniper.
     *
     * @param output the output
     * @param input  the input
     * @param client the client
     * @param game   the game
     */
    public Sniper(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);

        mayorTimeBehaviour = new NonMayorTreat(this);
        mafiasVoteTimeBehaviour = new NonMafiasTreat(this);
        godFatherTimeBehaviour = new NonGodFatherTreat(this);
        doctorLecterTimeBehaviour = new NonDoctorLecterTreat(this);
        doctorTimeBehaviour = new NonDoctorTreat(this);
        detectiveTimeBehaviour = new NonDetectiveTreat(this);
        sniperTimeBehaviour = new SniperTreat(this);
        psychologistTimeBehaviour = new NonPsychologistTreat(this);
        diehardTimeBehaviour = new NonDiehardTreat(this);
    }



}
