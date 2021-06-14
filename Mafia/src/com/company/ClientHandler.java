package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Vector;

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
    private boolean isAlive; // TODO must handle this field very carefully
    private boolean consultStarted;


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
        isAlive = true;
        consultStarted = false;

        try {
            this.socket = socket;
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {

        }
    }


    public void setSilent(boolean silent) {
        isSilent = silent;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Character getCharacter() {
        return character;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public ObjectInputStream getInput() {
        return input;
    }


    public void setHealth(int health) {
        this.health = health;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public boolean isVoteStarted() {
        return voteStarted;
    }

    public ClientHandler getMyVote() {
        return myVote;
    }

    public boolean isChatStarted() {
        return chatStarted;
    }

    public Mode getGameMode() {
        return gameMode;
    }

    public void setGameMode(Mode gameMode) {
        this.gameMode = gameMode;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isIntroduced() {
        return introduced;
    }

    public boolean isReady() {
        return isReady;
    }

    public boolean isSendRole() {
        return sendRole;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }


    public String getClientName() {
        return clientName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void sendMessage(Message message) {
        try {
            output.writeObject(message);
        } catch (SocketException e) {
            System.out.println("socket exception");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message receiveMessage() {
        Message message = null;
        try {
            message = (Message) input.readObject();
        } catch (SocketException e) {
            System.out.println("Socket exception");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    private void sendToOthers(Message message) {
        for (ClientHandler c : clients) {
            if (c != this)
                c.sendMessage(message);
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
            if (c.isLoggedIn) {
                s += i + ". " + c.getClientName() + " ";
            }
            i++;
        }
        return s;
    }

    public static ClientHandler isClientName(String text) {
        for (ClientHandler c : clients) {
            if (c.isAlive) {
                if (c.getClientName().equals(text)) {
                    return c;
                }
            }
        }
        return null;
    }

    public static ClientHandler isMafiaName(String text) {
        for (ClientHandler c : clients) {
            if (c.getClientName().equals(text) && Role.isMafia(c.getRole()) && c.isAlive) {
                return c;
            }
        }
        return null;
    }

    public static ClientHandler isVillagerName(String text) {
        for (ClientHandler c : clients) {
            if (c.isAlive) {
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
            } else if (gameMode == Mode.ResultOfVote) {
                sleepThread(1000);
                // the game will handle this part
            } else if (gameMode == Mode.MayorTime) {
                if (!mayorIntro)
                    mayorTimeIntro();
                character.getMayorTimeBehaviour().run();
                sleepThread(1000);
            } else if (gameMode == Mode.RemoveByVote) {
                sleepThread(1000);
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
        System.out.println("send the role to " + clientName);
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
                "ute last, if you get ready for the voting at any time just enter (ready) and wait for " +
                "the others"));
        sleepThread(2000);

        for (ClientHandler c : clients)
            c.isReady = false;

        if (isSilent) { // TODO i think that you should consider other factors for not chatting
            isReady = true;
            sendMessage(new Message("God", "You can not chat"));
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

    private void waitTillRemove() { // the removed one also use this
        Message message = receiveMessage();
        if (gameMode == Mode.RemoveByVote) {
            sendMessage(new Message("God", "Wait"));
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

}
