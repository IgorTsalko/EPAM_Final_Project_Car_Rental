package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.user.Bankcard;
import by.epamtc.tsalko.bean.user.Passport;
import by.epamtc.tsalko.bean.user.UserDetails;

import java.util.Date;

public class UserValidator {

    private static final String START_VISA = "4";
    private static final String START_MASTERCARD = "5";

    private static final int MIN_USER_ROLE_ID = 1;
    private static final int MAX_USER_ROLE_ID = 2;
    private static final int MIN_USER_RATING_ID = 1;
    private static final int MAX_USER_RATING_ID = 4;

    public static boolean userDetailsValidation(UserDetails userDetails) {
        int roleID = userDetails.getUserRoleID();
        int ratingID = userDetails.getUserRatingID();

        return roleID >= MIN_USER_ROLE_ID && roleID <= MAX_USER_ROLE_ID
                && ratingID >= MIN_USER_RATING_ID && ratingID <= MAX_USER_RATING_ID;
    }

    public static boolean passportValidation(Passport passport) {
        Date passportDateOfIssue = passport.getPassportDateOfIssue();
        Date passportUserDateOfBirth = passport.getPassportUserDateOfBirth();

        Date now = new Date();

        return passportDateOfIssue.compareTo(now) < 0 && passportUserDateOfBirth.compareTo(now) < 0;
    }

    public static boolean bankCardValidation(Bankcard bankCard) {
        String bankcardNumber = String.valueOf(bankCard.getBankcardNumber());
        Date cardValidTrue = bankCard.getBankcardValidTrue();

        Date now = new Date();

        return (bankcardNumber.startsWith(START_VISA) || bankcardNumber.startsWith(START_MASTERCARD))
                && cardValidTrue.compareTo(now) > 0;
    }
}
