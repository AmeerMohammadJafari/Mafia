package com.company;

public class NonDiehardTreat extends LimitedBehaviour{

    public NonDiehardTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        receiveMessage();

        if(client.getGameMode() == Mode.DieHardTime){
            sendMessage(new Message("God","Diehard Time, Can not say anything :|"));
        }
        // TODO handle sticking
    }
}
