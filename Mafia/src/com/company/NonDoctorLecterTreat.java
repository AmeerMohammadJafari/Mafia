package com.company;

/**
 * The NonDoctorLecterTreat class which extends LimitedBehaviour
 */
public class NonDoctorLecterTreat extends LimitedBehaviour {


    /**
     * Instantiates a new Non doctor lecter treat.
     *
     * @param character the character
     */
    public NonDoctorLecterTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        receiveMessage();

        if(client.getGameMode() == Mode.DoctorLecterTime){
            sendMessage(new Message("God","DoctorLecter Time, Can not say anything :|"));
        }
        // TODO handle sticking
    }
}
