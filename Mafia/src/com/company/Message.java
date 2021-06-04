package com.company;

import java.io.Serializable;

public class Message implements Serializable {
   // private String type
    private String name;
    private String text;

    public Message( String name, String text){

        this.name = name;
        this.text = text;
    }

    public Message(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }
}
