package by.epamtc.tsalko.controller.order_creation;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.controller.util.TechValidator;
import by.epamtc.tsalko.service.CarService;
import by.epamtc.tsalko.service.OrderService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.exception.InvalidInputDataServiceException;
import by.epamtc.tsalko.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CreateOrderController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(CreateOrderController.class);

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_PREVIOUS_REQUEST = "previous_request";

    private static final String PARAM_ORDER_ID = "&order_id=";
    private static final String PARAMETER_CAR_ID = "car_id";
    private static final String PARAMETER_PICK_UP_DATE = "pick_up_date";
    private static final String PARAMETER_DROP_OFF_DATE = "drop_off_date";

    private static final String MESSAGE_CREATE_ORDER = "&create_order=";
    private static final String CREATE_ORDER_ERROR = "create_order_error";
    private static final String INCORRECT_DATA = "incorrect_data";

    private static final String GO_TO_PAYMENT = "mainController?command=go_to_payment_page";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String previousRequest = (String) session.getAttribute(ATTRIBUTE_PREVIOUS_REQUEST);
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        Order order = new Order();

        StringBuilder page = new StringBuilder();

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        CarService carService = serviceProvider.getCarService();
        OrderService orderService = serviceProvider.getOrderService();

        try {
            order.setUserID(user.getId());
            order.setPickUpDate(LocalDate.parse(req.getParameter(PARAMETER_PICK_UP_DATE)));
            order.setDropOffDate(LocalDate.parse(req.getParameter(PARAMETER_DROP_OFF_DATE)));
            order.setDiscount(user.getDiscount());
            order.setCar(carService.getCarByID(Integer.parseInt(req.getParameter(PARAMETER_CAR_ID))));

            if (TechValidator.orderValidation(order)) {
                orderService.createOrder(order);
                page.append(GO_TO_PAYMENT).append(PARAM_ORDER_ID).append(order.getOrderId());
            } else {
                logger.info(order + " failed validation.");
                page.append(previousRequest).append(MESSAGE_CREATE_ORDER).append(INCORRECT_DATA);
            }
        } catch (InvalidInputDataServiceException e) {
            page.append(previousRequest).append(MESSAGE_CREATE_ORDER).append(INCORRECT_DATA);
        } catch (DateTimeParseException | NumberFormatException e) {
            logger.info("Could not create order, incorrect data.", e);
            page.append(previousRequest).append(MESSAGE_CREATE_ORDER).append(INCORRECT_DATA);
        } catch (ServiceException e) {
            page.append(previousRequest).append(MESSAGE_CREATE_ORDER).append(CREATE_ORDER_ERROR);
        }

        resp.sendRedirect(page.toString());
    }
}
