package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The Doctor class
 */
public class Doctor extends Character{


    /**
     * Instantiates a new Doctor.
     *
     * @param output the output
     * @param input  the input
     * @param client the client
     * @param game   the game
     */
    public Doctor(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);

        mayorTimeBehaviour = new NonMayorTreat(this);
        mafiasVoteTimeBehaviour = new NonMafiasTreat(this);
        godFatherTimeBehaviour = new NonGodFatherTreat(this);
        doctorLecterTimeBehaviour = new NonDoctorLecterTreat(this);
        doctorTimeBehaviour = new DoctorTreat(this);
        detectiveTimeBehaviour = new NonDetectiveTreat(this);
        sniperTimeBehaviour = new NonSniperTreat(this);
        psychologistTimeBehaviour = new NonPsychologistTreat(this);
        diehardTimeBehaviour = new NonDiehardTreat(this);

    }



}
