package by.epamtc.tsalko.controller;

import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

public class MainController extends HttpServlet {

    private static final String PARAMETER_COMMAND = "command";
    private static final String PARAMETER_LOCAL = "local";

    private final CommandProvider commandProvider = new CommandProvider();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute(PARAMETER_LOCAL) == null) {
            session.setAttribute(PARAMETER_LOCAL, Locale.getDefault());
        }

        String commandName = req.getParameter(PARAMETER_COMMAND);
        Command command = commandProvider.getCommand(commandName.toUpperCase());
        if (command != null) {
            command.execute(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
