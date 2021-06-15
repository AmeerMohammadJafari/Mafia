package com.company;

/**
 * The NonDoctorTreat class which extends LimitedBehaviour
 */
public class NonDoctorTreat extends LimitedBehaviour {


    /**
     * Instantiates a new Non doctor treat.
     *
     * @param character the character
     */
    public NonDoctorTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        receiveMessage();

        if(client.getGameMode() == Mode.DoctorTime){
            sendMessage(new Message("God","Doctor Time, Can not say anything :|"));
        }
        // TODO handle sticking
    }
}
