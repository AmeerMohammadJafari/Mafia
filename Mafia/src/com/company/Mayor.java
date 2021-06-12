package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Mayor extends Character{


    public Mayor(ObjectOutputStream output, ObjectInputStream input,
                     ClientHandler client,Game game){
        super(output, input, client, game);
    }



    @Override
    public void behaviour() {
        if(behaviourDone)
            return;

        while(true) {
            sendMessage(new Message("God", "Write (confirm) or (reject)."));
            Message message = receiveMessage();
            if(message.getText().equals("confirm")){
                sendMessage(new Message("God", "Done"));
                game.setMayorConfirmation(true);
                break;
            }
            else if(message.getText().equals("reject")){
                sendMessage(new Message("God", "Done"));
                game.setMayorConfirmation(false);
                break;
            }
            else{
                sendMessage(new Message("God", ":|"));
            }
        }
        behaviourDone = true;
    }

    @Override
    public void consultInNight() {

    }

}
