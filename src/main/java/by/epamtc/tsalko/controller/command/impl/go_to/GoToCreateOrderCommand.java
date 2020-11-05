package by.epamtc.tsalko.controller.command.impl.go_to;

import by.epamtc.tsalko.bean.car.Car;
import by.epamtc.tsalko.bean.user.Passport;
import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.bean.user.UserDetails;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.CarService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.EntityNotFoundServiceException;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToCreateOrderCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_CAR = "car";

    private static final String USER_PASSPORT = "user_passport";
    private static final String PARAMETER_CAR_ID = "car_id";
    private static final String USER_DETAILS = "user_details";

    private static final String BANKCARD_NUMBERS = "bankcard_numbers";

    private static final String MESSAGE_CREATE_ORDER = "message_create_order";
    private static final String ERROR_DATA_RETRIEVE = "data_retrieve_error";
    private static final String CREATE_ORDER_PAGE = "/WEB-INF/jsp/createOrder.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        CarService carService = serviceProvider.getCarService();

        try {
            int userID = ((User) req.getSession().getAttribute(ATTRIBUTE_USER)).getId();
            int carID = Integer.parseInt(req.getParameter(PARAMETER_CAR_ID));

            Car car = carService.getCarByID(carID);
            UserDetails userDetails = userService.getUserDetails(userID);
            List<Long> bankcardNumbers = userService.getBankcardNumbers(userID);
            Passport passport = userService.getUserPassport(userID);

            if (passport != null) {
                req.setAttribute(USER_PASSPORT, passport);
            }
            req.setAttribute(ATTRIBUTE_CAR, car);
            req.setAttribute(USER_DETAILS, userDetails);
            req.setAttribute(BANKCARD_NUMBERS, bankcardNumbers);

            req.getRequestDispatcher(CREATE_ORDER_PAGE).forward(req, resp);
        } catch (EntityNotFoundServiceException | NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (ServiceException e) {
            req.setAttribute(MESSAGE_CREATE_ORDER, ERROR_DATA_RETRIEVE);
            req.getRequestDispatcher(CREATE_ORDER_PAGE).forward(req, resp);
        }
    }
}
