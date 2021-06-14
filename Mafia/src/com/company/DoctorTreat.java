package com.company;

public class DoctorTreat extends LimitedBehaviour {


    public DoctorTreat(Character character) {
        super(character);
        treat = 1;
    }

    @Override
    public void run() {

        if (behaviourDone)
            return;


        sleepThread(3000);
        sendMessage(new Message("God", "Clients list :"));
        sleepThread(1000);
        sendMessage(new Message("God", game.clientsList()));
        sleepThread(1000);
        sendMessage(new Message("God", "Choose a person to cure"));

        while (true) {

            Message message = receiveMessage();
            ClientHandler myChoice = null;
            myChoice = ClientHandler.isClientName(message.getText());


            if (myChoice != null) {

                if (myChoice == client) {

                    if (treat <= 0) {
                        sendMessage(new Message("God", "You have chosen yourself before :/"));
                        continue;
                    }
                    treat--;
                }

                game.setDoctorChoice(myChoice);
                sendMessage(new Message("God", "Done"));
                break;

            } else {
                sendMessage(new Message("God", ":|"));
            }
        }
        behaviourDone = true;
    }
}
