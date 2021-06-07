package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientReadOnly extends Thread{

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
                if(message.getName().equals("God") && message.getText().equals("The chat is over")){
                  /*  System.out.println("Enter something to continue");*/
                    return;
                }
            }
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }
}
