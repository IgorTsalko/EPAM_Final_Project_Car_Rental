package by.epamtc.tsalko.controller.command.impl.user.update;

import by.epamtc.tsalko.bean.user.Passport;
import by.epamtc.tsalko.controller.TechValidator;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.InvalidInputDataServiceException;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UpdateUserPassportCommand implements Command {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static final String PARAMETER_PREVIOUS_COMMAND = "previous_command";
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

    private static final String MESSAGE_PASSPORT_UPDATE = "message_passport_update";
    private static final String DATA_UPDATE_ERROR = "data_update_error";
    private static final String INCORRECT_DATA = "incorrect_data";
    private static final String DATA_UPDATED = "data_updated";

    private static final String MAIN_CONTROLLER = "mainController";
    private static final String COMMAND_GO_TO_ALL_USER_DATA = "go_to_all_user_data";
    private static final String COMMAND = "command";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String previousCommand = req.getParameter(PARAMETER_PREVIOUS_COMMAND);
        StringBuilder page = new StringBuilder()
                .append(MAIN_CONTROLLER)
                .append("?")
                .append(COMMAND)
                .append("=")
                .append(previousCommand);

        Passport passport = new Passport();

        int userID = 0;
        try {
            userID = Integer.parseInt(req.getParameter(PARAMETER_USER_ID));
        } catch (NumberFormatException ignore) {/* NOPE */}
        if (userID < 0) {
            userID = 0;
        }

        if (previousCommand.equals(COMMAND_GO_TO_ALL_USER_DATA)) {
            page.append("&").append(PARAMETER_USER_ID).append("=").append(userID);
        }

        try {
            passport.setPassportDateOfIssue(
                    dateFormat.parse(req.getParameter(PARAMETER_USER_PASSPORT_DATE_OF_ISSUE)));
            passport.setPassportUserDateOfBirth(
                    dateFormat.parse(req.getParameter(PARAMETER_USER_PASSPORT_DATE_OF_BIRTH)));
        } catch (ParseException ignore) {/* NOPE */}

        passport.setUserID(userID);
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
            try {
                userService.updateUserPassport(passport);
                page.append("&").append(MESSAGE_PASSPORT_UPDATE).append("=").append(DATA_UPDATED);
            } catch (InvalidInputDataServiceException e) {
                page.append("&").append(MESSAGE_PASSPORT_UPDATE).append("=").append(INCORRECT_DATA);
            } catch (ServiceException e) {
                page.append("&").append(MESSAGE_PASSPORT_UPDATE).append("=").append(DATA_UPDATE_ERROR);
            }
        } else {
            page.append("&").append(MESSAGE_PASSPORT_UPDATE).append("=").append(INCORRECT_DATA);
        }
        resp.sendRedirect(page.toString());
    }
}