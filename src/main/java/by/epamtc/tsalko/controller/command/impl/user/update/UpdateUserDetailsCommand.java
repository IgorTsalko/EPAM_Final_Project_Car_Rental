package by.epamtc.tsalko.controller.command.impl.user.update;

import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.bean.user.UserDetails;
import by.epamtc.tsalko.controller.UserVerifier;
import by.epamtc.tsalko.controller.UserValidator;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUserDetailsCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";

    private static final String PARAMETER_SENDER_LOGIN = "sender_login";
    private static final String PARAMETER_USER_ID = "user_id";
    private static final String PARAMETER_USER_ROLE_ID = "user_role_id";
    private static final String PARAMETER_USER_RATING_ID = "user_rating_id";
    private static final String PARAMETER_USER_PHONE = "user_phone";
    private static final String PARAMETER_USER_EMAIL = "user_email";
    private static final String PARAMETER_PREVIOUS_COMMAND = "previous_command";

    private static final String MESSAGE_DETAILS_UPDATE = "message_details_update";
    private static final String DATA_UPDATE_ERROR = "data_update_error";
    private static final String INCORRECT_DATA = "incorrect_data";
    private static final String DATA_UPDATED = "data_updated";

    private static final String MAIN_CONTROLLER = "mainController";
    private static final String COMMAND_GO_TO_ALL_USER_DATA = "go_to_all_user_data";
    private static final String COMMAND = "command";

    private static final String GO_TO_MAIN_PAGE = "mainController?command=go_to_main_page";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);
        String senderLogin = req.getParameter(PARAMETER_SENDER_LOGIN);
        String previousCommand = req.getParameter(PARAMETER_PREVIOUS_COMMAND);
        int userID = 0;

        try {
            userID = Integer.parseInt(req.getParameter(PARAMETER_USER_ID));
        } catch (NumberFormatException ignore) {/* NOPE */}

        if (!(UserVerifier.isCustomer(user, senderLogin) && user.getId() == userID)
                && !UserVerifier.isAdmin(user, senderLogin)
                || previousCommand == null) {
            resp.sendRedirect(GO_TO_MAIN_PAGE);
        } else {
            StringBuilder page = new StringBuilder()
                    .append(MAIN_CONTROLLER).append("?").append(COMMAND).append("=").append(previousCommand);

            if (previousCommand.equals(COMMAND_GO_TO_ALL_USER_DATA)) {
                page.append("&").append(PARAMETER_USER_ID).append("=").append(userID);
            }

            UserDetails userDetails = new UserDetails();

            try {
                userDetails.setUserRoleID(Integer.parseInt(req.getParameter(PARAMETER_USER_ROLE_ID)));
                userDetails.setUserRatingID(Integer.parseInt(req.getParameter(PARAMETER_USER_RATING_ID)));
            } catch (NumberFormatException ignore) {/* NOPE */}

            userDetails.setUserID(userID);
            userDetails.setUserPhone(req.getParameter(PARAMETER_USER_PHONE));
            userDetails.setUserEmail(req.getParameter(PARAMETER_USER_EMAIL));

            if (UserValidator.userDetailsValidation(userDetails)) {
                ServiceProvider serviceProvider = ServiceProvider.getInstance();
                UserService userService = serviceProvider.getUserService();
                try {
                    userService.updateUserDetails(userDetails);
                    page.append("&").append(MESSAGE_DETAILS_UPDATE).append("=").append(DATA_UPDATED);
                } catch (ServiceException e) {
                    page.append("&").append(MESSAGE_DETAILS_UPDATE).append("=").append(DATA_UPDATE_ERROR);
                }
            } else {
                page.append("&").append(MESSAGE_DETAILS_UPDATE).append("=").append(INCORRECT_DATA);
            }
            resp.sendRedirect(page.toString());
        }
    }
}
