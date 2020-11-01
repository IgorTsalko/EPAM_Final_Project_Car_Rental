package by.epamtc.tsalko.controller.command.impl.go_to;

import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToLoginPageCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";

    private static final String PREVIOUS_REQUEST = "previous_request";
    private static final String LOGIN_REQUEST_PAGE = "login_request_page";

    private static final String LOGIN_PAGE = "/WEB-INF/jsp/loginPage.jsp";
    private static final String GO_TO_MAIN_PAGE = "mainController?command=go_to_main_page";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String loginRequestPage = (String) session.getAttribute(PREVIOUS_REQUEST);
        session.setAttribute(LOGIN_REQUEST_PAGE, loginRequestPage);

        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        if (user != null) {
            req.getRequestDispatcher(GO_TO_MAIN_PAGE).forward(req, resp);
        } else {
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
        }
    }
}
