package edu.neu.csye6220.utils;

public class RegexUtil {
    public static final String USERNAME_PATTERN =
            "[a-zA-Z0-9]+([_\\.-]?[a-zA-Z0-9])*";
    public static final String PASSWORD_PATTERN =
            "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}";
    public static final String EMAIL_PATTERN =
            "(([^<>()[\\]\\\\.,;:\\s@\"]+(\\.[^<>()[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))";
    public static final String PHONE_PATTERN =
            "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";

    public static final String INVALID_USERNAME =
            "Invalid username input format";

    public static final String INVALID_PASSWORD =
            "Invalid password input format";

    public static final String INVALID_EMAIL =
            "Invalid Email input format";

    public static final String INVALID_PHONE =
            "Invalid phone input format";

    public static final String INVALID_GROUP_NAME =
            "Invalid group name";

}
