package com.company;

public class NonGodFatherTreat extends Behaviour{


    public NonGodFatherTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        receiveMessage();

        if(ClientHandler.getMode() == Mode.GodFatherTime){
            sendMessage(new Message("God","GodFather Time, Can not say anything :|"));
        }
        // TODO handle sticking
    }
}
