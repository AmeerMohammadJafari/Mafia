package com.company;

public class NonMafiasTreat extends MafiasVoteTimeBehaviour{

    public NonMafiasTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {

        receiveMessage();

        if(ClientHandler.getMode() == Mode.MafiasVote){
            sendMessage(new Message("God","Mafias Time, Can not say anything :|"));
        }
        // TODO handle sticking
        sleepThread(1000);
    }
}
