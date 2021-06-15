package com.company;

/**
 * The NonSniperTreat class which extends Behaviour
 */
public class NonSniperTreat extends Behaviour{


    /**
     * Instantiates a new Non sniper treat.
     *
     * @param character the character
     */
    public NonSniperTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        receiveMessage();

        if(client.getGameMode() == Mode.SniperTime){
            sendMessage(new Message("God","Sniper Time, Can not say anything :|"));
        }
        // TODO handle sticking
    }
}
