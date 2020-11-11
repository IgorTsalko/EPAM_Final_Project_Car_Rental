package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.Bankcard;
import by.epamtc.tsalko.bean.user.Passport;
import by.epamtc.tsalko.bean.user.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    private static final List<String> availableOrderStatuses = new ArrayList<>();

    static {
        availableOrderStatuses.add("expired");
        availableOrderStatuses.add("new");
        availableOrderStatuses.add("processing");
        availableOrderStatuses.add("rent");
        availableOrderStatuses.add("ended");
        availableOrderStatuses.add("canceled");
    }

    private static final String START_VISA = "4";
    private static final String START_MASTERCARD = "5";

    private static final int MIN_USER_AGE = 16;
    private static final int MAX_PASSPORT_YEARS = 10;

    public static boolean passportValidation(Passport passport) {
        LocalDate passportDateOfIssue = passport.getPassportDateOfIssue();
        LocalDate passportUserDateOfBirth = passport.getPassportUserDateOfBirth();

        LocalDate now = LocalDate.now();

        return (passportDateOfIssue.isBefore(now) || passportDateOfIssue.isEqual(now))
                && passportDateOfIssue.isAfter(now.minusYears(MAX_PASSPORT_YEARS))
                && passportUserDateOfBirth.isBefore(now.minusYears(MIN_USER_AGE));
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

        return (now.isEqual(pickUpDate) || now.isBefore(pickUpDate))
                && pickUpDate.isBefore(dropOffDate);
    }

    public static boolean updateOrderValidation(Order order) {
        String orderStatus = order.getOrderStatus();
        String orderComment = order.getComment();
        LocalDate pickUpDate = order.getPickUpDate();
        LocalDate dropOffDate = order.getDropOffDate();

        return availableOrderStatuses.contains(orderStatus)
                && (orderComment == null || orderComment.length() <= 255)
                && pickUpDate.isBefore(dropOffDate);
    }
}
