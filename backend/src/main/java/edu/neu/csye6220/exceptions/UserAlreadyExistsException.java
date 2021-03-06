package edu.neu.csye6220.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    private final int code;
    public UserAlreadyExistsException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
