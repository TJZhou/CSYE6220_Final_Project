package edu.neu.csye6220.exceptions;

public class EntryNotFoundException extends RuntimeException{
    private final int code;
    public EntryNotFoundException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
