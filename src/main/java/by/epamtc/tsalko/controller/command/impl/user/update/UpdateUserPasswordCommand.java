package by.epamtc.tsalko.controller.command.impl.user.update;

import by.epamtc.tsalko.controller.TechValidator;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUserPasswordCommand implements Command {

    private static final Logger logger = LogManager.getLogger(UpdateUserPasswordCommand.class);

    private static final String PARAMETER_USER_ID = "user_id";
    private static final String PARAMETER_OLD_PASSWORD = "old_password";
    private static final String PARAMETER_NEW_PASSWORD = "new_password";

    private static final String MESSAGE_UPDATE_PASSWORD = "&message_update_password=";
    private static final String DATA_UPDATED = "data_updated";
    private static final String DATA_UPDATE_ERROR = "data_update_error";
    private static final String INVALID_OLD_PASSWORD = "invalid_old_password";
    private static final String INCORRECT_DATA = "incorrect_data";

    private static final String GO_TO_PERSONAL_PAGE_DETAILS = "mainController?command=go_to_personal_page_details";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        StringBuilder page = new StringBuilder();

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();

        try {
            int userID = Integer.parseInt(req.getParameter(PARAMETER_USER_ID));
            String oldPassword = req.getParameter(PARAMETER_OLD_PASSWORD);
            String newPassword = req.getParameter(PARAMETER_NEW_PASSWORD);

            if (TechValidator.passwordValidation(oldPassword) && TechValidator.passwordValidation(newPassword)) {
                if (userService.updateUserPassword(userID, oldPassword, newPassword)) {
                    page.append(GO_TO_PERSONAL_PAGE_DETAILS).append(MESSAGE_UPDATE_PASSWORD).append(DATA_UPDATED);
                } else {
                    page.append(MESSAGE_UPDATE_PASSWORD).append(INVALID_OLD_PASSWORD);
                }
            } else {
                logger.info("oldPassword: " + oldPassword + ", newPassword: "
                        + newPassword + " failed validation.");
                page.append(MESSAGE_UPDATE_PASSWORD).append(INCORRECT_DATA);
            }
        } catch (NumberFormatException e) {
            logger.warn("Incorrect input data.", e);
            page.append(MESSAGE_UPDATE_PASSWORD).append(INCORRECT_DATA);
        } catch (ServiceException e) {
            page.append(MESSAGE_UPDATE_PASSWORD).append(DATA_UPDATE_ERROR);
        }

        resp.sendRedirect(page.toString());
    }
}
