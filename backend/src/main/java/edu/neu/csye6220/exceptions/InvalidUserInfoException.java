package edu.neu.csye6220.exceptions;

public class InvalidUserInfoException extends RuntimeException{
    private final int code;
    public InvalidUserInfoException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
