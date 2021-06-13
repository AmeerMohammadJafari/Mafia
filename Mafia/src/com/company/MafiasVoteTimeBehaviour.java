package com.company;

import java.util.HashMap;

public abstract class MafiasVoteTimeBehaviour extends Behaviour{

    protected static HashMap<ClientHandler, ClientHandler> voteMap;

    public MafiasVoteTimeBehaviour(Character character) {
        super(character);
        voteMap = new HashMap<>();
    }

    public static HashMap<ClientHandler, ClientHandler> getVoteMap() {
        return voteMap;
    }
}
