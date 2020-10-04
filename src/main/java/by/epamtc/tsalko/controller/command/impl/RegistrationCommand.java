package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.bean.RegistrationData;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.controller.validator.UserValidator;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;
import by.epamtc.tsalko.service.exception.UserAlreadyExistsServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);

    private static final String PARAMETER_EMAIL = "email";
    private static final String PARAMETER_PHONE = "phone";
    private static final String PARAMETER_LOGIN = "login";
    private static final String PARAMETER_PASSWORD = "password";
    private static final String PARAMETER_SUCCESSFUL = "message=successful";
    private static final String PARAMETER_USER_EXISTS = "message=user_already_exists";
    private static final String PARAMETER_ERROR = "message=error";
    private static final String PARAMETER_INCORRECT_DATA = "message=incorrect_data";

    private static final String REGISTRATION_PAGE = "mainController?command=go_to_registration_page";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);
        String phone = req.getParameter(PARAMETER_PHONE);
        String email = req.getParameter(PARAMETER_EMAIL);

        RegistrationData registrationData = new RegistrationData();
        registrationData.setLogin(login);
        registrationData.setPassword(password);
        registrationData.setPhone(phone);
        registrationData.setEmail(email);

        String page;

        if (UserValidator.registrationValidation(registrationData)) {
            ServiceProvider serviceProvider = ServiceProvider.getInstance();
            UserService userService = serviceProvider.getUserService();

            try {
                if (userService.registration(registrationData)) {
                    logger.info("User is registered");
                    page = REGISTRATION_PAGE + "&" + PARAMETER_SUCCESSFUL;
                } else {
                    page = REGISTRATION_PAGE + "&" + PARAMETER_ERROR;
                }
            } catch (UserAlreadyExistsServiceException e) {
                logger.info("User tried to register a second time");
                page = REGISTRATION_PAGE + "&" + PARAMETER_USER_EXISTS;
            } catch (ServiceException e) {
                page = REGISTRATION_PAGE + "&" + PARAMETER_ERROR;
            }
        } else {
            page = REGISTRATION_PAGE + "&" + PARAMETER_INCORRECT_DATA;
        }

        resp.sendRedirect(page);
    }
}
