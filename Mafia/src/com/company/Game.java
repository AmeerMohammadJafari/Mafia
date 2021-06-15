package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * The Game which controls the ClientHandlers and move the game forward
 */
public class Game extends Thread {

    private Vector<ClientHandler> clients;
    private Vector<ClientHandler> mafias;
    private Vector<ClientHandler> villagers;
    private static int numberOfClients;
    private ClientHandler removedByVote;
    private boolean mayorConfirmation;
    private ArrayList<Role> removedRoles;
    private ClientHandler godFatherChoice;
    private ClientHandler doctorLecterChoice;
    private ClientHandler doctorChoice;
    private ClientHandler sniperChoice;
    private ClientHandler psychologistChoice;
    private boolean diehardAct;

    /**
     * Instantiates a new Game.
     *
     * @param clients         the clients
     * @param numberOfClients the number of clients
     */
    public Game(Vector<ClientHandler> clients, int numberOfClients) {
        this.clients = clients;
        Game.numberOfClients = numberOfClients;
        removedByVote = null;
        villagers = new Vector<>();
        mafias = new Vector<>();
        removedRoles = new ArrayList<>();
        for(ClientHandler c : clients){
            c.setRemovedRoles(removedRoles);
        }
    }

    /**
     * Sets diehard act.
     *
     * @param diehardAct the diehard act
     */
    public void setDiehardAct(boolean diehardAct) {
        this.diehardAct = diehardAct;
    }

    /**
     * Sets psychologist choice.
     *
     * @param psychologistChoice the psychologist choice
     */
    public void setPsychologistChoice(ClientHandler psychologistChoice) {
        this.psychologistChoice = psychologistChoice;
    }

    /**
     * Gets removed roles.
     *
     * @return the removed roles
     */
    public ArrayList<Role> getRemovedRoles() {
        return removedRoles;
    }

    /**
     * Sets sniper choice.
     *
     * @param sniperChoice the sniper choice
     */
    public void setSniperChoice(ClientHandler sniperChoice) {
        this.sniperChoice = sniperChoice;
    }

    /**
     * Sets doctor choice.
     *
     * @param doctorChoice the doctor choice
     */
    public void setDoctorChoice(ClientHandler doctorChoice) {
        this.doctorChoice = doctorChoice;
    }

    /**
     * Sets doctor lecter choice.
     *
     * @param doctorLecterChoice the doctor lecter choice
     */
    public void setDoctorLecterChoice(ClientHandler doctorLecterChoice) {
        this.doctorLecterChoice = doctorLecterChoice;
    }

    /**
     * Sets god father choice.
     *
     * @param godFatherChoice the god father choice
     */
    public void setGodFatherChoice(ClientHandler godFatherChoice) {
        this.godFatherChoice = godFatherChoice;
    }

    /**
     * Gets clients.
     *
     * @return the clients
     */
    public Vector<ClientHandler> getClients() {
        return clients;
    }

    /**
     * Sets mayor confirmation.
     *
     * @param mayorConfirmation the mayor confirmation
     */
    public void setMayorConfirmation(boolean mayorConfirmation) {
        this.mayorConfirmation = mayorConfirmation;
    }

