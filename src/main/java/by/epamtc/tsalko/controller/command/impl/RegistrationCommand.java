package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.bean.user.RegistrationData;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.controller.TechValidator;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;
import by.epamtc.tsalko.service.exception.EntityAlreadyExistsServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationCommand implements Command {

    private static final String PARAMETER_LOGIN = "login";
    private static final String PARAMETER_PASSWORD = "password";
    private static final String PARAMETER_EMAIL = "email";
    private static final String PARAMETER_PHONE = "phone";

    private static final String MESSAGE_REGISTRATION = "&message_registration=";
    private static final String SUCCESSFUL = "successful";
    private static final String USER_EXISTS = "user_exists";
    private static final String ERROR = "error";
    private static final String INCORRECT_DATA = "incorrect_data";

    private static final String GO_TO_REGISTRATION_PAGE = "mainController?command=go_to_registration_page";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = GO_TO_REGISTRATION_PAGE;

        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);
        String phone = req.getParameter(PARAMETER_PHONE);
        String email = req.getParameter(PARAMETER_EMAIL);

        RegistrationData registrationData = new RegistrationData();
        registrationData.setLogin(login);
        registrationData.setPassword(password);
        registrationData.setPhone(phone);
        registrationData.setEmail(email);

        if (TechValidator.registrationValidation(registrationData)) {
            ServiceProvider serviceProvider = ServiceProvider.getInstance();
            UserService userService = serviceProvider.getUserService();

            try {
                userService.registration(registrationData);
                page += MESSAGE_REGISTRATION + SUCCESSFUL;
            } catch (EntityAlreadyExistsServiceException e) {
                page += MESSAGE_REGISTRATION + USER_EXISTS;
            } catch (ServiceException e) {
                page += MESSAGE_REGISTRATION + ERROR;
            }
        } else {
            page += MESSAGE_REGISTRATION + INCORRECT_DATA;
        }

        resp.sendRedirect(page);
    }
}