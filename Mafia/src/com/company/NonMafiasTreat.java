package com.company;

/**
 * The NonMafiasTreat class which extends MafiasVoteTimeBehaviour
 */
public class NonMafiasTreat extends MafiasVoteTimeBehaviour{

    /**
     * Instantiates a new Non mafias treat.
     *
     * @param character the character
     */
    public NonMafiasTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {

        receiveMessage();

        if(client.getGameMode() == Mode.MafiasVote){
            sendMessage(new Message("God","Mafias Time, Can not say anything :|"));
        }
        // TODO handle sticking
    }
}
