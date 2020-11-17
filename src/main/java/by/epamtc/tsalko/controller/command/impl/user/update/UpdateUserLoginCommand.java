package by.epamtc.tsalko.controller.command.impl.user.update;

import by.epamtc.tsalko.controller.util.TechValidator;
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

public class UpdateUserLoginCommand implements Command {

    private static final Logger logger = LogManager.getLogger(UpdateUserLoginCommand.class);

    private static final String ATTRIBUTE_USER = "user";

    private static final String PARAMETER_USER_ID = "user_id";
    private static final String PARAMETER_NEW_LOGIN = "new_login";

    private static final String MESSAGE_UPDATE_LOGIN = "&message_update_login=";
    private static final String SUCCESSFULLY = "successfully";
    private static final String DATA_UPDATE_ERROR = "data_update_error";
    private static final String INCORRECT_DATA = "incorrect_data";

    private static final String GO_TO_PERSONAL_PAGE_DETAILS = "mainController?command=go_to_personal_page_details";
    private static final String GO_TO_LOGIN_PAGE = "mainController?command=go_to_login_page";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        StringBuilder page = new StringBuilder();

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();

        try {
            int userID = Integer.parseInt(req.getParameter(PARAMETER_USER_ID));
            String newLogin = req.getParameter(PARAMETER_NEW_LOGIN);

            if (TechValidator.loginValidation(newLogin)) {
                userService.updateUserLogin(userID, newLogin);
                req.getSession().removeAttribute(ATTRIBUTE_USER);
                page.append(GO_TO_LOGIN_PAGE).append(MESSAGE_UPDATE_LOGIN).append(SUCCESSFULLY);
            } else {
                logger.info(newLogin + " failed validation.");
                page.append(GO_TO_PERSONAL_PAGE_DETAILS)
                        .append(MESSAGE_UPDATE_LOGIN).append(INCORRECT_DATA);
            }
        } catch (NumberFormatException e) {
            logger.warn("Incorrect input data.", e);
            page.append(GO_TO_PERSONAL_PAGE_DETAILS)
                    .append(MESSAGE_UPDATE_LOGIN).append(INCORRECT_DATA);
        } catch (ServiceException e) {
            page.append(GO_TO_PERSONAL_PAGE_DETAILS)
                    .append(MESSAGE_UPDATE_LOGIN).append(DATA_UPDATE_ERROR);
        }

        resp.sendRedirect(page.toString());
    }
}
