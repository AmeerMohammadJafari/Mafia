package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientReadOnly implements Runnable{

    private ObjectInputStream input;

    public ClientReadOnly(ObjectInputStream input){
        this.input = input;
    }

    @Override
    public void run() {

        try {
            while (true) {
                Message message = (Message) input.readObject();
                System.out.println(message.getName() + " : " + message.getText());
            }
        }
        catch (IOException | ClassNotFoundException e){

        }
    }
}
