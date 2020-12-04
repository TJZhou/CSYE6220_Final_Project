package edu.neu.csye6220.models.enums;

public enum Status {
    CREATE_USER_SUCCESS(2000, "Successfully create a user"),
    GET_USER_SUCCESS(2001, "Successfully get a user info"),
    LOGIN_SUCCESS(2002, "Login Successfully"),
    UPDATE_USER_INFO_SUCCESS(2003, "Successfully update user information"),
    UPDATE_USER_PSWD_SUCCESS(2004, "Successfully update password"),
    GET_INCOMES_SUCCESS(2010, "Successfully get incomes"),
    USER_NOT_FOUND(4000, "User Not Found"),
    USER_ALREADY_EXISTS(4001, "User already exists, please login"),
    INVALID_CREDENTIAL(4002, "Invalid Credential"),
    CONSTRAINT_VIOLATION(4003, "Inputs violate validation");

    private final int code;
    private final String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.message;
    }
}
