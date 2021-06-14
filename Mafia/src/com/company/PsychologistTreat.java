package com.company;

public class PsychologistTreat extends Behaviour{

    public PsychologistTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        if (behaviourDone)
            return;

        sleepThread(1000);
        sendMessage(new Message("God", "Clients list :"));
        sleepThread(1000);
        sendMessage(new Message("God", game.clientsList()));
        sleepThread(1000);
        sendMessage(new Message("God", "Choose a person you want to silent"));

        while (true) {

            Message message = receiveMessage();
            ClientHandler myChoice = null;
            myChoice = ClientHandler.isClientName(message.getText());


            if (myChoice != null) {


                myChoice.setSilent(true);
                sendMessage(new Message("God", "Done"));
                break;

            } else {
                sendMessage(new Message("God", ":|"));
            }
        }
        behaviourDone = true;
    }
}
