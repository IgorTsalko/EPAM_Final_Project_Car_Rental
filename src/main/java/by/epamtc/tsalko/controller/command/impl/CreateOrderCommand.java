package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.user.*;
import by.epamtc.tsalko.controller.TechValidator;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.CarService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.InvalidInputDataServiceException;
import by.epamtc.tsalko.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreateOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger(CreateOrderCommand.class);

    private static final String MOCK_PASSWORD = "12345678";

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_PREVIOUS_REQUEST = "previous_request";

    private static final String PARAMETER_PICK_UP_DATE = "pick_up_date";
    private static final String PARAMETER_DROP_OFF_DATE = "drop_off_date";
    private static final String PARAMETER_CAR_ID = "car_id";
    private static final String PARAMETER_PHONE = "phone";
    private static final String PARAMETER_EMAIL = "email";
    private static final String PARAMETER_UPDATE_PASSPORT = "update_passport";
    private static final String PARAMETER_PAY_IN_OFFICE = "pay_in_office";
    private static final String PARAMETER_ADD_BANKCARD = "add_bankcard";

    private static final String PARAMETER_USER_PASSPORT_SERIES = "user_passport_series";
    private static final String PARAMETER_USER_PASSPORT_NUMBER = "user_passport_number";
    private static final String PARAMETER_USER_PASSPORT_DATE_OF_ISSUE = "user_passport_date_of_issue";
    private static final String PARAMETER_USER_PASSPORT_DATE_OF_BIRTH = "user_passport_date_of_birth";
    private static final String PARAMETER_USER_PASSPORT_ADDRESS = "user_passport_address";
    private static final String PARAMETER_USER_PASSPORT_ISSUED_BY = "user_passport_issued_by";
    private static final String PARAMETER_USER_PASSPORT_SURNAME = "user_passport_surname";
    private static final String PARAMETER_USER_PASSPORT_NAME = "user_passport_name";
    private static final String PARAMETER_USER_PASSPORT_THIRDNAME = "user_passport_thirdname";

    private static final String PARAMETER_BANKCARD_NUMBER = "bankcard_number";
    private static final String PARAMETER_BANKCARD_FIRSTNAME = "bankcard_firstname";
    private static final String PARAMETER_BANKCARD_LASTNAME = "bankcard_lastname";
    private static final String PARAMETER_BANKCARD_VALID_TRUE_MONTH = "bankcard_valid_true_month";
    private static final String PARAMETER_BANKCARD_VALID_TRUE_YEAR = "bankcard_valid_true_year";
    private static final String PARAMETER_BANKCARD_CVV = "bankcard_cvv";

    private static final String MESSAGE_CREATE_ORDER = "&message_create_order=";
    private static final String CREATE_ORDER_ERROR = "create_order_error";
    private static final String CREATE_ORDER_SUCCESSFULLY = "create_order_successfully";
    private static final String INCORRECT_DATA = "incorrect_data";

    private static final String validTrueFormatPattern = "d-MM-yy";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        StringBuilder page = new StringBuilder()
                .append((String) session.getAttribute(ATTRIBUTE_PREVIOUS_REQUEST));

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        CarService carService = serviceProvider.getCarService();

        try {
            Order order = new Order();
            int carID = Integer.parseInt(req.getParameter(PARAMETER_CAR_ID));
            LocalDate pickUpDate = LocalDate.parse(req.getParameter(PARAMETER_PICK_UP_DATE));
            LocalDate dropOffDate = LocalDate.parse(req.getParameter(PARAMETER_DROP_OFF_DATE));
            String userPhone = req.getParameter(PARAMETER_PHONE);
            String userEmail = req.getParameter(PARAMETER_EMAIL);

            boolean updatePassport = Boolean.parseBoolean(req.getParameter(PARAMETER_UPDATE_PASSPORT));
            boolean payInOffice = Boolean.parseBoolean(req.getParameter(PARAMETER_PAY_IN_OFFICE));
            boolean addBankcard = Boolean.parseBoolean(req.getParameter(PARAMETER_ADD_BANKCARD));

            if (user == null) {
                RegistrationData registrationData = new RegistrationData();
                registrationData.setLogin(userPhone);
                registrationData.setPhone(userPhone);
                registrationData.setEmail(userEmail);

                AuthorizationData authorizationData = new AuthorizationData();
                authorizationData.setLogin(userPhone);
                authorizationData.setPassword(MOCK_PASSWORD);

                if (TechValidator.registrationValidation(registrationData)
                        && TechValidator.loginValidation(authorizationData)) {
                    userService.registration(registrationData);
                    user = userService.authorization(authorizationData);

                    req.getSession().setAttribute(ATTRIBUTE_USER, user);
                }
            }

            if (user != null) {
                if (updatePassport) {
                    Passport passport = new Passport();

                    passport.setUserID(user.getId());
                    passport.setPassportDateOfIssue(
                            LocalDate.parse(req.getParameter(PARAMETER_USER_PASSPORT_DATE_OF_ISSUE)));// здесь нам не нужно исключение
                    passport.setPassportUserDateOfBirth(
                            LocalDate.parse(req.getParameter(PARAMETER_USER_PASSPORT_DATE_OF_BIRTH)));
                    passport.setPassportSeries(req.getParameter(PARAMETER_USER_PASSPORT_SERIES));
                    passport.setPassportNumber(req.getParameter(PARAMETER_USER_PASSPORT_NUMBER));
                    passport.setPassportIssuedBy(req.getParameter(PARAMETER_USER_PASSPORT_ISSUED_BY));
                    passport.setPassportUserAddress(req.getParameter(PARAMETER_USER_PASSPORT_ADDRESS));
                    passport.setPassportUserSurname(req.getParameter(PARAMETER_USER_PASSPORT_SURNAME));
                    passport.setPassportUserName(req.getParameter(PARAMETER_USER_PASSPORT_NAME));
                    passport.setPassportUserThirdName(req.getParameter(PARAMETER_USER_PASSPORT_THIRDNAME));

                    if (TechValidator.passportValidation(passport)) {
                        try {
                            userService.updateUserPassport(passport);
                        } catch (InvalidInputDataServiceException e) {
                            logger.info("Could not update user passport, incorrect data", e);
                        } catch (ServiceException e) {
                            logger.info("Could not update user passport", e);
                        }
                    } else {
                        logger.info("Could not update user passport, incorrect data");
                    }

                }

                if (!payInOffice) {
                    long bankcardNumber = Long.parseLong(req.getParameter(PARAMETER_BANKCARD_NUMBER));

                    if (TechValidator.bankcardNumberValidation(bankcardNumber)) {
                        logger.info("Mock payment for card number " + bankcardNumber + " is made");
                        order.setPaid(true);

                        if (addBankcard) {
                            Bankcard bankcard = new Bankcard();
                            bankcard.setUserID(user.getId());
                            bankcard.setBankcardNumber(bankcardNumber);
                            String cardValidTrue = 1 + "-" + req.getParameter(PARAMETER_BANKCARD_VALID_TRUE_MONTH)
                                    + "-" + req.getParameter(PARAMETER_BANKCARD_VALID_TRUE_YEAR);
                            bankcard.setBankcardValidTrue(
                                    LocalDate.parse(cardValidTrue, DateTimeFormatter.ofPattern(validTrueFormatPattern)));
                            bankcard.setBankcardUserFirstname(req.getParameter(PARAMETER_BANKCARD_FIRSTNAME).toUpperCase());
                            bankcard.setBankcardUserLastname(req.getParameter(PARAMETER_BANKCARD_LASTNAME).toUpperCase());
                            bankcard.setBankcardCVV(req.getParameter(PARAMETER_BANKCARD_CVV));

                            try {
                                userService.createBankcard(bankcard);
                            } catch (InvalidInputDataServiceException e) {
                                logger.info("Could not link new card, incorrect data", e);
                            } catch (ServiceException e) {
                                logger.info("Could not link new card", e);
                            }
                        }
                    } else {
                        logger.info("Mock payment isn't made, incorrect data");
                    }
                }

                order.setUserID(user.getId());
                order.setDiscount(user.getDiscount());

                order.setPickUpDate(pickUpDate);
                order.setDropOffDate(dropOffDate);
                order.setCar(carService.getCarByID(carID));

                if (TechValidator.orderValidation(order)) {
                    userService.addOrder(order);
                    page.append(MESSAGE_CREATE_ORDER).append(CREATE_ORDER_SUCCESSFULLY);
                } else {
                    page.append(MESSAGE_CREATE_ORDER).append(INCORRECT_DATA);
                }
            } else {
                page.append(MESSAGE_CREATE_ORDER).append(INCORRECT_DATA);
            }

        } catch (InvalidInputDataServiceException e) {
            page.append(MESSAGE_CREATE_ORDER).append(INCORRECT_DATA);
        } catch (DateTimeParseException | NumberFormatException | ServiceException e) {
            System.out.println(e);
            page.append(MESSAGE_CREATE_ORDER).append(CREATE_ORDER_ERROR);
        }

        resp.sendRedirect(page.toString());
    }
}
