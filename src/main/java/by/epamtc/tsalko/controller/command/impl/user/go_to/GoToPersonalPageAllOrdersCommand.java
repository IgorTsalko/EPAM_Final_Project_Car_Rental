package by.epamtc.tsalko.controller.command.impl.user.go_to;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.controller.UserVerifier;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToPersonalPageAllOrdersCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";

    private static final String ALL_ORDERS = "all_orders";

    private static final String MESSAGE_ALL_ORDERS = "message_all_orders";
    private static final String ERROR_DATA_RETRIEVE = "data_retrieve_error";

    private static final String GO_TO_MAIN_PAGE = "mainController?command=go_to_main_page";
    private static final String PERSONAL_PAGE = "/WEB-INF/jsp/personal_page/personalPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);

        if (!UserVerifier.isAdmin(user)) {
            resp.sendRedirect(GO_TO_MAIN_PAGE);
        } else {
            ServiceProvider serviceProvider = ServiceProvider.getInstance();
            UserService userService = serviceProvider.getUserService();

            List<Order> allOrders;

            try {
                allOrders = userService.getAllOrders();
                req.setAttribute(ALL_ORDERS, allOrders);
            } catch (ServiceException e) {
                req.setAttribute(MESSAGE_ALL_ORDERS, ERROR_DATA_RETRIEVE);
            }
            req.getRequestDispatcher(PERSONAL_PAGE).forward(req, resp);
        }
    }
}