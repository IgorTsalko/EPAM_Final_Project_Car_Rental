package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AppLocalizationCommand implements Command {

    private static final String PARAMETER_LOCAL = "local";

    private static final String ATTRIBUTE_PREVIOUS_REQUEST = "previous_request";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String local = req.getParameter(PARAMETER_LOCAL);
        String previousRequest = (String) session.getAttribute(ATTRIBUTE_PREVIOUS_REQUEST);
        session.setAttribute(PARAMETER_LOCAL, local);
        resp.sendRedirect(previousRequest);
    }
}
