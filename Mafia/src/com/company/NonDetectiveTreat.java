package com.company;

/**
 * The NonDetectiveTreat class
 */
public class NonDetectiveTreat extends Behaviour{


    /**
     * Instantiates a new Non detective treat.
     *
     * @param character the character
     */
    public NonDetectiveTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        receiveMessage();

        if(client.getGameMode() == Mode.DetectiveTime){
            sendMessage(new Message("God","Detective Time, Can not say anything :|"));
        }
        // TODO handle sticking
    }
}
