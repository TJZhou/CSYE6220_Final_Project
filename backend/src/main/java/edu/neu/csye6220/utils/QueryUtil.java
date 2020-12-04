package edu.neu.csye6220.utils;

public class QueryUtil {
    public static final String GET_USER_BY_ID =
            "select * from user where user_id = :id";

    public static final String GET_USER_BY_EMAIL =
            "select * from user where email = :email";

    public static final String GET_INCOME =
            "select * from income i where i.user_id = :id and i.date like :date";
}
