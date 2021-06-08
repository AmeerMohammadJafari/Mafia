package com.company;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

public class Game implements Runnable {

    private Vector<ClientHandler> clients;
    private Vector<ClientHandler> mafias;
    private Vector<ClientHandler> villagers;
    private int numberOfClients;


    public Game(Vector<ClientHandler> clients, int numberOfClients) {
        this.clients = clients;
        this.numberOfClients = numberOfClients;

        // checks that all the roles have distributed and then create the game
       /* synchronized (clients) {
            while (true) {
                int flag = 1;
                for (ClientHandler c : clients) {
                    if (c.getRole() == null)
                        flag = 0;
                }
                if (flag == 1)
                    break;
            }
        }
        for (ClientHandler c : clients) {
            if (Roles.isMafia(c.getRole()))
                mafias.add(c);
            else
                villagers.add(c);
        }*/
    }


    // setting roles to the clients
    public void setRoles() {
        ArrayList<Roles> roles = new ArrayList<>();
        int mafiasNum = numberOfClients / 3;
        int villagersNum = numberOfClients - mafiasNum;
        roles.add(Roles.GodFather);
        roles.add(Roles.DoctorLecter);
        roles.add(Roles.Doctor);
        roles.add(Roles.Detective);
        roles.add(Roles.Sniper);
        roles.add(Roles.Mayor);
        roles.add(Roles.Psychologist);
        roles.add(Roles.Diehard);
        for (int i = 0; i < mafiasNum - 2; i++) {
            roles.add(Roles.SimpleMafia);
        }
        for (int i = 0; i < villagersNum - 6; i++) {
            roles.add(Roles.SimpleVillager);
        }
        Collections.shuffle(roles);
        for (int i = 0; i < numberOfClients; i++) {
            clients.get(i).setRole(roles.get(i));
        }
        for (ClientHandler c : clients)
            if (c.getRole() == Roles.Diehard)
                c.setHealth(2);
    }

    private void notifyAllClients() {
        for (ClientHandler c : clients) {
            synchronized (c) {
                c.notify();
            }
        }
    }

    private void waitAllClients() throws InterruptedException {
        for (ClientHandler c : clients)
            synchronized (c) {
                c.wait();
            }
    }

    private void waitGame() {
        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private synchronized boolean clientsWaiting() {
        synchronized (clients) {
            for (ClientHandler c : clients) {
                synchronized (c) {
                    if (c.getState() != Thread.State.WAITING /*&& c.getState() != Thread.State.TIMED_WAITING*/)
                        return false;
                }
            }
            return true;
        }
    }


    private void checkAllWaitingHandler() {
        /*while (true) {
            if (clientsWaiting())
                break;
        }*/
        // TODO this part is modified, take care
        while (!clientsWaiting()) {

        }
    }

    private void chat() {
        final boolean[] isDone = {false};
        checkAllWaitingHandler();
        notifyAllClients();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                endChatForHandlers();
                isDone[0] = true;
            }
        };
        timer.schedule(timerTask, 30 * 1000);


        while (!isDone[0]) {
            if (allClientReady()) {
                endChatForHandlers();
                timer.cancel();
                break;
            }
        }
    }

    private boolean allClientReady() {
        for (ClientHandler c : clients) {
            if (!c.isReady())
                return false;
        }
        return true;
    }

    private synchronized void endChatForHandlers() {
        synchronized (clients) {
            for (ClientHandler c : clients) {
                synchronized (c) {
                    c.setDayChat(false);
                }
            }
        }
    }

    private synchronized void interruptVoteThreads() {
        synchronized (clients) {
            for (ClientHandler c : clients) {
                synchronized (c) {
                    c.getVoteThread().interrupt();
                }
            }
        }
    }

    private void vote() {
        notifyAllClients();
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                interruptVoteThreads();
            }
        };

        timer.schedule(timerTask, 30 * 1000);

    }


    // the game will go forward by checking handlers waiting state in each step
    @Override
    public void run() {
        // at first set the roles of clients, but checks if all clientHandlers are waiting
        checkAllWaitingHandler();
        // set the roles to the clientHandlers
        setRoles();
        // then notify clientHandlers to send message to clients about their roles
        notifyAllClients();
        // check for waiting state
        checkAllWaitingHandler();
        // notify all and for 5 min at last set the game time in the dayChat
        chat();
        // check for waiting state
        checkAllWaitingHandler();
        // notify all and kill the threads after 30 seconds
        vote();


    }
}
