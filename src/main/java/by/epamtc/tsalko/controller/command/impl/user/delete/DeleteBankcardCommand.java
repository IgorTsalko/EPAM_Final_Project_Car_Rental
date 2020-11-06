package by.epamtc.tsalko.controller.command.impl.user.delete;

import by.epamtc.tsalko.controller.TechValidator;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.BankcardService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteBankcardCommand implements Command {

    private static final String ATTRIBUTE_PREVIOUS_REQUEST = "previous_request";

    private static final String PARAMETER_USER_ID = "user_id";
    private static final String PARAMETER_BANKCARD_ID = "bankcard_id";

    private static final String MESSAGE_BANKCARD_DELETE = "&message_bankcard_delete=";
    private static final String DATA_DELETE_ERROR = "data_delete_error";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        StringBuilder page = new StringBuilder((String) req.getSession().getAttribute(ATTRIBUTE_PREVIOUS_REQUEST));
        try {
            int userID = Integer.parseInt(req.getParameter(PARAMETER_USER_ID));
            int bankcardID = Integer.parseInt(req.getParameter(PARAMETER_BANKCARD_ID));

            if (TechValidator.userBankCardDeleteValidation(userID, bankcardID)) {
                ServiceProvider serviceProvider = ServiceProvider.getInstance();
                BankcardService bankcardService = serviceProvider.getBankcardService();

                bankcardService.deleteBankcard(userID, bankcardID);
            } else {
                page.append(MESSAGE_BANKCARD_DELETE).append(DATA_DELETE_ERROR);
            }
        }  catch (NumberFormatException | ServiceException e) {
            page.append(MESSAGE_BANKCARD_DELETE).append(DATA_DELETE_ERROR);
        }
        resp.sendRedirect(page.toString());
    }
}
