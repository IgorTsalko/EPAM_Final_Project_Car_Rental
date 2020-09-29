package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToLoginPageCommand implements Command {

    private final static String ATTRIBUTE_AUTHORIZATION_MESSAGE = "authorization_message";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/loginPage.jsp").forward(req, resp);
        req.getSession().removeAttribute(ATTRIBUTE_AUTHORIZATION_MESSAGE);
    }
}
