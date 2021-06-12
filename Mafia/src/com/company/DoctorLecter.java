package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class DoctorLecter extends Mafia{

    private int treat;

    public DoctorLecter(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);
        treat = 1;
    }

    @Override
    public void behaviour() {

    }

}
