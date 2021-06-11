package com.company;

import java.util.*;

public class Game extends Thread{

    private Vector<ClientHandler> clients;
    private Vector<ClientHandler> mafias;
    private Vector<ClientHandler> villagers;
    private static int numberOfClients;

    public Game(Vector<ClientHandler> clients, int numberOfClients) {
        this.clients = clients;
        Game.numberOfClients = numberOfClients;
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
        /*for (ClientHandler c : clients)
            if (c.getRole() == Role.Diehard)
                c.setHealth(2);*/ //TODO this part should be added when you have create the role classes
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
            }
            sleepThread(1000);
        }
    }

    private void endVote(){

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                sendToAll(new Message("God", "The vote is over"));
                ClientHandler.setMode(Mode.ResultOfVote);
            }
        };
        timer.schedule(timerTask, 30 * 1000);

        while(ClientHandler.getMode() == Mode.Vote){
            sleepThread(1000);
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

        endVote();






    }
}
