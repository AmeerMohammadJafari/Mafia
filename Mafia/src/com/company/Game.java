package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
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
                    if (c.getState() != Thread.State.WAITING && c.getState() != Thread.State.TIMED_WAITING)
                        return false;
                }
            }
            return true;
        }
    }


    private void allWaitingHandler() {
        while (true) {
            if (clientsWaiting())
                break;
        }
    }


    @Override
    public void run() {
        // at first set the roles of clients
        allWaitingHandler();
        setRoles();
        notifyAllClients();
        // the game will go forward by checking handlers waiting state in each step
    }
}
