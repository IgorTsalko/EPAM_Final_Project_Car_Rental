package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.bean.RegistrationData;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;
import by.epamtc.tsalko.service.exception.UserAlreadyExistsServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationCommand implements Command {

    private final static String PARAMETER_EMAIL = "email";
    private final static String PARAMETER_PHONE = "phone";
    private final static String PARAMETER_LOGIN = "login";
    private final static String PARAMETER_PASSWORD = "password";
    private final static String PARAMETER_REGISTRATION_MESSAGE = "registration_message";

    private final static String REGISTRATION_PAGE = "mainController?command=go_to_registration_page";

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
                req.getServletContext().setAttribute(PARAMETER_REGISTRATION_MESSAGE, "registration_successful");
            } else {
                req.getServletContext().setAttribute(PARAMETER_REGISTRATION_MESSAGE, "registration_error");
            }
        } catch (UserAlreadyExistsServiceException e) {
            req.getServletContext().setAttribute(PARAMETER_REGISTRATION_MESSAGE, "user_already_exists");
            // todo: loging
        } catch (ServiceException e) {
            // todo: loging. Прочие ошибки, придумать что делать для каждой
        }
        resp.sendRedirect(REGISTRATION_PAGE);
    }
}
