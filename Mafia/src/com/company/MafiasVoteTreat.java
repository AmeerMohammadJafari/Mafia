package com.company;

public class MafiasVoteTreat extends MafiasVoteTimeBehaviour {


    public MafiasVoteTreat(Character character) {
        super(character);
    }


    @Override
    public void run() {

        if(behaviourDone)
            return;

        sendMessage(new Message("God", "The mafias list\n" + game.mafiasList()));
        sleepThread(1000);
        sendMessage(new Message("God", "The villagers list\n" + game.villagersList()));
        sleepThread(1000);
        sendMessage(new Message("God", "Choose the one you think should be killed"));

        while (true) {

            Message message = receiveMessage();


            if (ClientHandler.isClientName(message.getText()) != null) {


                ClientHandler myVote = ClientHandler.isClientName(message.getText());
                voteMap.put(client, myVote);
                sendMessage(new Message("God", "Done"));
                break;

            } else {
                sendMessage(new Message("God", ":|"));
            }
        }

        behaviourDone = true;
    }


}

