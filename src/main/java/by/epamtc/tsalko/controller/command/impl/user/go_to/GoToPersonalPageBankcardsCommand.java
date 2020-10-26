package by.epamtc.tsalko.controller.command.impl.user.go_to;

import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToPersonalPageBankcardsCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";

    private static final String BANKCARD_NUMBERS = "bankcard_numbers";

    private static final String MESSAGE_BANKCARDS = "message_bankcards";
    private static final String ERROR_DATA_RETRIEVE = "data_retrieve_error";

    private static final String PERSONAL_PAGE = "/WEB-INF/jsp/personal_page/personalPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);
        int userID = user.getId();

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();

        List<Long> bankcardNumbers;
        try {
            bankcardNumbers = userService.getBankcardNumbers(userID);
            req.setAttribute(BANKCARD_NUMBERS, bankcardNumbers);
        } catch (ServiceException e) {
            req.setAttribute(MESSAGE_BANKCARDS, ERROR_DATA_RETRIEVE);
        }
        req.getRequestDispatcher(PERSONAL_PAGE).forward(req, resp);
    }
}