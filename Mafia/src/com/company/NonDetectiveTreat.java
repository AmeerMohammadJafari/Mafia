package com.company;

public class NonDetectiveTreat extends Behaviour{


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
