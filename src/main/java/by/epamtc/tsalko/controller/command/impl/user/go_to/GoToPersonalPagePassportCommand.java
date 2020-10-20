package by.epamtc.tsalko.controller.command.impl.user.go_to;

import by.epamtc.tsalko.bean.user.Passport;
import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToPersonalPagePassportCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";

    private static final String USER_PASSPORT = "user_passport";

    private static final String MESSAGE_PASSPORT = "message_passport";
    private static final String ERROR_DATA_RETRIEVE = "data_retrieve_error";

    private static final String GO_TO_MAIN_PAGE = "mainController?command=go_to_main_page";
    private static final String PERSONAL_PAGE = "/WEB-INF/jsp/personal_page/personalPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);

        if (user == null) {
            resp.sendRedirect(GO_TO_MAIN_PAGE);
        } else {
            ServiceProvider serviceProvider = ServiceProvider.getInstance();
            UserService userService = serviceProvider.getUserService();

            Passport passport;
            int userID = user.getId();

            try {
                passport = userService.getUserPassport(userID);
                if (passport != null) {
                    req.setAttribute(USER_PASSPORT, passport);
                }
            } catch (ServiceException e) {
                req.setAttribute(MESSAGE_PASSPORT, ERROR_DATA_RETRIEVE);
            }

            req.getRequestDispatcher(PERSONAL_PAGE).forward(req, resp);
        }
    }
}