    private void sleepThread(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setModeForAll(Mode mode) {
        for (ClientHandler c : clients) {
            if (c.isAliveClient())
                c.setGameMode(mode);
        }
    }

    /**
     * Mafias list string.
     *
     * @return the string
     */
    public String mafiasList() {
        String list = "";
        int i = 1;
        for (ClientHandler c : clients) {
            synchronized (c) {
                if (Role.isMafia(c.getRole()) && c.isAliveClient()) {
                    list += i + "." + c.getClientName() + " ";
                    i++;
                }
            }
        }
        return list;
    }

    /**
     * Villagers list string.
     *
     * @return the string
     */
    public String villagersList() {
        String list = "";
        int i = 1;
        for (ClientHandler c : clients) {
            synchronized (c) {
                if (!Role.isMafia(c.getRole()) && c.isAliveClient()) {
                    list += i + "." + c.getClientName() + " ";
                    i++;
                }
            }
        }
        return list;
    }

    /**
     * Clients list string.
     *
     * @return the string
     */
    public String clientsList() {
        String list = "";
        int i = 1;
        for (ClientHandler c : clients) {
            synchronized (c) {
                if (c.isAliveClient()) {
                    list += i + "." + c.getClientName() + " ";
                    i++;
                }
            }
        }
        return list;
    }

    private String removedList() {
        String list = "";
        int i = 1;
        for (Role role : removedRoles) {
            list += i + "." + role + " ";
            i++;
        }
        return list;
    }

    private boolean allAreLoggedIn() {
        if (clients.size() != numberOfClients) {
            return false;
        }

        for (ClientHandler c : clients) {
            synchronized (c) {
                if (c.isAliveClient()) {
                    if (!c.isLoggedIn())
                        return false;
                }
            }
        }
        return true;
    }

    private void loopUntilAllLoggedIn() {
        while (!allAreLoggedIn()) {
            sleepThread(2000);
        }
    }

    private boolean allSendRole() {
        for (ClientHandler c : clients) {
            if (!c.isSendRole() && c.isAliveClient())
                return false;
        }
        return true;
    }

    private void loopUntilAllSendRole() {
        sleepThread(1000);
        while (!allSendRole()) {
            sleepThread(2000);
        }
    }

    private boolean allIntroduced() {
        for (ClientHandler c : clients) {
            synchronized (c) {
                if (!c.isIntroduced() && c.isAliveClient())
                    return false;
            }
        }
        return true;
    }

    private void loopUntilAllIntroduced() {
        while (!allIntroduced()) {
            sleepThread(2000);
        }
    }

    private boolean allClientReady() {
        for (ClientHandler c : clients) {
            if (c.isAliveClient() && !c.isSilent())
                if (!c.isReady())
                    return false;
        }
        return true;
    }

    private boolean allInMode(Mode mode) {
        for (ClientHandler c : clients) {
            synchronized (c) {
                if (c.getGameMode() != mode && c.isAliveClient())
                    return false;
            }
        }
        return true;
    }

    private void canOnlyWatch(ClientHandler c) {
        c.sendMessage(new Message("God", "So you can only watch"));
        c.setAliveClient(false);
        c.setGameMode(Mode.CanOnlyWatch);
        removedRoles.add(c.getRole());
    }

    private void outOfGame(ClientHandler c) {
        c.setAliveClient(false);
        c.setGameMode(Mode.OutOfGame);
        removedRoles.add(c.getRole());

        // closes the socket
        try {
            c.getSocket().close();
        } catch (IOException e) {

        }
    }

    /**
     * Sets roles.
     */
    public void setRoles() {

        while (numberOfClients != clients.size()) {
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

        for (ClientHandler c : clients) {
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

    private void sendToAll(Message message) {
        for (ClientHandler c : clients) {
            synchronized (c) {
                try {
                    c.getOutput().writeObject(message);
                } catch (IOException e) {

                }
            }
        }
    }

    private boolean allChatIntro() {
        for (ClientHandler c : clients) {
            synchronized (c) {
                if (c.isAliveClient()) {
                    if (!c.isChatStarted())
                        return false;
                }
            }
        }
        return true;
    }

    private void loopUntilChatIntro() {
        while (!allChatIntro()) {
            sleepThread(1000);
        }
    }

    private boolean allMafiasVoteDone() {
        for (ClientHandler c : clients) {
            if (c.isAliveClient() && !c.getCharacter().getMafiasVoteTimeBehaviour().behaviourDone &&
                    c.getCharacter().getMafiasVoteTimeBehaviour() instanceof MafiasVoteTreat)
                return false;
        }
        return true;
    }


    private void endChat() {

        loopUntilChatIntro();
        boolean[] isDone = {false};
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                setModeForAll(Mode.Vote);
                sleepThread(500);
                sendToAll(new Message("God", "The chat is over."));
                sendToAll(new Message("God", "enter")); // for sticking
                isDone[0] = true;
            }
        };
        timer.schedule(timerTask, 5 * 60 * 1000);

        while (!isDone[0]) {
            if (allClientReady()) {
                setModeForAll(Mode.Vote);
                sleepThread(500);
                sendToAll(new Message("God", "The chat is over."));
                sendToAll(new Message("God", "enter")); // for sticking
                timer.cancel();
                break;
            }
            sleepThread(1000);
        }
        sleepThread(3000);
    }

    private boolean allVoteStart() {
        for (ClientHandler c : clients) {
            synchronized (c) {
                if (c.isAliveClient()) {
                    if (!c.isVoteStarted())
                        return false;
                }
            }
        }
        return true;
    }


    private void loopUntilVoteStart() {
        while (!allVoteStart()) {
            sleepThread(1000);
        }
    }

    private void endVote() {

        loopUntilVoteStart();

        boolean[] isDone = {false};
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                setModeForAll(Mode.ResultOfVote);
                sleepThread(500);
                sendToAll(new Message("God", "The vote is over."));
                sendToAll(new Message("God", "enter")); // for sticking
                isDone[0] = true;
            }
        };
        timer.schedule(timerTask, 30 * 1000);


        // a loop for not going forward to result vote until the mode is changed
        while (!isDone[0]) {
            sleepThread(1000);
        }
        //
        sleepThread(3000);

    }

