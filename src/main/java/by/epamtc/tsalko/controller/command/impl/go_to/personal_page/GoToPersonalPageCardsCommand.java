package by.epamtc.tsalko.controller.command.impl.go_to.personal_page;

import by.epamtc.tsalko.bean.User;
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
import java.util.List;

public class GoToPersonalPageCardsCommand implements Command {

    private static final String ATTRIBUTE_USER_CARD_ACCOUNTS = "user_card_accounts";
    private static final String ATTRIBUTE_MESSAGE = "message";

    private static final String ERROR_DATA_RETRIEVE = "data_retrieve_error";

    private static final String COMMAND_GO_TO_MAIN_PAGE = "mainController?command=go_to_main_page";

    private static final String PERSONAL_PAGE = "/WEB-INF/jsp/personal_page/personalPage.jsp";

    private static final Logger logger = LogManager.getLogger(GoToPersonalPageCardsCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            logger.warn("An unauthorized user is trying to get personal data");
            resp.sendRedirect(COMMAND_GO_TO_MAIN_PAGE);
        } else {
            ServiceProvider serviceProvider = ServiceProvider.getInstance();
            UserService userService = serviceProvider.getUserService();

            List<Long> userCardAccounts;
            int userID = user.getId();

            try {
                userCardAccounts = userService.getUserCardAccounts(userID);
                req.setAttribute(ATTRIBUTE_USER_CARD_ACCOUNTS, userCardAccounts);
            } catch (ServiceException e) {
                req.setAttribute(ATTRIBUTE_MESSAGE, ERROR_DATA_RETRIEVE);
            }
            req.getRequestDispatcher(PERSONAL_PAGE).forward(req, resp);
        }
    }
}