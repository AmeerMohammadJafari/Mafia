package com.company;

public class DoctorLecterTreat extends DoctorTimeBehaviour {


    public DoctorLecterTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {
        if (behaviourDone)
            return;


        sleepThread(1000);
        sendMessage(new Message("God", "Choose one of your teammates to cure"));
        sleepThread(1000);
        sendMessage(new Message("God", "The mafias list\n" + game.mafiasList()));

        while (true) {

            Message message = receiveMessage();
            ClientHandler myChoice = ClientHandler.isMafiaName(message.getText());

            if (myChoice != null) {

                if (myChoice == client) {
                    treat--;
                    if (treat < 0) {
                        sendMessage(new Message("God", "You have chosen yourself before :/"));
                        continue;
                    }
                }

                game.setDoctorLecterChoice(myChoice);
                sendMessage(new Message("God", "Done"));
                break;

            } else {
                sendMessage(new Message("God", ":|"));
            }
        }

        behaviourDone = true;
    }
}
