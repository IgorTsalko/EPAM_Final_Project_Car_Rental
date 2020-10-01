package by.epamtc.tsalko.controller;

import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainController extends HttpServlet {

    private static final String PARAMETER_COMMAND = "command";

    private final CommandProvider commandProvider = new CommandProvider();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter(PARAMETER_COMMAND);
        Command command = commandProvider.getCommand(commandName.toUpperCase());
        command.execute(req, resp);
    }


}
