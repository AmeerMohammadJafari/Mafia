package com.company;

public class NonDoctorTreat extends DoctorTimeBehaviour{


    public NonDoctorTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        receiveMessage();

        if(ClientHandler.getMode() == Mode.DoctorLecterTime){
            sendMessage(new Message("God","Doctor Time, Can not say anything :|"));
        }
        // TODO handle sticking
    }
}
