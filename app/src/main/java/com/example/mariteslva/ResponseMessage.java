package com.example.mariteslva;

/**
 * A class that contains messages used as response to users
  */

public class  ResponseMessage {
    String message; //contains the messages
    boolean marites; //to check if the user is the bot or a person interacting with the bot

    // A constructor methods
    public ResponseMessage(String message, boolean marites) {
        this.message = message;
        this.marites = marites;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMarites() {
        return marites;
    }

    public void setMarites(boolean marites) {
        this.marites = marites;
    }
}
