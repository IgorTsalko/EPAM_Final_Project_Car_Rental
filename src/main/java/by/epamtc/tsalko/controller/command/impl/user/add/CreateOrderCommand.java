package by.epamtc.tsalko.controller.command.impl.user.add;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.user.AuthorizationData;
import by.epamtc.tsalko.bean.user.RegistrationData;
import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.controller.TechValidator;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.InvalidInputDataServiceException;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CreateOrderCommand implements Command {

    private static final String MOCK_PASSWORD = "12345678";

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_PREVIOUS_REQUEST = "previous_request";

    private static final String PARAMETER_PICK_UP_DATE = "pick_up_date";
    private static final String PARAMETER_DROP_OFF_DATE = "drop_off_date";
    private static final String PARAMETER_CAR_ID = "car_id";
    private static final String PARAMETER_CAR_PRICE_PER_DAY = "car_price_per_day";
    private static final String PARAMETER_PHONE = "phone";

    private static final String MESSAGE_CREATE_ORDER = "message_create_order";
    private static final String CREATE_ORDER_ERROR = "create_order_error";
    private static final String CREATE_ORDER_SUCCESSFULLY = "create_order_successfully";
    private static final String INCORRECT_DATA = "incorrect_data";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        StringBuilder page = new StringBuilder()
                .append((String) session.getAttribute(ATTRIBUTE_PREVIOUS_REQUEST));

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();

        try {
            LocalDate pickUpDate = LocalDate.parse(req.getParameter(PARAMETER_PICK_UP_DATE));
            LocalDate dropOffDate = LocalDate.parse(req.getParameter(PARAMETER_DROP_OFF_DATE));
            int carID = Integer.parseInt(req.getParameter(PARAMETER_CAR_ID));
            double carPricePerDay = Double.parseDouble(req.getParameter(PARAMETER_CAR_PRICE_PER_DAY));
            String userPhone = req.getParameter(PARAMETER_PHONE);

            if (user == null) {
                RegistrationData registrationData = new RegistrationData();
                registrationData.setLogin(userPhone);
                registrationData.setPhone(userPhone);

                AuthorizationData authorizationData = new AuthorizationData();
                authorizationData.setLogin(userPhone);
                authorizationData.setPassword(MOCK_PASSWORD);

                if (TechValidator.registrationValidation(registrationData)
                        && TechValidator.loginValidation(authorizationData)) {
                    userService.registration(registrationData);
                    user = userService.authorization(authorizationData);

                    req.getSession().setAttribute(ATTRIBUTE_USER, user);
                }
            }

            Order order = new Order();

            if (user != null) {
                order.setUserID(user.getId());
                order.setDiscount(user.getDiscount());
            }

            order.setPickUpDate(pickUpDate);
            order.setDropOffDate(dropOffDate);
            order.setCarID(carID);
            order.setPricePerDay(carPricePerDay);

            if (TechValidator.orderValidation(order)) {
                userService.addOrder(order);
                page.append("&").append(MESSAGE_CREATE_ORDER).append("=").append(CREATE_ORDER_SUCCESSFULLY);
            } else {
                page.append("&").append(MESSAGE_CREATE_ORDER).append("=").append(INCORRECT_DATA);
            }
        } catch (InvalidInputDataServiceException e) {
            page.append("&").append(MESSAGE_CREATE_ORDER).append("=").append(INCORRECT_DATA);
        } catch (DateTimeParseException | NumberFormatException | ServiceException e) {
            page.append("&").append(MESSAGE_CREATE_ORDER).append("=").append(CREATE_ORDER_ERROR);
        }

        resp.sendRedirect(page.toString());
    }
}
