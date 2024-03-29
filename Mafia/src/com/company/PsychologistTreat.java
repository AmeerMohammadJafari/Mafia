package com.company;

/**
 * The PsychologistTreat class which extends Behaviour
 */
public class PsychologistTreat extends Behaviour{

    /**
     * Instantiates a new Psychologist treat.
     *
     * @param character the character
     */
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
        sendMessage(new Message("God", "Choose a person you want to silent, or enter " +
                "(no) if you don't want to silent anyone"));

        while (true) {

            Message message = receiveMessage();
            if(message.getText().equals("no")){
                game.setPsychologistChoice(null);
                sendMessage(new Message("God", "Done"));
                break;
            }

            ClientHandler myChoice = null;
            myChoice = ClientHandler.isClientName(message.getText());


            if (myChoice != null) {

                if(myChoice == client){
                    sendMessage(new Message("God", ":|"));
                    continue;
                }

                game.setPsychologistChoice(myChoice);
                sendMessage(new Message("God", "Done"));
                break;

            } else {
                sendMessage(new Message("God", ":|"));
            }
        }
        behaviourDone = true;
    }
}
