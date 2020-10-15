package by.epamtc.tsalko.controller.command.impl.go_to.personal_page;

import by.epamtc.tsalko.bean.Passport;
import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.bean.UserDetails;
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

public class ShowUserDetailsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowUserDetailsCommand.class);

    private static final String ATTRIBUTE_USER_DETAILS = "user_details";
    private static final String ATTRIBUTE_USER_PASSPORT = "user_passport";
    private static final String ATTRIBUTE_USER_CARD_ACCOUNTS = "user_card_accounts";

    private static final String PARAMETER_USER_ID = "user_id";

    private static final String COMMAND_GO_TO_MAIN_PAGE = "mainController?command=go_to_main_page";

    private static final String USER_DATA_PAGE = "/WEB-INF/jsp/personal_page/userData.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        if (user == null || !user.getRole().equals("admin")) {
            logger.warn("Attempt to obtain private data");
            resp.sendRedirect(COMMAND_GO_TO_MAIN_PAGE);
        } else {
            ServiceProvider serviceProvider = ServiceProvider.getInstance();
            UserService userService = serviceProvider.getUserService();

            UserDetails userDetails;
            Passport userPassport;
            List<Long> userCardAccounts;

            int userID = Integer.parseInt(req.getParameter(PARAMETER_USER_ID));

            try {
                userDetails = userService.getUserDetails(userID);
                userPassport = userService.getUserPassport(userID);
                userCardAccounts = userService.getUserCardAccounts(userID);

                req.setAttribute(ATTRIBUTE_USER_DETAILS, userDetails);
                req.setAttribute(ATTRIBUTE_USER_PASSPORT, userPassport);
                req.setAttribute(ATTRIBUTE_USER_CARD_ACCOUNTS, userCardAccounts);
            } catch (ServiceException e) {
                e.printStackTrace();
            }

            req.getRequestDispatcher(USER_DATA_PAGE).forward(req, resp);
        }
    }
}