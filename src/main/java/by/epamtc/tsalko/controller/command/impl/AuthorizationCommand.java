package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.bean.AuthorizationData;
import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;
import by.epamtc.tsalko.service.exception.UserNotFoundServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationCommand implements Command {

    private final static String PARAMETER_LOGIN = "login";
    private final static String PARAMETER_PASSWORD = "password";
    private final static String PARAMETER_USER = "user";
    private final static String PARAMETER_AUTHORIZATION_MESSAGE = "authorization_message";

    private final static String LOGIN_PAGE = "mainController?command=go_to_login_page";
    private final static String MAIN_PAGE = "mainController?command=go_to_main_page";

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

        HttpSession session;
        User user;

        try {
            user = userService.authorization(authorizationData);
            session = req.getSession();
            session.setAttribute(PARAMETER_USER, user);
            resp.sendRedirect(MAIN_PAGE);
        } catch (UserNotFoundServiceException e) {
            req.getServletContext().setAttribute(PARAMETER_AUTHORIZATION_MESSAGE, "wrong data");
            resp.sendRedirect(LOGIN_PAGE);
        } catch (ServiceException e) {
            e.printStackTrace();
            // todo: loging. Обрабатываем, устраняем
        }

    }
}
