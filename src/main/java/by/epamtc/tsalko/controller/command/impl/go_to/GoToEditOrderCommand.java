package by.epamtc.tsalko.controller.command.impl.go_to;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.car.Car;
import by.epamtc.tsalko.bean.content.OrderStatus;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.*;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToEditOrderCommand implements Command {

    private static final String PARAMETER_ORDER_ID = "order_id";

    private static final String ORDER = "order";
    private static final String CARS = "cars";
    private static final String ORDER_STATUSES = "order_statuses";

    private static final String EDIT_ORDER_PAGE = "/WEB-INF/jsp/editOrder.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrderService orderService = serviceProvider.getOrderService();
        CarService carService = serviceProvider.getCarService();
        ContentService contentService = serviceProvider.getContentService();

        try {
            Order order = orderService.getOrder(Integer.parseInt(req.getParameter(PARAMETER_ORDER_ID)));
            order.setCar(carService.getCarByID(order.getCar().getCarID()));
            order.setReturnAct(orderService.getReturnAct(order.getOrderId()));
            List<Car> cars = carService.getAllCars();
            List<OrderStatus> orderStatuses = contentService.getAllOrderStatuses();
            req.setAttribute(ORDER, order);
            req.setAttribute(CARS, cars);
            req.setAttribute(ORDER_STATUSES, orderStatuses);

            req.getRequestDispatcher(EDIT_ORDER_PAGE).forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (ServiceException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
