
package com.company;

/**
 * The DoctorLecter treat
 */
public class DoctorLecterTreat extends LimitedBehaviour {


    /**
     * Instantiates a new Doctor lecter treat.
     *
     * @param character the character
     */
    public DoctorLecterTreat(Character character) {
        super(character);
        treat = 1;
    }

    @Override
    public void run() {
        if (behaviourDone)
            return;


        sleepThread(3000);
        sendMessage(new Message("God", "Choose one of your teammates to cure, or enter (no) " +
                "if you dont want to save anyone"));
        sleepThread(1000);
        sendMessage(new Message("God", "The mafias list\n" + game.mafiasList()));

        while (true) {

            Message message = receiveMessage();

            if(message.getText().equals("no")){
                game.setDoctorLecterChoice(null);
                sendMessage(new Message("God", "Done"));
                break;
            }


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
