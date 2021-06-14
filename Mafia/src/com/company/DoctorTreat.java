package com.company;

public class DoctorTreat extends DoctorTimeBehaviour {


    public DoctorTreat(Character character) {
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
        sendMessage(new Message("God", "Choose a person to cure"));

        while (true) {

            Message message = receiveMessage();
            ClientHandler myChoice = null;
            try {
                myChoice = ClientHandler.isClientName(message.getText());
            }catch (NullPointerException e){
                System.out.println("null exception");
            }

            if (myChoice != null) {

                if (myChoice == client) {
                    treat--;
                    if (treat < 0) {
                        sendMessage(new Message("God", "You have chosen yourself before :/"));
                        continue;
                    }
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
