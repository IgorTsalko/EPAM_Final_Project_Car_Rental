package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationCommand implements Command {

    private final static String PARAMETER_EMAIL = "email";
    private final static String PARAMETER_PHONE = "phone";
    private final static String PARAMETER_LOGIN = "login";
    private final static String PARAMETER_PASSWORD = "password";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String email = req.getParameter(PARAMETER_EMAIL);
        String phone = req.getParameter(PARAMETER_PHONE);
        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
    }
}