    private void resultOfVote() {

        sendToAll(new Message("God", "The result will be shown."));
        sleepThread(3000);

        HashMap<ClientHandler, Integer> voteMap = new HashMap<>();

        for (ClientHandler c : clients) {
            synchronized (c) {
                if (c.isAliveClient()) {
                    voteMap.put(c, 0);
                }
            }
        }

        for (ClientHandler c : clients) {
            synchronized (c) {
                try {
                    if (c.isAliveClient()) {
                        voteMap.put(c.getMyVote(), voteMap.get(c.getMyVote()) + 1);
                    }
                } catch (NullPointerException e) {

                }
            }
        }


        int maxVote = Collections.max(voteMap.values());
        for (ClientHandler c : voteMap.keySet()) {
            if (voteMap.get(c) == maxVote) {
                removedByVote = c;
                break;
            }
        }


        assert removedByVote != null;
        sendToAll(new Message("God", removedByVote.getClientName() + " has the most votes"));
        setModeForAll(Mode.MayorTime);
        sleepThread(1000);
    }

    // have to consider the death of every character


    /**
     * Mayor time.
     */
    public void mayorTime() {

        ClientHandler mayor = null;

        for (ClientHandler c : clients) {
            if (c.getRole() == Role.Mayor)
                mayor = c;
        }

        // do like the mayor do not confirm
        if (!mayor.isAliveClient()) {
            mayor.getCharacter().getMayorTimeBehaviour().setBehaviourDone(true);
            mayorConfirmation = false;
            sleepThread(10 * 1000);
        }

        Character character = null;
        try {
            character = mayor.getCharacter();
        } catch (NullPointerException e) {
        }

        // loop until mayor done his behaviour
        while (!character.getMayorTimeBehaviour().behaviourDone) {
            sleepThread(1000);
        }

        if (mayorConfirmation) {
            sendToAll(new Message("God", "The mayor confirmed the vote"));
            sleepThread(1000);
            sendToAll(new Message("God", removedByVote.getClientName() + " is removed."));
            setModeForAll(Mode.Remove);
        } else {
            sendToAll(new Message("God", "The mayor rejects the voting"));
            sleepThread(1000);
            sendToAll(new Message("God", "So " + removedByVote.getClientName() + " is still " +
                    "with us"));
            removedByVote = null;
            setModeForAll(Mode.MafiasVote);
        }
        sleepThread(500);

        for (ClientHandler c : clients) { // for sticking
            synchronized (c) {
                if (c.getCharacter().getMayorTimeBehaviour() instanceof NonMayorTreat) {
                    c.sendMessage(new Message("God", "enter"));
                }
            }
        }
        sleepThread(500);
        sendToAll(new Message("God", "MayorTime ends."));
        sleepThread(3000);
    }

    private void removeByVote() {
        // the game will enter this method if there is a removedByVote client
        sleepThread(1000);
        if (allInMode(Mode.Remove)) {
            removeFromGame(removedByVote);
            if (removedByVote.getRole() == Role.GodFather) {
                ClientHandler clientHandler = null;
                for (ClientHandler c : clients) {
                    if (Role.isMafia(c.getRole()) && c.isAliveClient()) {
                        clientHandler = c;
                        break;
                    }
                }
                Character character = clientHandler.getCharacter();
                character.setGodFatherTimeBehaviour(new GodFatherTreat(character));
            }
            removedByVote = null;
            setModeForAll(Mode.MafiasVote);
        }
    }


