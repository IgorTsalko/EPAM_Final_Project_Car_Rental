package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.bean.RegistrationData;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationCommand implements Command {

    private final static String PARAMETER_EMAIL = "email";
    private final static String PARAMETER_PHONE = "phone";
    private final static String PARAMETER_LOGIN = "login";
    private final static String PARAMETER_PASSWORD = "password";
    private final static String PARAMETER_MESSAGE = "message";

    private final static String REGISTRATION_PAGE = "/WEB-INF/jsp/registrationPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String email = req.getParameter(PARAMETER_EMAIL);
        String phone = req.getParameter(PARAMETER_PHONE);
        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);
        //todo: техническая валидация

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();

        RegistrationData registrationData = new RegistrationData();
        registrationData.setEmail(email);
        registrationData.setPhone(phone);
        registrationData.setLogin(login);
        registrationData.setPassword(password);

        try {
            if (userService.registration(registrationData)) {
                req.setAttribute(PARAMETER_MESSAGE, "registration successful");
                req.getRequestDispatcher(REGISTRATION_PAGE).forward(req, resp);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            // todo: loging
        }
    }
}
