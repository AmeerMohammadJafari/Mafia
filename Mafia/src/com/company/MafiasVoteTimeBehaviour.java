package com.company;

import java.util.HashMap;

/**
 * The MafiasVoteTimeBehaviour class, used for mafiasVoteTime behaviour
 */
public abstract class MafiasVoteTimeBehaviour extends Behaviour{

    /**
     * The Vote map.
     */
    protected static HashMap<ClientHandler, ClientHandler> voteMap;

    /**
     * Instantiates a new Mafias vote time behaviour.
     *
     * @param character the character
     */
    public MafiasVoteTimeBehaviour(Character character) {
        super(character);
        voteMap = new HashMap<>();
    }

    public static void setVoteMap(HashMap<ClientHandler, ClientHandler> voteMap) {
        MafiasVoteTimeBehaviour.voteMap = voteMap;
    }

    /**
     * Gets vote map.
     *
     * @return the vote map
     */
    public static HashMap<ClientHandler, ClientHandler> getVoteMap() {
        return voteMap;
    }
}