    private void removeFromGame(ClientHandler c) {
        sendToAll(new Message("God", "Wait for " + c.getClientName()));
        c.sendMessage(new Message("God", "You are removed from the game :("));
        sleepThread(1000);
        c.sendMessage(new Message("God", "If you still want to follow the game " +
                "events, enter (follow) else enter (quit) if you want to quit the game"));

        while (true) {
            Message message = c.receiveMessage();
            if (message.getText().equals("follow")) {
                canOnlyWatch(c);
                break;
            } else if (message.getText().equals("quit")) {
                outOfGame(c);
                break;
            } else {
                c.sendMessage(new Message("God", ":|"));
            }
        }
    }

    private void endConsult() {
        // wait till all the mafias consult
        while (!allMafiasVoteDone()) {
            sleepThread(1000);
        }

        // we should check the godFather is alive or not, then go to the next part
        ClientHandler clientHandler = null;
        for (ClientHandler c : clients) {
            if (c.getCharacter().getGodFatherTimeBehaviour() instanceof GodFatherTreat && c.isAliveClient()) {
                clientHandler = c;
            }
        }

        // set godFather treat to another mafia
        if (clientHandler == null) {
            for (ClientHandler c : clients) {
                if (Role.isMafia(c.getRole()) && c.isAliveClient()) {
                    clientHandler = c;
                    break;
                }
            }
            Character character = clientHandler.getCharacter();
            character.setGodFatherTimeBehaviour(new GodFatherTreat(character));
        }

        setModeForAll(Mode.GodFatherTime);

        for (ClientHandler c : clients) {
            synchronized (c) {
                if (c.getCharacter().getMafiasVoteTimeBehaviour() instanceof NonMafiasTreat &&
                        c.isAliveClient()) {
                    c.sendMessage(new Message("God", "enter"));
                }
            }
        }
        sendToAll(new Message("God", "Consult time ends"));
        sleepThread(1000);
    }

    private void godFather() {

        sendToAll(new Message("God", "The GodFatherTime"));

        ClientHandler godFather = null;
        for (ClientHandler c : clients) {
            if (c.getCharacter().getGodFatherTimeBehaviour() instanceof GodFatherTreat && c.isAliveClient())
                godFather = c;
        }
        // loop until godFather done his work
        while (!godFather.getCharacter().getGodFatherTimeBehaviour().behaviourDone) {
            sleepThread(1000);
        }

        sendToAll(new Message("God", "GodFather chooses someone."));
        setModeForAll(Mode.DoctorLecterTime);
        for (ClientHandler c : clients) {
            synchronized (c) {
                if (c.getCharacter().godFatherTimeBehaviour instanceof NonGodFatherTreat && c.isAliveClient()) {
                    c.sendMessage(new Message("God", "enter"));
                }
            }
        }
        sleepThread(3000);
    }

    private void doctorLecter() {

        sendToAll(new Message("God", "DoctorLecter Time"));
        ClientHandler doctorLecter = null;
        for (ClientHandler c : clients) {
            if (c.getRole() == Role.DoctorLecter)
                doctorLecter = c;
        }

        // handling dead dr lecter
        if (!doctorLecter.isAliveClient()) {
            doctorLecter.getCharacter().getDoctorLecterTimeBehaviour().setBehaviourDone(true);
            doctorLecterChoice = null;
            sleepThread(10 * 1000);
        }

        // loop until the doctor done his work
        while (!doctorLecter.getCharacter().getDoctorLecterTimeBehaviour().behaviourDone) {
            sleepThread(1000);
        }
        sendToAll(new Message("God", "DoctorLecter chooses someone."));
        setModeForAll(Mode.DoctorTime);
        for (ClientHandler c : clients) {
            synchronized (c) {
                if (c.getCharacter().getDoctorLecterTimeBehaviour() instanceof NonDoctorLecterTreat &&
                        c.isAliveClient()) {
                    c.sendMessage(new Message("God", "enter"));
                }
            }
        }
        sleepThread(3000);
    }

    private void doctor() {

        sendToAll(new Message("God", "Doctor Time"));
        ClientHandler doctor = null;
        for (ClientHandler c : clients) {
            if (c.getRole() == Role.Doctor)
                doctor = c;
        }

        // handle dead doctor
        if (!doctor.isAliveClient()) {
            doctor.getCharacter().getDoctorTimeBehaviour().setBehaviourDone(true);
            doctorChoice = null;
            sleepThread(10 * 1000);
        }

        // loop until the doctor done his work
        while (!doctor.getCharacter().getDoctorTimeBehaviour().behaviourDone) {
            sleepThread(1000);
        }
        sendToAll(new Message("God", "Doctor chooses someone."));
        setModeForAll(Mode.DetectiveTime);
        for (ClientHandler c : clients) {
            synchronized (c) {
                if (c.getCharacter().getDoctorTimeBehaviour() instanceof NonDoctorTreat &&
                        c.isAliveClient()) {
                    c.sendMessage(new Message("God", "enter"));
                }
            }
        }
        sleepThread(3000);
    }


