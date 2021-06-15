package com.company;

/**
 * The NonPsychologistTreat which extends Behaviour
 */
public class NonPsychologistTreat extends Behaviour{

    /**
     * Instantiates a new Non psychologist treat.
     *
     * @param character the character
     */
    public NonPsychologistTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        receiveMessage();

        if(client.getGameMode() == Mode.PsychologistTime){
            sendMessage(new Message("God","Psychologist Time, Can not say anything :|"));
        }
        // TODO handle sticking
    }
}
