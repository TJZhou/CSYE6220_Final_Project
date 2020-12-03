package edu.neu.csye6220.dao;

public class CustomQuery {
    public static final String GET_USER_BY_ID = "select * from User where user_id =:id";
    public static final String GET_USER_BY_EMAIL = "select * from User where email=:email";

}
