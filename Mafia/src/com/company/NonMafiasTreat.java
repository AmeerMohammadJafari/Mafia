package com.company;

public class NonMafiasTreat extends MafiasVoteTimeBehaviour{

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
