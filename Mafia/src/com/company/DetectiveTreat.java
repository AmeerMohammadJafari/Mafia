package com.company;

public class DetectiveTreat extends Behaviour {

    public DetectiveTreat(Character character) {
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
        sendMessage(new Message("God", "Choose a person you want to know is mafia or not"));

        while (true) {

            Message message = receiveMessage();
            ClientHandler myChoice = null;
            myChoice = ClientHandler.isClientName(message.getText());

            if (myChoice != null) {
                if(myChoice == client) {
                    sendMessage(new Message("God", ":|"));
                    continue;
                }
                if(!Role.isMafia(myChoice.getRole()) || myChoice.getRole() == Role.GodFather){
                    sendMessage(new Message("God", "Not mafia"));
                }
                else{
                    sendMessage(new Message("God", "Is mafia"));
                }

                break;

            } else {
                sendMessage(new Message("God", ":|"));
            }
        }
        behaviourDone = true;
    }
}
