package com.company;

import java.io.Serializable;

/**
 * The Message class which is used for transforming messages
 */
public class Message implements Serializable {
    private String name;
    private String text;

    /**
     * Instantiates a new Message.
     *
     * @param name the name
     * @param text the text
     */
    public Message( String name, String text){

        this.name = name;
        this.text = text;
    }

    /**
     * Instantiates a new Message.
     *
     * @param text the text
     */
    public Message(String text){
        this.text = text;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}
