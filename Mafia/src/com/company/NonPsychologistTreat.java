package com.company;

public class NonPsychologistTreat extends Behaviour{

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
