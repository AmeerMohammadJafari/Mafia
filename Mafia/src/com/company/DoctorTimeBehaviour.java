package com.company;

public abstract class DoctorTimeBehaviour extends Behaviour{

    protected int treat;

    public DoctorTimeBehaviour(Character character) {
        super(character);
        treat = 1;
    }

    public abstract void run();
}
