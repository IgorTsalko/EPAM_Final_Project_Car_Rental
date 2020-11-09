package by.epamtc.tsalko.controller.command.impl.go_to;

import by.epamtc.tsalko.bean.Bankcard;
import by.epamtc.tsalko.bean.car.Car;
import by.epamtc.tsalko.bean.user.Passport;
import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.bean.user.UserDetails;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.BankcardService;
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

    private static final String BANKCARDS = "bankcards";

    private static final String CREATE_ORDER_PAGE = "/WEB-INF/jsp/createOrder.jsp";


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        BankcardService bankcardService = serviceProvider.getBankcardService();
        CarService carService = serviceProvider.getCarService();

        try {
            int carID = Integer.parseInt(req.getParameter(PARAMETER_CAR_ID));
            Car car = carService.getCarByID(carID);
            req.setAttribute(ATTRIBUTE_CAR, car);

            if (user != null) {
                int userID = user.getId();

                UserDetails userDetails = userService.getUserDetails(userID);
                List<Bankcard> bankcards = bankcardService.getUserBankcards(userID);
                Passport passport = userService.getUserPassport(userID);

                req.setAttribute(USER_DETAILS, userDetails);
                req.setAttribute(BANKCARDS, bankcards);
                req.setAttribute(USER_PASSPORT, passport);
            }

            req.getRequestDispatcher(CREATE_ORDER_PAGE).forward(req, resp);
        } catch (EntityNotFoundServiceException | NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (ServiceException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
