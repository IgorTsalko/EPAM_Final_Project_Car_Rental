package by.epamtc.tsalko.controller.command.impl.user.update;

import by.epamtc.tsalko.bean.user.UserDetails;
import by.epamtc.tsalko.controller.TechValidator;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ContentService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUserDetailsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(UpdateUserDetailsCommand.class);

    private static final String ATTRIBUTE_PREVIOUS_REQUEST = "previous_request";

    private static final String PARAMETER_USER_ID = "user_id";
    private static final String PARAMETER_USER_ROLE_ID = "user_role_id";
    private static final String PARAMETER_USER_RATING_ID = "user_rating_id";
    private static final String PARAMETER_USER_PHONE = "user_phone";
    private static final String PARAMETER_USER_EMAIL = "user_email";

    private static final String MESSAGE_DETAILS_UPDATE = "&message_details_update=";
    private static final String DATA_UPDATE_ERROR = "data_update_error";
    private static final String INCORRECT_DATA = "incorrect_data";
    private static final String DATA_UPDATED = "data_updated";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        StringBuilder page = new StringBuilder((String) req.getSession().getAttribute(ATTRIBUTE_PREVIOUS_REQUEST));

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        ContentService contentService = serviceProvider.getContentService();

        try {
            UserDetails userDetails
                    = userService.getUserDetails(
                            Integer.parseInt(req.getParameter(PARAMETER_USER_ID)));

            userDetails.setUserPhone(req.getParameter(PARAMETER_USER_PHONE));
            userDetails.setUserEmail(req.getParameter(PARAMETER_USER_EMAIL));

            try {
                userDetails.setUserRole(
                        contentService.getRoleByID(
                                Integer.parseInt(req.getParameter(PARAMETER_USER_ROLE_ID))));
                userDetails.setUserRating(
                        contentService.getRatingByID(
                                Integer.parseInt(req.getParameter(PARAMETER_USER_RATING_ID))));
            } catch (NumberFormatException ignore) {/*NOPE*/}

            if (TechValidator.userDetailsValidation(userDetails)) {
                userService.updateUserDetails(userDetails);
                page.append(MESSAGE_DETAILS_UPDATE).append(DATA_UPDATED);
            } else {
                logger.info(userDetails + " failed validation.");
                page.append(MESSAGE_DETAILS_UPDATE).append(INCORRECT_DATA);
            }
        } catch (NumberFormatException e) {
            page.append(MESSAGE_DETAILS_UPDATE).append(INCORRECT_DATA);
        } catch (ServiceException e) {
            page.append(MESSAGE_DETAILS_UPDATE).append(DATA_UPDATE_ERROR);
        }

        resp.sendRedirect(page.toString());
    }
}
