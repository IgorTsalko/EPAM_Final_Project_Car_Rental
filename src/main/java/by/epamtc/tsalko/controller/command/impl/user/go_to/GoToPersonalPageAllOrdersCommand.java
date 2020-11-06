package by.epamtc.tsalko.controller.command.impl.user.go_to;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.OrderService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToPersonalPageAllOrdersCommand implements Command {

    private static final int ROWS_AMOUNT = 10;

    private static final String ORDERS = "orders";
    private static final String OFFSET = "offset";
    private static final String PAGE = "page";
    private static final String FIRST_PAGE = "first_page";
    private static final String LAST_PAGE = "last_page";

    private static final String MESSAGE_ORDERS = "message_orders";
    private static final String ERROR_DATA_RETRIEVE = "data_retrieve_error";

    private static final String PERSONAL_PAGE = "/WEB-INF/jsp/personal_page/personalPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ServiceProvider serviceProvider = ServiceProvider.getInstance();
            OrderService orderService = serviceProvider.getOrderService();

            int page = 1;
            try {
                page = Integer.parseInt(req.getParameter(PAGE));
            } catch (NumberFormatException ignore) {/*NOPE*/}
            if (page < 1) {
                page = 1;
            }

            int offset = (page - 1) * ROWS_AMOUNT;
            List<Order> orders = orderService.getOrders(offset, ROWS_AMOUNT);

            if (orders.size() < ROWS_AMOUNT) {
                req.setAttribute(LAST_PAGE, true);
            }
            if (page == 1) {
                req.setAttribute(FIRST_PAGE, true);
            }

            req.setAttribute(OFFSET, offset);
            req.setAttribute(PAGE, page);
            req.setAttribute(ORDERS, orders);

            req.getRequestDispatcher(PERSONAL_PAGE).forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (ServiceException e) {
            req.setAttribute(MESSAGE_ORDERS, ERROR_DATA_RETRIEVE);
            req.getRequestDispatcher(PERSONAL_PAGE).forward(req, resp);
        }
    }
}