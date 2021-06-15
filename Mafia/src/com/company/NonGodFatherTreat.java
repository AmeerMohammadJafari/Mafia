package com.company;

/**
 * The NonGodfatherTreat class
 */
public class NonGodFatherTreat extends Behaviour{


    /**
     * Instantiates a new Non god father treat.
     *
     * @param character the character
     */
    public NonGodFatherTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        receiveMessage();

        if(client.getGameMode() == Mode.GodFatherTime){
            sendMessage(new Message("God","GodFather Time, Can not say anything :|"));
        }
        // TODO handle sticking
    }
}
