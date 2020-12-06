package edu.neu.csye6220.utils;

public class QueryUtil {
    public static final String GET_USER_BY_EMAIL =
            "select * from user where email = :email";

    public static final String GET_INCOMES =
            "select * from income i where i.user_id = :userId and i.date like :date order by i.date desc";

    public static final String GET_EXPENSES =
            "select * from expense e where e.user_id = :userId and e.date like :date order by e.date desc";

    public static final String INCOMES_GROUP_BY_TYPE =
            "select i.type, SUM(i.amount) as sum from income i " +
                    "where i.user_id = :userId and i.date like :date group by i.type;";

    public static final String EXPENSES_GROUP_BY_TYPE =
            "select e.type, SUM(e.amount) as sum from expense e " +
                    "where e.user_id = :userId and e.date like :date group by e.type;";
}
