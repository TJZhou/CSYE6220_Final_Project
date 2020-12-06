package edu.neu.csye6220.exceptions;

public class CustomIllegalArgumentException extends RuntimeException{
    private final int code;
    public CustomIllegalArgumentException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
