package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationCommand implements Command {

    private final static String PARAMETER_LOGIN = "login";
    private final static String PARAMETER_PASSWORD = "password";

    private final static String LOGIN_PAGE = "loginPage.jsp";
    private final static String ERROR_PAGE = "errorPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);
        //todo: техническая валидация

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();

        String page;
        User user;

        try {
            user = userService.verification(login, password);
            page = LOGIN_PAGE;
            req.setAttribute("user", user);
        } catch (ServiceException e) {
            e.printStackTrace();
            // todo: убрать! Временный вывод ошибки
            // todo: loging
            page = ERROR_PAGE;
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("stackTrace", e.getStackTrace());
        }

        req.getRequestDispatcher(page).forward(req, resp);
    }
}
