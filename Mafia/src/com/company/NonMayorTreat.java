package com.company;

/**
 * The NonMayorTreat class
 *
 */
public class NonMayorTreat extends Behaviour{

    /**
     * Instantiates a new Non mayor treat.
     *
     * @param character the character
     */
    public NonMayorTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        receiveMessage();

        if(client.getGameMode() == Mode.MayorTime){
            sendMessage(new Message("God","Mayor Time, Can not say anything :|"));
        }
        // TODO handle sticking
    }
}
