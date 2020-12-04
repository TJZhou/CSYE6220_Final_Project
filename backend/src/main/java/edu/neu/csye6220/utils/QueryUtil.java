package edu.neu.csye6220.utils;

public class QueryUtil {
    public static final String GET_USER_BY_EMAIL =
            "select * from user where email = :email";

    public static final String GET_INCOMES =
            "select * from income i where i.user_id = :userId and i.date like :date";

    public static final String GET_EXPENSES =
            "select * from expense e where e.user_id = :userId and e.date like :date";
}
