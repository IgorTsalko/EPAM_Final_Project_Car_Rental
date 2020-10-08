package by.epamtc.tsalko.controller.command.impl.go_to;

import by.epamtc.tsalko.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToRegistrationPageCommand implements Command {

    private static final String REGISTRATION_PAGE = "/WEB-INF/jsp/registrationPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(REGISTRATION_PAGE).forward(req, resp);
    }
}
