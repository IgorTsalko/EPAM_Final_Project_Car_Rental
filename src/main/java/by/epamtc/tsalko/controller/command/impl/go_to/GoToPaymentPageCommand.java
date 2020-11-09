package by.epamtc.tsalko.controller.command.impl.go_to;

import by.epamtc.tsalko.bean.Bankcard;
import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.BankcardService;
import by.epamtc.tsalko.service.OrderService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GoToPaymentPageCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";

    private static final String PARAMETER_ORDER_ID = "order_id";
    private static final String ORDER = "order";
    private static final String BANKCARDS = "bankcards";

    private static final String PAYMENT_PAGE = "/WEB-INF/jsp/paymentPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        BankcardService bankcardService = serviceProvider.getBankcardService();
        OrderService orderService = serviceProvider.getOrderService();

        try {
            int orderID = Integer.parseInt(req.getParameter(PARAMETER_ORDER_ID));
            Order order = orderService.getOrder(orderID);
            List<Bankcard> bankcards = bankcardService.getUserBankcards(user.getId());

            req.setAttribute(ORDER, order);
            req.setAttribute(BANKCARDS, bankcards);

            req.getRequestDispatcher(PAYMENT_PAGE).forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (ServiceException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
