package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * This class is used for handling every single Client
 */
public class ClientHandler extends Thread {

    private static Vector<ClientHandler> clients;
    private static int numberOfClients;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;
    private String clientName;
    private boolean isReady;
    private Role role;
    private boolean isSilent; // TODO this field can be set by psycho either when a client just left and want
    private boolean isLoggedIn;
    private Mode gameMode;
    private boolean sendRole;
    private boolean introduced;
    private boolean chatStarted;
    private boolean voteStarted;
    private ClientHandler myVote;
    private Character character;
    private int health;
    private boolean mayorIntro;
    private boolean aliveClient; // TODO must handle this field very carefully
    private boolean consultStarted;
    private ArrayList<Role> removedRoles;

    /**
     * Instantiates a new Client handler.
     *
     * @param socket          the socket
     * @param clientHandlers  the client handlers
     * @param numberOfClients the number of clients
     */
    public ClientHandler(Socket socket, Vector<ClientHandler> clientHandlers, int numberOfClients) {

        gameMode = Mode.EnterNameAndReady;
        ClientHandler.numberOfClients = numberOfClients;
        this.isReady = false;
        this.isSilent = false;
        this.isLoggedIn = false;
        this.chatStarted = false;
        this.voteStarted = false;
        role = null;
        clientName = null;
        clients = clientHandlers;
        sendRole = false;
        introduced = false;
        myVote = null;
        character = null;
        mayorIntro = false;
        aliveClient = true;
        consultStarted = false;

        try {
            this.socket = socket;
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {

        }
    }

    public void setRemovedRoles(ArrayList<Role> removedRoles) {
        this.removedRoles = removedRoles;
    }

    /**
     * Is silent boolean.
     *
     * @return the boolean
     */
    public boolean isSilent() {
        return isSilent;
    }

    /**
     * Sets consult started.
     *
     * @param consultStarted the consult started
     */
    public void setConsultStarted(boolean consultStarted) {
        this.consultStarted = consultStarted;
    }

    /**
     * Sets mayor intro.
     *
     * @param mayorIntro the mayor intro
     */
    public void setMayorIntro(boolean mayorIntro) {
        this.mayorIntro = mayorIntro;
    }

    /**
     * Sets my vote.
     *
     * @param myVote the my vote
     */
    public void setMyVote(ClientHandler myVote) {
        this.myVote = myVote;
    }

    /**
     * Sets vote started.
     *
     * @param voteStarted the vote started
     */
    public void setVoteStarted(boolean voteStarted) {
        this.voteStarted = voteStarted;
    }

    /**
     * Sets chat started.
     *
     * @param chatStarted the chat started
     */
    public void setChatStarted(boolean chatStarted) {
        this.chatStarted = chatStarted;
    }

    /**
     * Is alive client boolean.
     *
     * @return the boolean
     */
    public boolean isAliveClient() {
        return aliveClient;
    }

    /**
     * Sets silent.
     *
     * @param silent the silent
     */
    public void setSilent(boolean silent) {
        isSilent = silent;
    }

    /**
     * Gets socket.
     *
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Sets alive client.
     *
     * @param aliveClient the alive client
     */
    public void setAliveClient(boolean aliveClient) {
        this.aliveClient = aliveClient;
    }

    /**
     * Gets character.
     *
     * @return the character
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Gets output.
     *
     * @return the output
     */
    public ObjectOutputStream getOutput() {
        return output;
    }

    /**
     * Gets input.
     *
     * @return the input
     */
    public ObjectInputStream getInput() {
        return input;
    }


    /**
     * Sets health.
     *
     * @param health the health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Gets health.
     *
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Low health.
     */
    public void lowHealth(){
        health--;
    }

    /**
     * Sets character.
     *
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**
     * Is vote started boolean.
     *
     * @return the boolean
     */
    public boolean isVoteStarted() {
        return voteStarted;
    }

    /**
     * Gets my vote.
     *
     * @return the my vote
     */
    public ClientHandler getMyVote() {
        return myVote;
    }

    /**
     * Is chat started boolean.
     *
     * @return the boolean
     */
    public boolean isChatStarted() {
        return chatStarted;
    }

    /**
     * Gets game mode.
     *
     * @return the game mode
     */
    public Mode getGameMode() {
        return gameMode;
    }

    /**
     * Sets game mode.
     *
     * @param gameMode the game mode
     */
    public void setGameMode(Mode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Sets ready.
     *
     * @param ready the ready
     */
    public void setReady(boolean ready) {
        isReady = ready;
    }

    /**
     * Is introduced boolean.
     *
     * @return the boolean
     */
    public boolean isIntroduced() {
        return introduced;
    }

    /**
     * Is ready boolean.
     *
     * @return the boolean
     */
    public boolean isReady() {
        return isReady;
    }

    /**
     * Is send role boolean.
     *
     * @return the boolean
     */
    public boolean isSendRole() {
        return sendRole;
    }

    /**
     * Is logged in boolean.
     *
     * @return the boolean
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }


    /**
     * Gets client name.
     *
     * @return the client name
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Send message.
     *
     * @param message the message
     */
    public void sendMessage(Message message) {
        try {
            output.writeObject(message);
        } catch (SocketException e) {
            System.out.println("socket exception");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // send all the messages for dead clients to
        for(ClientHandler c : clients){
            if(!c.isAliveClient()){
                try {
                    c.getOutput().writeObject(message);
                }catch (IOException e){

                }
            }
        }
    }

    /**
     * Receive message message.
     *
     * @return the message
     */
    public Message receiveMessage() {
        Message message = null;
        try {
            message = (Message) input.readObject();
        } catch (SocketException e) {
            System.out.println("Socket exception");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        // handling exit parts
        if(message.getText().equals("exit")){
            setAliveClient(false);
            setGameMode(Mode.OutOfGame);
            removedRoles.add(role);

            // closes the socket
            try {
                getSocket().close();
            } catch (IOException e) {

            }
        }


        // send this message for the player which is dead
        for(ClientHandler c : clients){
            if(!c.isAliveClient() && c != this){
                try {
                    c.getOutput().writeObject(message);
                }catch (IOException e){

                }
            }
        }
        return message;
    }



    private void sendToOthers(Message message) {
        for (ClientHandler c : clients) {
            if (c != this ){
                try {
                    c.getOutput().writeObject(message);
                }catch (IOException e){

                }
            }
        }
    }

    private void sleepThread(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String makeAListOfClients() {
        String s = "";
        int i = 1;
        for (ClientHandler c : clients) {
            if (c.isLoggedIn && c.isAliveClient()) {
                s += i + ". " + c.getClientName() + " ";
            }
            i++;
        }
        return s;
    }

    /**
     * Is client name client handler.
     *
     * @param text the text
     * @return the client handler
     */
    public static ClientHandler isClientName(String text) {
        for (ClientHandler c : clients) {
            if (c.isAliveClient()) {
                if (c.getClientName().equals(text)) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * Is mafia name client handler.
     *
     * @param text the text
     * @return the client handler
     */
    public static ClientHandler isMafiaName(String text) {
        for (ClientHandler c : clients) {
            if (c.getClientName().equals(text) && Role.isMafia(c.getRole()) && c.isAliveClient()) {
                return c;
            }
        }
        return null;
    }

    /**
     * Is villager name client handler.
     *
     * @param text the text
     * @return the client handler
     */
    public static ClientHandler isVillagerName(String text) {
        for (ClientHandler c : clients) {
            if (c.isAliveClient()) {
                if (c.getClientName().equals(text) && !Role.isMafia(c.getRole())) {
                    return c;
                }
            }
        }
        return null;
    }


    @Override
    public void run() {


        while (true) {

            if (gameMode == Mode.EnterNameAndReady) {
                if (isLoggedIn) {
                    sleepThread(1000);
                    continue;
                }
                enterNameAndReady();
            } else if (gameMode == Mode.SendRoll) {
                if (sendRole) {
                    sleepThread(1000);
                    continue;
                }
                sendRole();
            } else if (gameMode == Mode.Introduction) {
                if (introduced) {
                    sleepThread(1000);
                    continue;
                }
                introduce();
            } else if (gameMode == Mode.DayChatroom) {
                if (!chatStarted) {
                    chatIntro();
                }
                chat();
            } else if (gameMode == Mode.Vote) {
                if (!voteStarted) {
                    voteIntro();
                }
                vote();
                sleepThread(1000);
            } else if (gameMode == Mode.ResultOfVote) {
                sleepThread(5000);
                // the game will handle this part
            } else if (gameMode == Mode.MayorTime) {
                if (!mayorIntro)
                    mayorTimeIntro();
                character.getMayorTimeBehaviour().run();
                sleepThread(1000);
            } else if (gameMode == Mode.Remove) {
                sleepThread(2000);
                // the game will handle this part
            } else if (gameMode == Mode.MafiasVote) {

                if (!consultStarted)
                    consultIntro();

                character.getMafiasVoteTimeBehaviour().run();

                sleepThread(1000);
            } else if (gameMode == Mode.GodFatherTime) {
                character.getGodFatherTimeBehaviour().run();
                sleepThread(1000);
            } else if (gameMode == Mode.DoctorLecterTime) {
                character.getDoctorLecterTimeBehaviour().run();
                sleepThread(1000);
            } else if (gameMode == Mode.DoctorTime) {
                character.getDoctorTimeBehaviour().run();
                sleepThread(1000);
            }
            else if(gameMode == Mode.DetectiveTime){
                character.getDetectiveTimeBehaviour().run();
                sleepThread(1000);
            }
            else if(gameMode == Mode.SniperTime){
                character.getSniperTimeBehaviour().run();
                sleepThread(1000);
            }
            else if(gameMode == Mode.PsychologistTime){
                character.getPsychologistTimeBehaviour().run();
                sleepThread(1000);
            }
            else if(gameMode == Mode.DieHardTime){
                character.getDiehardTimeBehaviour().run();
                sleepThread(1000);
            }
            else if(gameMode == Mode.EndOfNight){
                sleepThread(1000);
            }
            else if(gameMode == Mode.CanOnlyWatch){
                onlyWatch();
                sleepThread(1000);
            }
            else if(gameMode == Mode.OutOfGame){
                sleepThread(10 * 1000);
            }
            else if(gameMode == Mode.MafiasWin){
                mafiasWin();
                return;
            }
            else if(gameMode == Mode.VillagersWin){
                villagersWin();
                return;
            }
        }
    }

    // TODO so many parts can be in the game instead, such as intros i guess

    private void enterNameAndReady() {
        while (true) {
            sendMessage(new Message("God", "Please enter your name"));
            Message message = receiveMessage();
            int flag = 1;
            for (ClientHandler c : clients) {
                if (c.getClientName().equals(message.getText())) {
                    sendMessage(new Message("God", "The name you entered is chosen by" +
                            " another client"));
                    flag = 0;
                    break;
                }
            }
            if (flag == 1) {
                sendMessage(new Message("God", "Done"));
                clientName = message.getText();
                clients.add(this);
                break;
            }
        }
        // ready part
        while (true) {
            sendMessage(new Message("God", "Please enter" +
                    " (ready) if you are ready to start the game"));
            Message message = receiveMessage();
            if (message.getText().equals("ready")) {

                sendMessage(new Message("God", "Done"));
                break;

            } else {
                sendMessage(new Message("God", " :|"));
            }
        }
        this.isLoggedIn = true;
    }


    private void sendRole() {
        sendMessage(new Message("God", "You are " + this.role.toString()));
        sendRole = true;
    }


    private void introduce() {


        sendMessage(new Message("God", "The introduction night starts."));
        sleepThread(3000);

        String text = "";
        // if the client is mafia
        if (Role.isMafia(role)) {
            for (ClientHandler c : clients) {
                if (c != this && Role.isMafia(c.getRole())) {
                    text += c.getClientName() + " is " + c.getRole() + ". ";
                }
            }
            sendMessage(new Message("God", text));
        }

        // if client is mayor
        if (role == Role.Mayor) {
            for (ClientHandler c : clients) {
                if (c.getRole() == Role.Doctor) {
                    sendMessage(new Message("God", c.getClientName() + " is " + c.getRole()));
                    break;
                }
            }
        }

        introduced = true;
        sendMessage(new Message("God", "The night is over"));
    }


    private void chatIntro() {
        sendMessage(new Message("God", "Now the day starts and it's chat time. It will take 5 min" +
                "utes at last, if you get ready for the voting at any time just enter (ready) and wait for " +
                "the others"));
        sleepThread(2000);

        for (ClientHandler c : clients)
            c.isReady = false;

        if (isSilent) {
            isReady = true;
        }

        chatStarted = true;
    }


    private void chat() {
        Message message = receiveMessage();

        if (gameMode == Mode.DayChatroom) {
            if (isSilent) {
                sendMessage(new Message("God", "As I just said, you can not chat"));
            } else {
                if (message.getText().equals("ready")) {
                    isReady = true;
                    sendToOthers(new Message("God", clientName + " is ready."));
                } else {
                    message.setName(clientName);
                    sendToOthers(message);
                }
            }
        }
    }

    private void voteIntro() {
        isSilent = false;
        sendMessage(new Message("God", "The voting is starting."));

        sleepThread(1000);

        sendMessage(new Message("God", "The list of current players :\n" +
                makeAListOfClients() + "\nEnter name of the player, you think is mafia."));

        myVote = null;
        voteStarted = true;
    }

    private void vote() {

        Message message = receiveMessage();

        if (gameMode == Mode.Vote) {
            if (isClientName(message.getText()) != null) {

                if (message.getText().equals(clientName)) {
                    sendMessage(new Message("God", "You can not vote to yourself :|"));
                } else {
                    myVote = isClientName(message.getText());
                    sendMessage(new Message("God", "Done"));
                    sendToOthers(new Message(clientName, "I vote to " + message.getText() + "."));
                }

            } else {
                sendMessage(new Message("God", ":|"));
            }
        }
    }

    private void mayorTimeIntro() {

        sendMessage(new Message("God", "Now the Mayor has to decide confirm or reject " +
                "the vote result"));
        sleepThread(2000);
        sendMessage(new Message("God", "Wait till mayor decides."));
        mayorIntro = true;

    }

    private void consultIntro() {
        sendMessage(new Message("God", "The night starts."));
        sleepThread(1000);
        sendMessage(new Message("God", "Mafias consult time"));
        sleepThread(1000);
        sendMessage(new Message("God", "Now mafias have to decide who should be killed"));
        consultStarted = true;
    }

    private void onlyWatch(){
        try {
            input.readObject();
        }catch (IOException | ClassNotFoundException e){

        }
        sendMessage(new Message("God", "You can only watch the game"));
    }

    private void mafiasWin(){
        sendMessage(new Message("God", "The mafias win the game"));
    }

    private void villagersWin(){
        sendMessage(new Message("God", "The villagers win"));
    }

}
