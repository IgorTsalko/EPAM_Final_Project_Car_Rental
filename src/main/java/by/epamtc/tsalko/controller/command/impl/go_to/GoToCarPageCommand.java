package by.epamtc.tsalko.controller.command.impl.go_to;

import by.epamtc.tsalko.bean.car.Car;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.CarService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.exception.EntityNotFoundServiceException;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToCarPageCommand implements Command {

    private static final int NUMBER_OF_RECOMMENDED_CARS = 3;

    private static final String ATTRIBUTE_CAR = "car";
    private static final String ATTRIBUTE_RECOMMENDED_CARS = "recommended_cars";

    private static final String PARAMETER_CAR_ID = "car_id";

    private static final String CAR_PAGE = "/WEB-INF/jsp/car/carPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        CarService carService = serviceProvider.getCarService();

        try {
            int carID = Integer.parseInt(req.getParameter(PARAMETER_CAR_ID));

            Car car = carService.getCarByID(carID);
            car.setCarImages(carService.getAllCarImagesByID(carID));
            List<Car> randomCars = carService.getRecommendedCars(NUMBER_OF_RECOMMENDED_CARS, carID);

            req.setAttribute(ATTRIBUTE_CAR, car);
            req.setAttribute(ATTRIBUTE_RECOMMENDED_CARS, randomCars);
            req.getRequestDispatcher(CAR_PAGE).forward(req, resp);
        } catch (EntityNotFoundServiceException | NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (ServiceException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
