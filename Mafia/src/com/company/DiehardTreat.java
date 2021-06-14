package com.company;

import java.util.ArrayList;

public class DiehardTreat extends LimitedBehaviour{

    public DiehardTreat(Character character) {
        super(character);
        treat = 2;
    }

    @Override
    public void run() {

        if (behaviourDone)
            return;

        if(treat <= 0){
            sleepThread(1000);
            sendMessage(new Message("God", "You can not use your act any more :|"));
            sleepThread(1000);
            behaviourDone = true;
            return;
        }

        sleepThread(1000);
        sendMessage(new Message("God", "If you want to know which roles are out of " +
                "game, enter (yes) else (no)"));
        sleepThread(1000);

        while (true) {

            Message message = receiveMessage();
            if(message.getText().equals("yes")){
                /*sendMessage(new Message("God", "The roles which are removed :"));
                ArrayList<Role> roles = game.getRemovedRoles();
                String text = "";
                for(Role role : roles){
                    text += role + ", ";
                }
                sleepThread(1000);*/
                game.setDiehardAct(true);
                sendMessage(new Message("God", "Done"));
                treat--;
                break;
            }
            else if(message.getText().equals("no")){
                sendMessage(new Message("God", "Done"));
                break;
            }
            else{
                sendMessage(new Message("God", ":|"));
            }

        }

        behaviourDone = true;
    }
}
