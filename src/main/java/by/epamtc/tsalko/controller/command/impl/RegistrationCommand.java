package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.bean.RegistrationData;
import by.epamtc.tsalko.controller.command.Command;
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

    private final static String PARAMETER_EMAIL = "email";
    private final static String PARAMETER_PHONE = "phone";
    private final static String PARAMETER_LOGIN = "login";
    private final static String PARAMETER_PASSWORD = "password";
    private final static String PARAMETER_REGISTRATION_SUCCESSFUL = "message=registration_successful";
    private final static String PARAMETER_REGISTRATION_USER_EXISTS = "message=user_already_exists";
    private final static String PARAMETER_REGISTRATION_ERROR = "message=registration_error";

    private final static String REGISTRATION_PAGE = "mainController?command=go_to_registration_page";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        String page;

        try {
            if (userService.registration(registrationData)) {
                logger.info("User is registered");
                page = REGISTRATION_PAGE + "&" + PARAMETER_REGISTRATION_SUCCESSFUL;
            } else {
                page = REGISTRATION_PAGE + "&" + PARAMETER_REGISTRATION_ERROR;
            }
        } catch (UserAlreadyExistsServiceException e) {
            logger.info("User tried to register a second time");
            page = REGISTRATION_PAGE + "&" + PARAMETER_REGISTRATION_USER_EXISTS;
        } catch (ServiceException e) {
            page = REGISTRATION_PAGE + "&" + PARAMETER_REGISTRATION_ERROR;
        }
        resp.sendRedirect(page);
    }
}
