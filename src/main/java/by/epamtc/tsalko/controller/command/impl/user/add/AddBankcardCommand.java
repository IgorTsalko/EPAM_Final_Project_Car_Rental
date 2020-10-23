package by.epamtc.tsalko.controller.command.impl.user.add;

import by.epamtc.tsalko.bean.user.Bankcard;
import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.controller.UserValidator;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.EntityAlreadyExistsServiceException;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddBankcardCommand implements Command {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yy");

    private static final String PARAMETER_USER_ID = "user_id";
    private static final String PARAMETER_BANKCARD_NUMBER = "bankcard_number";
    private static final String PARAMETER_BANKCARD_FIRSTNAME = "bankcard_firstname";
    private static final String PARAMETER_BANKCARD_LASTNAME = "bankcard_lastname";
    private static final String PARAMETER_BANKCARD_VALID_TRUE_MONTH = "bankcard_valid_true_month";
    private static final String PARAMETER_BANKCARD_VALID_TRUE_YEAR = "bankcard_valid_true_year";
    private static final String PARAMETER_BANKCARD_CVV = "bankcard_cvv";

    private static final String MESSAGE_BANKCARD_ADDING = "message_bankcard_adding";
    private static final String DATA_ADD_ERROR = "data_add_error";
    private static final String INCORRECT_DATA = "incorrect_data";
    private static final String BANKCARD_EXISTS = "bankcard_exists";

    private static final String GO_TO_PERSONAL_PAGE_BANKCARDS = "mainController?command=go_to_personal_page_bankcards";
    private static final String GO_TO_PERSONAL_PAGE_ADD_BANKCARD = "mainController?command=go_to_personal_page_add_bankcard";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        StringBuilder page = new StringBuilder();

        int userID = 0;
        try {
            userID = Integer.parseInt(req.getParameter(PARAMETER_USER_ID));
        } catch (NumberFormatException ignore) {/* NOPE */}
        if (userID < 0) {
            userID = 0;
        }

        Bankcard bankcard = new Bankcard();
        bankcard.setUserID(userID);
        bankcard.setBankcardUserFirstname(req.getParameter(PARAMETER_BANKCARD_FIRSTNAME).toUpperCase());
        bankcard.setBankcardUserLastname(req.getParameter(PARAMETER_BANKCARD_LASTNAME).toUpperCase());
        bankcard.setBankcardCVV(req.getParameter(PARAMETER_BANKCARD_CVV));
        try {
            bankcard.setBankcardNumber(Long.parseLong(req.getParameter(PARAMETER_BANKCARD_NUMBER)));
        } catch (NumberFormatException ignore) {}

        try {
            bankcard.setBankcardValidTrue(
                    dateFormat.parse(req.getParameter(PARAMETER_BANKCARD_VALID_TRUE_MONTH)
                    + "-" + req.getParameter(PARAMETER_BANKCARD_VALID_TRUE_YEAR)));
        } catch (ParseException ignore) {/* NOPE */}

        if (UserValidator.BankCardValidation(bankcard)) {
            ServiceProvider serviceProvider = ServiceProvider.getInstance();
            UserService userService = serviceProvider.getUserService();
            try {
                boolean bankcardAdded = userService.addBankcard(bankcard);
                if (!bankcardAdded) {
                    page.append(GO_TO_PERSONAL_PAGE_ADD_BANKCARD)
                            .append("&").append(MESSAGE_BANKCARD_ADDING).append("=").append(DATA_ADD_ERROR);
                } else {
                    page.append(GO_TO_PERSONAL_PAGE_BANKCARDS);
                }
            } catch (EntityAlreadyExistsServiceException e) {
                page.append(GO_TO_PERSONAL_PAGE_ADD_BANKCARD)
                        .append("&").append(MESSAGE_BANKCARD_ADDING).append("=").append(BANKCARD_EXISTS);
            } catch (ServiceException e) {
                page.append(GO_TO_PERSONAL_PAGE_ADD_BANKCARD)
                        .append("&").append(MESSAGE_BANKCARD_ADDING).append("=").append(DATA_ADD_ERROR);
            }
        } else {
            page.append(GO_TO_PERSONAL_PAGE_ADD_BANKCARD)
                    .append("&").append(MESSAGE_BANKCARD_ADDING).append("=").append(INCORRECT_DATA);
        }
        resp.sendRedirect(page.toString());
    }
}
