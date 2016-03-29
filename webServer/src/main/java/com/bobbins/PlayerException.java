package com.bobbins;

/**
 * Created by james on 28/03/2016.
 */
public class PlayerException extends Exception {

    public PlayerException(String message) {
        super(message);
    }

    public PlayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerException(Throwable cause) {
        super(cause);
    }
}
