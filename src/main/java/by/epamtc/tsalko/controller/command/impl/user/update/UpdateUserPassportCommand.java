package by.epamtc.tsalko.controller.command.impl.user.update;

import by.epamtc.tsalko.bean.user.Passport;
import by.epamtc.tsalko.controller.util.TechValidator;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.InvalidInputDataServiceException;
import by.epamtc.tsalko.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UpdateUserPassportCommand implements Command {

    private static final Logger logger = LogManager.getLogger(UpdateUserPassportCommand.class);

    private static final String ATTRIBUTE_PREVIOUS_REQUEST = "previous_request";

    private static final String PARAMETER_USER_ID = "user_id";
    private static final String PARAMETER_USER_PASSPORT_SERIES = "user_passport_series";
    private static final String PARAMETER_USER_PASSPORT_NUMBER = "user_passport_number";
    private static final String PARAMETER_USER_PASSPORT_DATE_OF_ISSUE = "user_passport_date_of_issue";
    private static final String PARAMETER_USER_PASSPORT_DATE_OF_BIRTH = "user_passport_date_of_birth";
    private static final String PARAMETER_USER_PASSPORT_ADDRESS = "user_passport_address";
    private static final String PARAMETER_USER_PASSPORT_ISSUED_BY = "user_passport_issued_by";
    private static final String PARAMETER_USER_PASSPORT_SURNAME = "user_passport_surname";
    private static final String PARAMETER_USER_PASSPORT_NAME = "user_passport_name";
    private static final String PARAMETER_USER_PASSPORT_THIRDNAME = "user_passport_thirdname";

    private static final String MESSAGE_PASSPORT_UPDATE = "&message_passport_update=";
    private static final String DATA_UPDATE_ERROR = "data_update_error";
    private static final String INCORRECT_DATA = "incorrect_data";
    private static final String DATA_UPDATED = "data_updated";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        StringBuilder page = new StringBuilder((String) req.getSession().getAttribute(ATTRIBUTE_PREVIOUS_REQUEST));

        try {
            Passport passport = new Passport();

            passport.setUserID(Integer.parseInt(req.getParameter(PARAMETER_USER_ID)));
            passport.setPassportDateOfIssue(
                    LocalDate.parse(req.getParameter(PARAMETER_USER_PASSPORT_DATE_OF_ISSUE)));
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
                ServiceProvider serviceProvider = ServiceProvider.getInstance();
                UserService userService = serviceProvider.getUserService();

                if (userService.updateUserPassport(passport)) {
                    page.append(MESSAGE_PASSPORT_UPDATE).append(DATA_UPDATED);
                } else {
                    page.append(MESSAGE_PASSPORT_UPDATE).append(DATA_UPDATE_ERROR);
                }
            } else {
                logger.info(passport + " failed validation.");
                page.append(MESSAGE_PASSPORT_UPDATE).append(INCORRECT_DATA);
            }
        } catch (DateTimeParseException | NumberFormatException e) {
            logger.warn("Incorrect input data.", e);
            page.append(MESSAGE_PASSPORT_UPDATE).append(INCORRECT_DATA);
        } catch (InvalidInputDataServiceException e) {
            page.append(MESSAGE_PASSPORT_UPDATE).append(INCORRECT_DATA);
        } catch (ServiceException e) {
            page.append(MESSAGE_PASSPORT_UPDATE).append(DATA_UPDATE_ERROR);
        }
        resp.sendRedirect(page.toString());
    }
}
