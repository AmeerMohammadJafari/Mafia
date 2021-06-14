package com.company;

public class NonSniperTreat extends Behaviour{


    public NonSniperTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        receiveMessage();

        if(ClientHandler.getMode() == Mode.SniperTime){
            sendMessage(new Message("God","Sniper Time, Can not say anything :|"));
        }
        // TODO handle sticking
    }
}
