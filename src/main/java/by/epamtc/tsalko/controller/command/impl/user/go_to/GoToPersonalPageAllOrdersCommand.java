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

    private static final int LINE_AMOUNT = 4;

    private static final String ATTRIBUTE_USER = "user";

    private static final String ORDERS = "orders";
    private static final String OFFSET = "offset";
    private static final String PAGE = "page";

    private static final String MESSAGE_ORDERS = "message_orders";
    private static final String ERROR_DATA_RETRIEVE = "data_retrieve_error";
    private static final String LAST_PAGE = "last_page";
    private static final String FIRST_PAGE = "first_page";

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

            List<Order> orders;

            int page = 1;
            try {
                page = Integer.parseInt(req.getParameter(PAGE));
            } catch (NumberFormatException ignore) {/*NOPE*/}

            if (page < 1) page = 1;

            try {
                int offset = (page - 1) * LINE_AMOUNT;
                orders = userService.getOrders(offset, LINE_AMOUNT);

                if (orders.size() < LINE_AMOUNT) {
                    req.setAttribute(MESSAGE_ORDERS, LAST_PAGE);
                }
                if (page == 1) {
                    req.setAttribute(MESSAGE_ORDERS, FIRST_PAGE);
                }
                req.setAttribute(OFFSET, offset);
                req.setAttribute(ORDERS, orders);
                req.setAttribute(PAGE, page);
            } catch (ServiceException e) {
                req.setAttribute(MESSAGE_ORDERS, ERROR_DATA_RETRIEVE);
            }
            req.getRequestDispatcher(PERSONAL_PAGE).forward(req, resp);
        }
    }
}