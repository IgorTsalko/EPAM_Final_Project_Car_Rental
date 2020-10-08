package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToUserPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AuthorizationCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userData = req.getParameter("user_data");
        if (userData == null) {
            userData = "orders";
        }
        User user = (User) req.getSession().getAttribute("user");

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();

        if (user != null) {
            int userID = user.getId();
            if (userData.equals("orders")) {
                setOrdersToRequest(userID, req, userService);
            } else if (userData.equals("passport")) {
                setPassportDataToRequest(userID, req, userService);
            } else if (userData.equals("cards")) {
                setCardsToRequest(userID, req, userService);
            }
        }

        req.getRequestDispatcher("/WEB-INF/jsp/userPage.jsp").forward(req, resp);
    }

    private void setOrdersToRequest(int userID, HttpServletRequest req, UserService userService) {
        List<Order> userOrders = null;

        try {
            userOrders = userService.getUserOrders(userID);
        } catch (ServiceException e) {
            logger.warn("Cannot retrieve user orders", e);
        }

        if (userOrders != null) {
            req.setAttribute("userOrders", userOrders);
        }
    }

    private void setPassportDataToRequest(int userID, HttpServletRequest req, UserService userService) {

    }

    private void setCardsToRequest(int userID, HttpServletRequest req, UserService userService) {

    }
}