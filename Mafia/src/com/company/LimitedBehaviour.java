package com.company;

/**
 * The LimitedBehaviour class which extends the Behaviour and has an optional field which is
 * used for Characters such as Doctor, DoctorLecter, ...
 */
public abstract class LimitedBehaviour extends Behaviour{

    /**
     * The Treat.
     */
    protected int treat;

    /**
     * Instantiates a new Limited behaviour.
     *
     * @param character the character
     */
    public LimitedBehaviour(Character character) {
        super(character);
    }

    public abstract void run();
}
