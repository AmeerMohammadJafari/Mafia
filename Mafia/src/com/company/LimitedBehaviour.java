package com.company;

public abstract class LimitedBehaviour extends Behaviour{

    protected int treat;

    public LimitedBehaviour(Character character) {
        super(character);
    }

    public abstract void run();
}
