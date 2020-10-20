package by.epamtc.tsalko.controller;

import by.epamtc.tsalko.bean.user.User;

public class UserVerifier {

    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_CUSTOMER = "customer";

    public static boolean isAdmin(User user) {
        return user.getRole().equals(ROLE_ADMIN);
    }

    public static boolean isCustomer(User user) {
        return user.getRole().equals(ROLE_CUSTOMER);
    }

    public static boolean isAdmin(User user, String userLogin) {
        return user.getRole().equals(ROLE_ADMIN) && user.getLogin().equals(userLogin);
    }

    public static boolean isCustomer(User user, String userLogin) {
        return user.getRole().equals(ROLE_CUSTOMER) && user.getLogin().equals(userLogin);
    }
}
