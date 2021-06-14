package com.company;

public class SniperTreat extends Behaviour{

    public SniperTreat(Character character) {
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
        sendMessage(new Message("God", "Choose a person you want to kill"));

        while (true) {

            Message message = receiveMessage();
            ClientHandler myChoice = null;
            myChoice = ClientHandler.isClientName(message.getText());


            if (myChoice != null) {


                game.setSniperChoice(myChoice);
                sendMessage(new Message("God", "Done"));
                break;

            } else {
                sendMessage(new Message("God", ":|"));
            }
        }
        behaviourDone = true;
    }
}