    private void detective() {
        sendToAll(new Message("God", "Detective Time"));
        ClientHandler detective = null;
        for (ClientHandler c : clients) {
            if (c.getRole() == Role.Detective)
                detective = c;
        }

        // handling dead detective
        if (!detective.isAliveClient()) {
            detective.getCharacter().getDetectiveTimeBehaviour().setBehaviourDone(true);
            sleepThread(10 * 1000);
        }


        // loop until the doctor done his work
        while (!detective.getCharacter().getDetectiveTimeBehaviour().behaviourDone) {
            sleepThread(1000);
        }
        sendToAll(new Message("God", "Detective done his work."));
        setModeForAll(Mode.SniperTime);
        for (ClientHandler c : clients) {
            synchronized (c) {
                if (c.getCharacter().getDetectiveTimeBehaviour() instanceof NonDetectiveTreat) {
                    c.sendMessage(new Message("God", "enter"));
                }
            }
        }
        sleepThread(3000);
    }


    private void sniper() {
        sendToAll(new Message("God", "Sniper Time"));
        ClientHandler sniper = null;
        for (ClientHandler c : clients) {
            if (c.getRole() == Role.Sniper)
                sniper = c;
        }

        // handling dead sniper
        if (!sniper.isAliveClient()) {
            sniper.getCharacter().getSniperTimeBehaviour().setBehaviourDone(true);
            sniperChoice = null;
            sleepThread(10 * 1000);
        }

        while (!sniper.getCharacter().getSniperTimeBehaviour().behaviourDone) {
            sleepThread(1000);
        }
        sendToAll(new Message("God", "Sniper done his work."));
        setModeForAll(Mode.PsychologistTime);
        for (ClientHandler c : clients) {
            synchronized (c) {
                if (c.getCharacter().getSniperTimeBehaviour() instanceof NonSniperTreat) {
                    c.sendMessage(new Message("God", "enter"));
                }
            }
        }
        sleepThread(3000);
    }


    private void psychologist() {
        sendToAll(new Message("God", "Psychologist Time"));
        ClientHandler psycho = null;
        for (ClientHandler c : clients) {
            if (c.getRole() == Role.Psychologist)
                psycho = c;
        }

        // handling dead psycho
        if (!psycho.isAliveClient()) {
            psycho.getCharacter().getPsychologistTimeBehaviour().setBehaviourDone(true);
            psychologistChoice = null;
            sleepThread(10 * 1000);
        }

        while (!psycho.getCharacter().getPsychologistTimeBehaviour().behaviourDone) {
            sleepThread(1000);
        }
        sendToAll(new Message("God", "Psychologist did his work."));
        setModeForAll(Mode.DieHardTime);
        for (ClientHandler c : clients) {
            synchronized (c) {
                if (c.getCharacter().getPsychologistTimeBehaviour() instanceof NonPsychologistTreat) {
                    c.sendMessage(new Message("God", "enter"));
                }
            }
        }
        sleepThread(500);
    }

    private void diehard() {
        sendToAll(new Message("God", "Diehard Time"));
        ClientHandler diehard = null;
        for (ClientHandler c : clients) {
            if (c.getRole() == Role.Diehard)
                diehard = c;
        }

        // handling dead diehard
        if (!diehard.isAliveClient()) {
            diehard.getCharacter().getDiehardTimeBehaviour().setBehaviourDone(true);
            diehardAct = false;
            sleepThread(10 * 1000);
        }


        while (!diehard.getCharacter().getDiehardTimeBehaviour().behaviourDone) {
            sleepThread(1000);
        }

        sendToAll(new Message("God", "Diehard did his work."));
        setModeForAll(Mode.EndOfNight);
        for (ClientHandler c : clients) {
            synchronized (c) {
                if (c.getCharacter().getDiehardTimeBehaviour() instanceof NonDiehardTreat) {
                    c.sendMessage(new Message("God", "enter"));
                }
            }
        }
        sleepThread(3000);
    }


