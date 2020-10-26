package by.epamtc.tsalko.controller.command.impl.user.delete;

import by.epamtc.tsalko.controller.TechValidator;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteBankcardCommand implements Command {

    private static final String PARAMETER_USER_ID = "user_id";
    private static final String PARAMETER_BANKCARD_NUMBER = "bankcard_number";
    private static final String PARAMETER_PREVIOUS_COMMAND = "previous_command";

    private static final String MESSAGE_BANKCARD_DELETE = "message_bankcard_delete";
    private static final String DATA_DELETE_ERROR = "data_delete_error";

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

        int userID = 0;
        long bankcardNumber = 0;
        try {
            userID = Integer.parseInt(req.getParameter(PARAMETER_USER_ID));
            bankcardNumber = Long.parseLong(req.getParameter(PARAMETER_BANKCARD_NUMBER));
        } catch (NumberFormatException ignore) {/* NOPE */}
        if (userID < 0) {
            userID = 0;
        }

        if (previousCommand.equals(COMMAND_GO_TO_ALL_USER_DATA)) {
            page.append("&").append(PARAMETER_USER_ID).append("=").append(userID);
        }

        if (TechValidator.userBankCardDeleteValidation(userID, bankcardNumber)) {
            ServiceProvider serviceProvider = ServiceProvider.getInstance();
            UserService userService = serviceProvider.getUserService();
            try {
                if (!userService.deleteBankcard(userID, bankcardNumber)) {
                    page.append("&").append(MESSAGE_BANKCARD_DELETE).append("=").append(DATA_DELETE_ERROR);
                }
            } catch (ServiceException e) {
                page.append("&").append(MESSAGE_BANKCARD_DELETE).append("=").append(DATA_DELETE_ERROR);
            }
        } else {
            page.append("&").append(MESSAGE_BANKCARD_DELETE).append("=").append(DATA_DELETE_ERROR);
        }
        resp.sendRedirect(page.toString());
    }
}
