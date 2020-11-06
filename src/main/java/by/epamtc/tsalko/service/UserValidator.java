package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.Bankcard;
import by.epamtc.tsalko.bean.user.Passport;
import by.epamtc.tsalko.bean.user.UserDetails;

import java.time.LocalDate;

public class UserValidator {

    private static final String START_VISA = "4";
    private static final String START_MASTERCARD = "5";

    private static final int MIN_USER_ROLE_ID = 0;
    private static final int MAX_USER_ROLE_ID = 2;
    private static final int MIN_USER_RATING_ID = 0;
    private static final int MAX_USER_RATING_ID = 4;

    private static final int MIN_USER_AGE = 16;
    private static final int MAX_PASSPORT_YEARS = 10;

    public static boolean userDetailsValidation(UserDetails userDetails) {
        int roleID = userDetails.getUserRoleID();
        int ratingID = userDetails.getUserRatingID();

        return roleID >= MIN_USER_ROLE_ID && roleID <= MAX_USER_ROLE_ID
                && ratingID >= MIN_USER_RATING_ID && ratingID <= MAX_USER_RATING_ID;
    }

    public static boolean passportValidation(Passport passport) {
        LocalDate passportDateOfIssue = passport.getPassportDateOfIssue();
        LocalDate passportUserDateOfBirth = passport.getPassportUserDateOfBirth();

        LocalDate now = LocalDate.now();

        return now.isAfter(passportDateOfIssue)
                && now.minusYears(MIN_USER_AGE).isAfter(passportUserDateOfBirth)
                && now.minusYears(MAX_PASSPORT_YEARS).isBefore(passportDateOfIssue);
    }

    public static boolean bankCardValidation(Bankcard bankCard) {
        String bankcardNumber = String.valueOf(bankCard.getBankcardNumber());
        LocalDate cardValidTrue = bankCard.getBankcardValidTrue();

        return (bankcardNumber.startsWith(START_VISA) || bankcardNumber.startsWith(START_MASTERCARD))
                && LocalDate.now().isBefore(cardValidTrue);
    }

    public static boolean orderValidation(Order order) {
        LocalDate pickUpDate = order.getPickUpDate();
        LocalDate dropOffDate = order.getDropOffDate();

        LocalDate now = LocalDate.now();

        return (pickUpDate == null && dropOffDate == null)
                || (pickUpDate != null && dropOffDate != null
                && (now.isEqual(pickUpDate) || now.isBefore(pickUpDate))
                && pickUpDate.isBefore(dropOffDate));
    }
}
