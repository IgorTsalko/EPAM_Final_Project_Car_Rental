package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.bean.AuthorizationData;
import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;
import by.epamtc.tsalko.service.exception.UserNotFoundServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AuthorizationCommand.class);

    private final static String PARAMETER_LOGIN = "login";
    private final static String PARAMETER_PASSWORD = "password";
    private final static String PARAMETER_WRONG_DATA = "message=wrong_data";
    private final static String PARAMETER_BD_ERROR = "message=bd_error";

    private final static String ATTRIBUTE_USER = "user";

    private final static String LOGIN_PAGE = "mainController?command=go_to_login_page";
    private final static String USER_PAGE = "mainController?command=go_to_user_page";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);
        //todo: техническая валидация
        AuthorizationData authorizationData = new AuthorizationData();
        authorizationData.setLogin(login);
        authorizationData.setPassword(password);

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();

        try {
            User user = userService.authorization(authorizationData);
            req.getSession().setAttribute(ATTRIBUTE_USER, user);
            logger.info("User is authorized");
            resp.sendRedirect(USER_PAGE);
        } catch (UserNotFoundServiceException e) {
            logger.info("User has entered incorrect data");
            resp.sendRedirect(LOGIN_PAGE + "&" + PARAMETER_WRONG_DATA);
        } catch (ServiceException e) {
            resp.sendRedirect(LOGIN_PAGE + "&" + PARAMETER_BD_ERROR);
        }
    }
}
