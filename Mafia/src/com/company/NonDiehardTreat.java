package com.company;

/**
 * The NonDiehardTreat class which extends LimitedBehaviour
 */
public class NonDiehardTreat extends LimitedBehaviour{

    /**
     * Instantiates a new Non diehard treat.
     *
     * @param character the character
     */
    public NonDiehardTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        receiveMessage();

        if(client.getGameMode() == Mode.DieHardTime){
            sendMessage(new Message("God","Diehard Time, Can not say anything :|"));
        }
        // TODO handle sticking
    }
}
