package edu.neu.csye6220.exceptions;

public class UserNotFoundException extends RuntimeException {
    private final int code;
    public UserNotFoundException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
