package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.ReturnAct;
import by.epamtc.tsalko.controller.util.TechValidator;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.CarService;
import by.epamtc.tsalko.service.OrderService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.EntityNotFoundServiceException;
import by.epamtc.tsalko.service.exception.InvalidInputDataServiceException;
import by.epamtc.tsalko.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class UpdateOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger(UpdateOrderCommand.class);

    private static final String ATTRIBUTE_PREVIOUS_REQUEST = "previous_request";

    private static final String PARAMETER_ORDER_ID = "order_id";
    private static final String PARAMETER_ORDER_STATUS = "order_status";
    private static final String PARAMETER_CAR_ID = "car_id";
    private static final String PARAMETER_PICK_UP_DATE = "pick_up_date";
    private static final String PARAMETER_DROP_OFF_DATE = "drop_off_date";
    private static final String PARAMETER_COMMENT = "comment";

    private static final String PARAMETER_RETURN_DATE = "return_date";
    private static final String PARAMETER_CAR_ODOMETER = "car_odometer";
    private static final String PARAMETER_CAR_DAMAGE = "car_damage";
    private static final String PARAMETER_ORDER_FINE = "order_fine";
    private static final String PARAMETER_ACT_COMMENT = "act_comment";

    private static final String MESSAGE_UPDATE_ORDER = "&message_update_order=";
    private static final String DATA_UPDATE_ERROR = "data_update_error";
    private static final String INCORRECT_DATA = "incorrect_data";

    private static final int ENDED_ORDER_STATUS_ID = 5;

    private static final String GO_TO_PERSONAL_PAGE_ALL_ORDERS = "mainController?command=go_to_personal_page_all_orders";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String previousRequest = (String) req.getSession().getAttribute(ATTRIBUTE_PREVIOUS_REQUEST);

        StringBuilder page = new StringBuilder();

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        OrderService orderService = serviceProvider.getOrderService();
        CarService carService = serviceProvider.getCarService();

        try {
            Order order = orderService.getOrder(Integer.parseInt(req.getParameter(PARAMETER_ORDER_ID)));
            order.getOrderStatus().setStatusID(Integer.parseInt(req.getParameter(PARAMETER_ORDER_STATUS)));
            order.setCar(carService.getCarByID(Integer.parseInt(req.getParameter(PARAMETER_CAR_ID))));
            order.setPickUpDate(LocalDate.parse(req.getParameter(PARAMETER_PICK_UP_DATE)));
            order.setDropOffDate(LocalDate.parse(req.getParameter(PARAMETER_DROP_OFF_DATE)));
            order.setComment(req.getParameter(PARAMETER_COMMENT));
            order.setDiscount(userService.getUserDetails(order.getUserID()).getUserRating().getDiscount());

            if (order.getOrderStatus().getStatusID() == ENDED_ORDER_STATUS_ID) {
                ReturnAct returnAct = new ReturnAct();
                returnAct.setOrderID(Integer.parseInt(req.getParameter(PARAMETER_ORDER_ID)));
                returnAct.setReturnDate(LocalDate.parse(req.getParameter(PARAMETER_RETURN_DATE)));
                returnAct.setCarOdometer(Integer.parseInt(req.getParameter(PARAMETER_CAR_ODOMETER)));
                returnAct.setCarDamage(req.getParameter(PARAMETER_CAR_DAMAGE));

                try {
                    returnAct.setFine(Double.parseDouble(req.getParameter(PARAMETER_ORDER_FINE)));
                } catch (NumberFormatException e) {/*NOPE*/}

                returnAct.setActComment(req.getParameter(PARAMETER_ACT_COMMENT));

                order.setReturnAct(returnAct);
            }

            if (TechValidator.orderValidation(order)) {
                orderService.updateOrder(order);
                page.append(GO_TO_PERSONAL_PAGE_ALL_ORDERS);
            } else {
                logger.info(order + " failed validation.");
                page.append(previousRequest).append(MESSAGE_UPDATE_ORDER).append(INCORRECT_DATA);
            }
        } catch (InvalidInputDataServiceException e) {
            page.append(previousRequest).append(MESSAGE_UPDATE_ORDER).append(INCORRECT_DATA);
        } catch (NumberFormatException | EntityNotFoundServiceException e) {
            logger.info("Could not update order, incorrect data.", e);
            page.append(previousRequest).append(MESSAGE_UPDATE_ORDER).append(INCORRECT_DATA);
        } catch (ServiceException e) {
            page.append(previousRequest).append(MESSAGE_UPDATE_ORDER).append(DATA_UPDATE_ERROR);
        }

        resp.sendRedirect(page.toString());
    }
}
