package com.company;

public class NonDoctorTreat extends LimitedBehaviour {


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
