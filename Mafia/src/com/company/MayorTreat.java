package com.company;

/**
 * The Mayor treat class which extends Behaviour
 */
public class MayorTreat extends Behaviour{


    /**
     * Instantiates a new Mayor treat.
     *
     * @param character the character
     */
    public MayorTreat(Character character) {
        super(character);
    }

    @Override
    public void run() {

        if(behaviourDone){
            return;
        }

        while(true) {
            sendMessage(new Message("God", "Write (confirm) or (reject)."));
            Message message = receiveMessage();
            if (message.getText().equals("confirm")) {
                sendMessage(new Message("God", "Done"));
                game.setMayorConfirmation(true);
                break;
            } else if (message.getText().equals("reject")) {
                sendMessage(new Message("God", "Done"));
                game.setMayorConfirmation(false);
                break;
            } else {
                sendMessage(new Message("God", ":|"));
            }
        }
        behaviourDone = true;
    }





}
