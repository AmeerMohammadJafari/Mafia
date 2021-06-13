package com.company;

import java.util.HashMap;

public abstract class MafiasVoteTimeBehaviour extends Behaviour{

    protected ClientHandler myVote;

    public MafiasVoteTimeBehaviour(Character character) {
        super(character);
        myVote = null;
    }

    public ClientHandler getMyVote() {
        return myVote;
    }
}
