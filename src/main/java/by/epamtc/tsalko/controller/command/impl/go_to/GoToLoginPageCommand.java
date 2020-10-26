package by.epamtc.tsalko.controller.command.impl.go_to;

import by.epamtc.tsalko.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToLoginPageCommand implements Command {

    private static final String PREVIOUS_REQUEST = "previous_request";
    private static final String LOGIN_REQUEST_PAGE = "login_request_Page";

    private static final String LOGIN_PAGE = "/WEB-INF/jsp/loginPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String loginRequestPage = (String) session.getAttribute(PREVIOUS_REQUEST);
        session.setAttribute(LOGIN_REQUEST_PAGE, loginRequestPage);
        req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
    }
}
