package by.epamtc.tsalko.controller.command.impl.user.go_to;

import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToPersonalPageAddBankcardCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";

    private static final String GO_TO_MAIN_PAGE = "mainController?command=go_to_main_page";
    private static final String PERSONAL_PAGE = "/WEB-INF/jsp/personal_page/personalPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);

        if (user == null) {
            resp.sendRedirect(GO_TO_MAIN_PAGE);
        } else {
            req.getRequestDispatcher(PERSONAL_PAGE).forward(req, resp);
        }
    }
}