package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The DoctorLecter class
 */
public class DoctorLecter extends Character{

    /**
     * Instantiates a new Doctor lecter.
     *
     * @param output the output
     * @param input  the input
     * @param client the client
     * @param game   the game
     */
    public DoctorLecter(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);

        mayorTimeBehaviour = new NonMayorTreat(this);
        mafiasVoteTimeBehaviour = new MafiasVoteTreat(this);
        godFatherTimeBehaviour = new NonGodFatherTreat(this);
        doctorLecterTimeBehaviour = new DoctorLecterTreat(this);
        doctorTimeBehaviour = new NonDoctorTreat(this);
        detectiveTimeBehaviour = new NonDetectiveTreat(this);
        sniperTimeBehaviour = new NonSniperTreat(this);
        psychologistTimeBehaviour = new NonPsychologistTreat(this);
        diehardTimeBehaviour = new NonDiehardTreat(this);

    }



}
