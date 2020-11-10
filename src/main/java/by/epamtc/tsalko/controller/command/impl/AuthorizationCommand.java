package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.bean.user.AuthorizationData;
import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.controller.TechValidator;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;
import by.epamtc.tsalko.service.exception.EntityNotFoundServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AuthorizationCommand.class);

    private static final String LOGIN_REQUEST_PAGE = "login_request_page";

    private static final String ATTRIBUTE_USER = "user";

    private static final String PARAMETER_LOGIN = "login";
    private static final String PARAMETER_PASSWORD = "password";

    private static final String MESSAGE_AUTHORIZATION = "&message_authorization=";
    private static final String WRONG_DATA = "wrong_data";
    private static final String ERROR = "error";
    private static final String INCORRECT_DATA = "incorrect_data";

    private static final String GO_TO_LOGIN_PAGE = "mainController?command=go_to_login_page";
    private static final String GO_TO_MAIN_PAGE = "mainController?command=go_to_main_page";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page;

        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);

        AuthorizationData authorizationData = new AuthorizationData();
        authorizationData.setLogin(login);
        authorizationData.setPassword(password);

        if (TechValidator.authValidation(authorizationData)) {
            ServiceProvider serviceProvider = ServiceProvider.getInstance();
            UserService userService = serviceProvider.getUserService();

            try {
                User user = userService.authorization(authorizationData);
                req.getSession().setAttribute(ATTRIBUTE_USER, user);

                page = (String) req.getSession().getAttribute(LOGIN_REQUEST_PAGE);
                if (page == null) {
                    page = GO_TO_MAIN_PAGE;
                }
            } catch (EntityNotFoundServiceException e) {
                page = GO_TO_LOGIN_PAGE + MESSAGE_AUTHORIZATION + WRONG_DATA;
            } catch (ServiceException e) {
                page = GO_TO_LOGIN_PAGE + MESSAGE_AUTHORIZATION + ERROR;
            }
        } else {
            logger.info(authorizationData + " failed validation.");
            page = GO_TO_LOGIN_PAGE + MESSAGE_AUTHORIZATION + INCORRECT_DATA;
        }

        resp.sendRedirect(page);
    }
}
