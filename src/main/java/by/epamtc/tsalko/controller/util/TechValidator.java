package by.epamtc.tsalko.controller.util;

import by.epamtc.tsalko.bean.Bankcard;
import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.content.OrderStatus;
import by.epamtc.tsalko.bean.content.Rating;
import by.epamtc.tsalko.bean.content.Role;
import by.epamtc.tsalko.bean.user.*;

import java.time.LocalDate;

public class TechValidator {

    private static final String LOGIN_REGEXP = "^[^\\s$/()]{3,25}$";
    private static final String PASSWORD_REGEXP = "^[^\\s]{6,18}$";

    private static final String EMAIL_REGEXP = "^(?=.{3,30}$)[^\\s]+@[^\\s]+\\.[^\\s]+$";
    private static final String PHONE_REGEXP = "^[0-9\\s-+()]{7,30}$";

    private static final String PASSPORT_SERIES_REGEXP = "^[a-zA-Zа-яА-Я]{2}$";
    private static final String PASSPORT_NUMBER_REGEXP = "^[\\d]{7}$";
    private static final String PASSPORT_ISSUED_BY_REGEXP = ".{1,255}";
    private static final String PASSPORT_USER_ADDRESS_REGEXP = ".{1,255}";
    private static final String PASSPORT_USER_SURNAME_REGEXP = "^[a-zA-Zа-яА-Я]{3,50}$";
    private static final String PASSPORT_USER_NAME_REGEXP = "^[a-zA-Zа-яА-Я]{3,50}$";
    private static final String PASSPORT_USER_THIRDNAME_REGEXP = "^[a-zA-Zа-яА-Я]{3,50}$";

    private static final String CARD_USER_FIRSTNAME_REGEXP = "^[a-zA-Z]{3,50}$";
    private static final String CARD_USER_LASTNAME_REGEXP = "^[a-zA-Z]{3,50}$";

    public static boolean authValidation(AuthorizationData data) {
        String login = data.getLogin();
        String password = data.getPassword();

        return login != null && login.matches(LOGIN_REGEXP)
                && password != null && password.matches(PASSWORD_REGEXP);
    }

    public static boolean loginValidation(String login) {
        return login != null && login.matches(LOGIN_REGEXP);
    }

    public static boolean passwordValidation(String password) {
        return password != null && password.matches(PASSWORD_REGEXP);
    }

    public static boolean registrationValidation(RegistrationData data) {
        String login = data.getLogin();
        String password = data.getPassword();
        String phone = data.getPhone();
        String email = data.getEmail();

        return (email == null || email.length() == 0 || email.matches(EMAIL_REGEXP))
                && login != null && login.matches(LOGIN_REGEXP)
                && password != null && password.matches(PASSWORD_REGEXP)
                && phone != null && phone.matches(PHONE_REGEXP);
    }

    public static boolean userDetailsValidation(UserDetails userDetails) {
        Role role = userDetails.getUserRole();
        Rating rating = userDetails.getUserRating();
        String phone = userDetails.getUserPhone();
        String email = userDetails.getUserEmail();

        return (email == null || email.length() == 0 || email.matches(EMAIL_REGEXP))
                && phone != null && phone.matches(PHONE_REGEXP)
                && role != null
                && rating != null;
    }

    public static boolean passportValidation(Passport passport) {
        String passportSeries = passport.getPassportSeries();
        String passportNumber = passport.getPassportNumber();
        LocalDate passportDateOfIssue = passport.getPassportDateOfIssue();
        String passportIssuedBy = passport.getPassportIssuedBy();
        String passportUserAddress = passport.getPassportUserAddress();
        String passportUserSurname = passport.getPassportUserSurname();
        String passportUserName = passport.getPassportUserName();
        String passportUserThirdName = passport.getPassportUserThirdName();
        LocalDate passportUserDateOfBirth = passport.getPassportUserDateOfBirth();

        return passportSeries != null && passportSeries.matches(PASSPORT_SERIES_REGEXP)
                && passportNumber != null && passportNumber.matches(PASSPORT_NUMBER_REGEXP)
                && passportDateOfIssue != null
                && passportIssuedBy != null && passportIssuedBy.matches(PASSPORT_ISSUED_BY_REGEXP)
                && passportUserAddress != null && passportUserAddress.matches(PASSPORT_USER_ADDRESS_REGEXP)
                && passportUserSurname != null && passportUserSurname.matches(PASSPORT_USER_SURNAME_REGEXP)
                && passportUserName != null && passportUserName.matches(PASSPORT_USER_NAME_REGEXP)
                && passportUserThirdName != null && passportUserThirdName.matches(PASSPORT_USER_THIRDNAME_REGEXP)
                && passportUserDateOfBirth != null;
    }

    public static boolean bankcardValidation(Bankcard bankCard) {
        long bankcardNumber = bankCard.getBankcardNumber();
        LocalDate cardValidTrue = bankCard.getBankcardValidTrue();
        String cardUserFirstname = bankCard.getBankcardUserFirstname();
        String cardUserLastname = bankCard.getBankcardUserLastname();
        String authorizationCode = bankCard.getBankcardCVV();

        return bankcardNumber >= 1000_0000_0000_000L && bankcardNumber <= 9999_9999_9999_9999L
                && cardValidTrue != null
                && cardUserFirstname != null && cardUserFirstname.matches(CARD_USER_FIRSTNAME_REGEXP)
                && cardUserLastname != null && cardUserLastname.matches(CARD_USER_LASTNAME_REGEXP)
                && authorizationCode != null && authorizationCode.length() == 3;
    }

    public static boolean userBankCardDeleteValidation(int userID, int bankcardID) {
        return userID > 0 && bankcardID > 0;
    }

    public static boolean orderValidation(Order order) {
        double discount = order.getDiscount();
        OrderStatus orderStatus = order.getOrderStatus();
        LocalDate pickUpDate = order.getPickUpDate();
        LocalDate dropOffDate = order.getDropOffDate();

        return  discount < 100
                && orderStatus != null
                && pickUpDate != null
                && dropOffDate != null;
    }
}
