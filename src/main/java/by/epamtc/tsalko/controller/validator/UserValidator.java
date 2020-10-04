package by.epamtc.tsalko.controller.validator;

import by.epamtc.tsalko.bean.AuthorizationData;
import by.epamtc.tsalko.bean.RegistrationData;

public class UserValidator {

    private static final String LOGIN_REGEXP = "^[a-zA-Z0-9_-]{3,25}$";
    private static final String PASSWORD_REGEXP = "^[^\\s]{6,18}$";
    private static final String EMAIL_REGEXP = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6}$";
    private static final String PHONE_REGEXP = "^[0-9\\(\\)-]+$";

    public static boolean loginValidation(AuthorizationData data) {
        String login = data.getLogin();
        String password = data.getPassword();

        return login != null && password != null
                && login.matches(LOGIN_REGEXP) && password.matches(PASSWORD_REGEXP);
    }

    public static boolean registrationValidation(RegistrationData data) {
        String login = data.getLogin();
        String password = data.getPassword();
        String phone = data.getPhone();
        String email = data.getEmail();

        if (email != null && !email.matches(EMAIL_REGEXP)) {
            return false;
        }

        if (login != null && password != null && phone != null) {
            return login.matches(LOGIN_REGEXP) && password.matches(PASSWORD_REGEXP)
                    && phone.matches(PHONE_REGEXP);
        } else {
            return false;
        }
    }
}
