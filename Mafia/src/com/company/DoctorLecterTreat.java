
package com.company;

public class DoctorLecterTreat extends LimitedBehaviour {


    public DoctorLecterTreat(Character character) {
        super(character);
        treat = 1;
    }

    @Override
    public void run() {
        if (behaviourDone)
            return;


        sleepThread(3000);
        sendMessage(new Message("God", "Choose one of your teammates to cure"));
        sleepThread(1000);
        sendMessage(new Message("God", "The mafias list\n" + game.mafiasList()));

        while (true) {

            Message message = receiveMessage();
            ClientHandler myChoice = ClientHandler.isMafiaName(message.getText());

            if (myChoice != null) {

                if (myChoice == client) {

                    if (treat <= 0) {
                        sendMessage(new Message("God", "You have chosen yourself before :/"));
                        continue;
                    }
                    treat--;
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
