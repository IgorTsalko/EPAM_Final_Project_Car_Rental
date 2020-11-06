package by.epamtc.tsalko.controller.command.impl.user.create;

import by.epamtc.tsalko.bean.Bankcard;
import by.epamtc.tsalko.controller.TechValidator;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.BankcardService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.exception.EntityAlreadyExistsServiceException;
import by.epamtc.tsalko.service.exception.InvalidInputDataServiceException;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreateBankcardCommand implements Command {

    private static final String PARAMETER_USER_ID = "user_id";
    private static final String PARAMETER_BANKCARD_NUMBER = "bankcard_number";
    private static final String PARAMETER_BANKCARD_FIRSTNAME = "bankcard_firstname";
    private static final String PARAMETER_BANKCARD_LASTNAME = "bankcard_lastname";
    private static final String PARAMETER_BANKCARD_VALID_TRUE_MONTH = "bankcard_valid_true_month";
    private static final String PARAMETER_BANKCARD_VALID_TRUE_YEAR = "bankcard_valid_true_year";
    private static final String PARAMETER_BANKCARD_CVV = "bankcard_cvv";

    private static final String MESSAGE_BANKCARD_ADDING = "&message_bankcard_adding=";
    private static final String DATA_ADD_ERROR = "data_add_error";
    private static final String INCORRECT_DATA = "incorrect_data";
    private static final String BANKCARD_EXISTS = "bankcard_exists";

    private static final String validTrueFormatPattern = "d-MM-yy";

    private static final String GO_TO_PERSONAL_PAGE_BANKCARDS = "mainController?command=go_to_personal_page_bankcards";
    private static final String GO_TO_PERSONAL_PAGE_CREATE_BANKCARD = "mainController?command=go_to_personal_page_create_bankcard";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        StringBuilder page = new StringBuilder();
        try {
            Bankcard bankcard = new Bankcard();
            bankcard.setUserID(Integer.parseInt(req.getParameter(PARAMETER_USER_ID)));
            bankcard.setBankcardNumber(Long.parseLong(req.getParameter(PARAMETER_BANKCARD_NUMBER)));
            String cardValidTrue = 1 + "-" + req.getParameter(PARAMETER_BANKCARD_VALID_TRUE_MONTH)
                    + "-" + req.getParameter(PARAMETER_BANKCARD_VALID_TRUE_YEAR);
            bankcard.setBankcardValidTrue(
                    LocalDate.parse(cardValidTrue, DateTimeFormatter.ofPattern(validTrueFormatPattern)));
            bankcard.setBankcardUserFirstname(req.getParameter(PARAMETER_BANKCARD_FIRSTNAME).toUpperCase());
            bankcard.setBankcardUserLastname(req.getParameter(PARAMETER_BANKCARD_LASTNAME).toUpperCase());
            bankcard.setBankcardCVV(req.getParameter(PARAMETER_BANKCARD_CVV));

            ServiceProvider serviceProvider = ServiceProvider.getInstance();
            BankcardService bankcardService = serviceProvider.getBankcardService();

            if (TechValidator.bankcardValidation(bankcard)) {
                bankcardService.createBankcard(bankcard);
                page.append(GO_TO_PERSONAL_PAGE_BANKCARDS);
            } else {
                page.append(GO_TO_PERSONAL_PAGE_CREATE_BANKCARD)
                        .append(MESSAGE_BANKCARD_ADDING).append(INCORRECT_DATA);
            }
        } catch (NumberFormatException | DateTimeParseException | InvalidInputDataServiceException e) {
            page.append(GO_TO_PERSONAL_PAGE_CREATE_BANKCARD)
                    .append(MESSAGE_BANKCARD_ADDING).append(INCORRECT_DATA);
        } catch (EntityAlreadyExistsServiceException e) {
            page.append(GO_TO_PERSONAL_PAGE_CREATE_BANKCARD)
                    .append(MESSAGE_BANKCARD_ADDING).append(BANKCARD_EXISTS);
        } catch (ServiceException e) {
            page.append(GO_TO_PERSONAL_PAGE_CREATE_BANKCARD)
                    .append(MESSAGE_BANKCARD_ADDING).append(DATA_ADD_ERROR);
        }

        resp.sendRedirect(page.toString());
    }
}
