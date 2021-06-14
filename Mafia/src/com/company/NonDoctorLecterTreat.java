package com.company;

public class NonDoctorLecterTreat extends LimitedBehaviour {


    public NonDoctorLecterTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        receiveMessage();

        if(ClientHandler.getMode() == Mode.DoctorLecterTime){
            sendMessage(new Message("God","DoctorLecter Time, Can not say anything :|"));
        }
        // TODO handle sticking
    }
}
