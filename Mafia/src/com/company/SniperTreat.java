package com.company;

/**
 * The SniperTreat class which extends Behaviour class
 */
public class SniperTreat extends Behaviour{

    /**
     * Instantiates a new Sniper treat.
     *
     * @param character the character
     */
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
        sendMessage(new Message("God", "Choose a person you want to kill, or enter " +
                "(no) if you don't want to kill anyone"));

        while (true) {

            Message message = receiveMessage();
            if(message.getText().equals("no")){
                game.setSniperChoice(null);
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
