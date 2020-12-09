package edu.neu.csye6220.models.enums;

public enum Status {
    CREATE_USER_SUCCESS(2000, "Successfully create a user"),
    GET_USER_SUCCESS(2001, "Successfully get a user info"),
    LOGIN_SUCCESS(2002, "Login Successfully"),
    UPDATE_USER_INFO_SUCCESS(2003, "Successfully update user information"),
    UPDATE_USER_PSWD_SUCCESS(2004, "Successfully update password"),

    GET_INCOMES_SUCCESS(2010, "Successfully get incomes"),
    CREATE_INCOME_SUCCESS(2011, "Successfully create an income"),
    UPDATE_INCOME_SUCCESS(2012, "Successfully update an income"),
    DELETE_INCOME_SUCCESS(2013, "Successfully delete an income"),
    GROUP_INCOMES_SUCCESS(2014, "Successfully get incomes and group by category"),

    GET_EXPENSES_SUCCESS(2020, "Successfully get expenses"),
    CREATE_EXPENSE_SUCCESS(2021, "Successfully create an expense"),
    UPDATE_EXPENSE_SUCCESS(2022, "Successfully update an expense"),
    DELETE_EXPENSE_SUCCESS(2023, "Successfully delete an expense"),
    GROUP_EXPENSES_SUCCESS(2024, "Successfully get expenses and group by category"),

    CREATE_GROUP_SUCCESS(2030, "Successfully create a group"),
    JOIN_GROUP_SUCCESS(2031, "Successfully join a group"),
    UPDATE_GROUP_SUCCESS(2032, "Successfully update group info"),
    DELETE_GROUP_SUCCESS(2033, "Successfully delete a group"),
    GET_GROUPS_SUCCESS(2034, "Successfully get all groups current participates in"),

    CREATE_BILL_SUCCESS(2040, "Successfully create a bill"),
    GET_BILLS_SUCCESS(2041, "Successfully get bills"),
    UPDATE_BILL_INFO_SUCCESS(2042, "Successfully update bill information"),
    UPDATE_BILL_PARTICIPANTS_SUCCESS(2043, "Successfully add participants to this bill"),
    DELETE_BILL_SUCCESS(2044, "Successfully delete a bill"),
    CALC_BILL_SUCCESS(2045, "Successfully calculate all bills within the bill group"),

    USER_NOT_FOUND(4000, "User not found"),
    USER_ALREADY_EXISTS(4001, "User already exists, please login"),
    INVALID_CREDENTIAL(4002, "Invalid Credential"),

    INCOME_NOT_FOUND(4010, "Income not found, please check your income id again"),
    EXPENSE_NOT_FOUND(4011, "Expense not found, please check your expense id again"),

    GROUP_NOT_FOUND(4020, "Group not found, please check your group id again"),
    GROUP_ALREADY_JOINED(4021, "You have already joined this group"),
    USER_NOT_IN_GROUP(4022, "Current user doesn't exist in bill group"),

    BILL_NOT_FOUND(4030, "Bill not found, please check your bill id again"),
    BILL_NOT_MATCH(4031, "Bill not match current user and group"),
    ENTRY_NOT_FOUND(4032, "Entry not found"),


    CONSTRAINT_VIOLATION(4100, "Inputs violate validation"),
    INVALID_REQUEST_BODY(4101, "Invalid request body"),

    SERVLET_EXCEPTION(5000, "Internal server exception, please contact admin for assistance"),
    OTHER_EXCEPTION(5100, "Some unknown exception occur, please contact admin for assistance");

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
