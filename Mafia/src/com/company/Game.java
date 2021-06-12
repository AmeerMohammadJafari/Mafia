package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Game extends Thread{

    private Vector<ClientHandler> clients;
    private Vector<ClientHandler> mafias;
    private Vector<ClientHandler> villagers;
    private static int numberOfClients;
    private ClientHandler removed;
    private boolean mayorConfirmation;

    public Game(Vector<ClientHandler> clients, int numberOfClients) {
        this.clients = clients;
        Game.numberOfClients = numberOfClients;
        removed = null;
        villagers = new Vector<>();
        mafias = new Vector<>();
    }

    public void setMayorConfirmation(boolean mayorConfirmation) {
        this.mayorConfirmation = mayorConfirmation;
    }

    private void sleepThread(int time){
        try{
            Thread.sleep(time);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private boolean allAreLoggedIn(){
        if(clients.size() != numberOfClients){
            return false;
        }

        for(ClientHandler c : clients){
            synchronized (c){
                if(!c.isLoggedIn())
                    return false;
            }
        }
        return true;
    }

    private void loopUntilAllLoggedIn(){
        while(!allAreLoggedIn()){
            sleepThread(2000);
        }
    }

    private boolean allSendRole(){
        for(ClientHandler c : clients){
            if(!c.isSendRole())
                return false;
        }
        return true;
    }

    private void loopUntilAllSendRole(){
        sleepThread(1000);
        while(!allSendRole()){
            sleepThread(2000);
        }
    }

    private boolean allIntroduced(){
        for(ClientHandler c : clients){
            synchronized (c){
                if(!c.isIntroduced())
                    return false;
            }
        }
        return true;
    }

    private void loopUntilAllIntroduced(){
        while(!allIntroduced()){
            sleepThread(2000);
        }
    }

    private boolean allClientReady() {
        for (ClientHandler c : clients) {
            if (!c.isReady())
                return false;
        }
        return true;
    }

    public void setRoles() {

        while(numberOfClients != clients.size()){
            sleepThread(1000);
        }

        ArrayList<Role> roles = new ArrayList<>();
        int mafiasNum = numberOfClients / 3;
        int villagersNum = numberOfClients - mafiasNum;
        roles.add(Role.GodFather);
        roles.add(Role.DoctorLecter);
        roles.add(Role.Doctor);
        roles.add(Role.Detective);
        roles.add(Role.Sniper);
        roles.add(Role.Mayor);
        roles.add(Role.Psychologist);
        roles.add(Role.Diehard);
        for (int i = 0; i < mafiasNum - 2; i++) {
            roles.add(Role.SimpleMafia);
        }
        for (int i = 0; i < villagersNum - 6; i++) {
            roles.add(Role.SimpleVillager);
        }
        Collections.shuffle(roles);
        for (int i = 0; i < numberOfClients; i++) {
            clients.get(i).setRole(roles.get(i));
        }

        for(ClientHandler c : clients){
            ObjectOutputStream output = c.getOutput();
            ObjectInputStream input = c.getInput();
            String clientName = c.getClientName();

            switch (c.getRole()) {
                case Mayor -> {
                    c.setCharacter(new Mayor(output, input, c, this));
                    villagers.add(c);
                }

                case Psychologist -> {
                    c.setCharacter(new Psychologist(output, input, c, this));
                    villagers.add(c);
                }

                case Diehard -> {
                    c.setCharacter(new Diehard(output, input, c, this));
                    villagers.add(c);
                }

                case Sniper -> {
                    c.setCharacter(new Sniper(output, input, c, this));
                    villagers.add(c);
                }

                case Detective -> {
                    c.setCharacter(new Detective(output, input, c, this));
                    villagers.add(c);
                }

                case Doctor -> {
                    c.setCharacter(new Doctor(output, input, c, this));
                    villagers.add(c);
                }

                case DoctorLecter -> {
                    c.setCharacter(new DoctorLecter(output, input, c, this));
                    mafias.add(c);
                }

                case GodFather -> {
                    c.setCharacter(new GodFather(output, input, c, this));
                    mafias.add(c);
                }

                case SimpleMafia -> {
                    c.setCharacter(new SimpleMafia(output, input, c, this));
                    mafias.add(c);
                }

                case SimpleVillager -> {
                    c.setCharacter(new SimpleVillager(output, input, c, this));
                    villagers.add(c);
                }
            }

        }
    }

    private void sendToAll(Message message){
        for(ClientHandler c : clients){
            synchronized (c){
                c.sendMessage(message);
            }
        }
    }

    private boolean allChatIntro(){
        for(ClientHandler c : clients){
            synchronized (c){
                if(!c.isChatStarted())
                    return false;
            }
        }
        return true;
    }

    private void loopUntilChatIntro(){
        while(!allChatIntro()){
            sleepThread(1000);
        }
    }

    private void endChat(){

        loopUntilChatIntro();
        boolean[] isDone = {false};
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for(ClientHandler c : clients){
                    synchronized (c){
                        c.setReady(true);
                    }
                } // makes every client ready
                ClientHandler.setMode(Mode.Vote);
                sleepThread(500);
                sendToAll(new Message("God", "The chat is over."));
                isDone[0] = true;
            }
        };
        timer.schedule(timerTask, 5 * 60 * 1000);

        while(ClientHandler.getMode() == Mode.DayChatroom){
            if (allClientReady()) {
                ClientHandler.setMode(Mode.Vote);
                sleepThread(500);
                sendToAll(new Message("God", "The chat is over."));
                timer.cancel();
                break;
            }
            sleepThread(1000);
        }
    }

    private boolean allVoteStart(){
        for(ClientHandler c : clients){
            synchronized (c){
                if(!c.isVoteStarted())
                    return false;
            }
        }
        return true;
    }


    private void loopUntilVoteStart(){
        while (!allVoteStart()) {
            sleepThread(1000);
        }
    }

    private void endVote(){

        loopUntilVoteStart();

        System.out.println("The first part of the endVote method, just before setting the timer");

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ClientHandler.setMode(Mode.ResultOfVote);
                sleepThread(500);
                sendToAll(new Message("God", "The vote is over."));
            }
        };
        timer.schedule(timerTask, 30 * 1000);


        // a loop for not going forward to result vote until the mode is changed
        while(ClientHandler.getMode() != Mode.ResultOfVote){
            sleepThread(1000);
        }

        sleepThread(500);



    }

    private void resultOfVote(){

        sendToAll(new Message("God", "The result will be shown."));
        sleepThread(3000);

        HashMap<ClientHandler, Integer> voteMap = new HashMap<>();

        for(ClientHandler c : clients){
            synchronized (c) {
                voteMap.put(c, 0);
            }
        }

        for(ClientHandler c : clients){
            synchronized (c){
                try {
                    voteMap.put(c.getMyVote(), voteMap.get(c.getMyVote()) + 1);
                }catch (NullPointerException e){
                }
            }
        }



        int maxVote = Collections.max(voteMap.values());
        for(ClientHandler c : voteMap.keySet()){
            if(voteMap.get(c) == maxVote){
                removed = c;
                break;
            }
        }


        assert removed != null;
        sendToAll(new Message("God", removed.getClientName() + " has the most votes"));
        ClientHandler.setMode(Mode.MayorTime);
    }

    public void mayorTime(){

        ClientHandler mayor = null;

        for(ClientHandler c : clients){
            if(c.getRole() == Role.Mayor)
                mayor = c;
        }

        assert mayor != null; // TODO take care
        Character character = mayor.getCharacter();

        // loop until mayor done his behaviour
        while(!character.behaviourDone){
            sleepThread(1000);
        }

        if(mayorConfirmation){
            sendToAll(new Message("God", "The mayor confirmed the vote"));
            sleepThread(1000);
            sendToAll(new Message("God", removed.getClientName() + " is removed."));
            // TODO should be removed from the game
        }
        else{
            sendToAll(new Message("God", "The mayor rejects the voting"));
            sleepThread(1000);
            sendToAll(new Message("God","So " + removed.getClientName() + "is still " +
                    "with us"));
            removed = null;
        }





    }


    @Override
    public void run() {

        loopUntilAllLoggedIn();

        setRoles();

        ClientHandler.setMode(Mode.SendRoll);

        loopUntilAllSendRole();

        ClientHandler.setMode(Mode.Introduction);

        loopUntilAllIntroduced();

        ClientHandler.setMode(Mode.DayChatroom);

        endChat(); // it sets the game mode to vote internally

        endVote(); // it sets the game to the resultOfVote internally

        resultOfVote();

        mayorTime();




    }
}
