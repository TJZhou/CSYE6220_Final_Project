package edu.neu.csye6220.utils;

public class QueryUtil {
    public static final String GET_USER_BY_ID =
            "select * from user where user_id = :id";

    public static final String GET_USER_BY_EMAIL =
            "select * from user where email = :email";

    public static final String GET_INCOME =
            "select * from income where id = :id";

    public static final String GET_INCOMES =
            "select * from income i where i.user_id = :userId and i.date like :date";
}