    private void endOfTheNight() {
        sendToAll(new Message("God", "The end of the night, wait for the results"));
        sleepThread(1000);
        // see who should be killed or not

        if (godFatherChoice == doctorChoice) {
            godFatherChoice = null;
        }


        // if the sniper chose villager, should be the sniperChoice himself
        try {
            if (!Role.isMafia(sniperChoice.getRole())) {
                for (ClientHandler c : clients) {
                    if (c.getRole() == Role.Sniper && c.isAliveClient()) {
                        sniperChoice = c;
                        break;
                    }
                }
            }
        } catch (NullPointerException ignored) {

        }


        if (sniperChoice == doctorLecterChoice)
            sniperChoice = null;


        if (psychologistChoice != null) {
            sleepThread(1000);
            sendToAll(new Message("God", psychologistChoice.getClientName() + " is silent"));
            psychologistChoice.setSilent(true);
        }

        if (godFatherChoice != null) {
            godFatherChoice.lowHealth();
            if (godFatherChoice.getHealth() == 0) {
                sleepThread(1000);
                sendToAll(new Message("God", godFatherChoice.getClientName() + " was killed last night."));
                sleepThread(1000);
               /* setModeForAll(Mode.Remove);*/
                removeFromGame(godFatherChoice);
                setModeForAll(Mode.EndOfNight);
            }
        }

        if (sniperChoice != null) {
            sniperChoice.lowHealth();
            if (sniperChoice.getHealth() == 0) {
                sleepThread(1000);
                sendToAll(new Message("God", sniperChoice.getClientName() + " was killed last night."));
               /* setModeForAll(Mode.Remove);*/
                removeFromGame(sniperChoice);
                setModeForAll(Mode.EndOfNight);
            }
        }

        if (diehardAct) {
            sleepThread(1000);
            sendToAll(new Message("God", "Diehard use his act last night"));
            sleepThread(1000);
            sendToAll(new Message("God", "Removed Roles are :"));
            sleepThread(1000);
            sendToAll(new Message("God", removedList()));
            sleepThread(3000);
        }


        if (godFatherChoice == null && sniperChoice == null && psychologistChoice == null && !diehardAct) {
            sendToAll(new Message("God", "Nothing happens apparently"));
        }


        reset();
        sleepThread(3000);
        setModeForAll(Mode.DayChatroom);
    }

    private void reset() {
        for (ClientHandler c : clients) {
            c.setReady(false);
            // silent false after chat
            c.setChatStarted(false);
            c.setVoteStarted(false);
            c.setMyVote(null);
            c.setMayorIntro(false);
            c.setConsultStarted(false);
            c.getCharacter().reset();
        }
        removedByVote = null;
        mayorConfirmation = false;
        godFatherChoice = null;
        doctorLecterChoice = null;
        doctorChoice = null;
        sniperChoice = null;
        psychologistChoice = null;
        diehardAct = false;

    }


    private boolean gameOver() {
        int mafias = 0;
        int villagers = 0;
        for (ClientHandler c : clients) {
            if (c.isAliveClient()) {
                if (Role.isMafia(c.getRole()))
                    mafias++;
                else
                    villagers++;
            }
        }
        if (mafias == 0) {
            setModeForAll(Mode.VillagersWin);
            return true;
        }
        if (mafias >= villagers) {
            setModeForAll(Mode.MafiasWin);
            return true;
        }
        return false;

    }

    @Override
    public void run() {

        loopUntilAllLoggedIn();

        setRoles();

        setModeForAll(Mode.SendRoll);
        /* ClientHandler.setMode(Mode.SendRoll);*/

        loopUntilAllSendRole();

        setModeForAll(Mode.Introduction);
        /* ClientHandler.setMode(Mode.Introduction);*/

        loopUntilAllIntroduced();

        setModeForAll(Mode.DayChatroom);
        /* ClientHandler.setMode(Mode.DayChatroom);*/
        while (true) {

            endChat(); // it sets the game mode to vote internally

            endVote(); // it sets the game to the resultOfVote internally

            resultOfVote();

            mayorTime();

            removeByVote(); // enter this method when a client is removedByVote

            if (gameOver())
                break;

            endConsult();

            godFather();

            doctorLecter();

            doctor();

            detective();

            sniper();

            psychologist();

            diehard();

            endOfTheNight();

            if (gameOver())
                break;
        }

    }
}
