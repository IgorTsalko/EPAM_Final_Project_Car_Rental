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
    private static final String ATTRIBUTE_CAR_IMAGES = "car_images";
    private static final String ATTRIBUTE_RECOMMENDED_CARS = "recommended_cars";
    private static final String ATTRIBUTE_MESSAGE = "message";

    private static final String PARAMETER_CAR_ID = "car_id";

    private static final String ERROR_DATA_RETRIEVE = "data_retrieve_error";

    private static final String CAR_PAGE = "/WEB-INF/jsp/carPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        CarService carService = serviceProvider.getCarService();

        Car car;
        List<String> carImages;
        List<Car> randomCars;

        int carID = 0;
        try {
            carID = Integer.parseInt(req.getParameter(PARAMETER_CAR_ID));
        } catch (NumberFormatException ignore) { /*NOPE*/ }
        if (carID < 0) {
            carID = 0;
        }

        try {
            car = carService.getCarByID(carID);
            carImages = carService.getAllCarImagesByID(carID);
            randomCars = carService.getRecommendedCars(NUMBER_OF_RECOMMENDED_CARS, carID);

            req.setAttribute(ATTRIBUTE_CAR, car);
            req.setAttribute(ATTRIBUTE_CAR_IMAGES, carImages);
            req.setAttribute(ATTRIBUTE_RECOMMENDED_CARS, randomCars);
            req.getRequestDispatcher(CAR_PAGE).forward(req, resp);
        } catch (EntityNotFoundServiceException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (ServiceException e) {
            req.setAttribute(ATTRIBUTE_MESSAGE, ERROR_DATA_RETRIEVE);
            req.getRequestDispatcher(CAR_PAGE).forward(req, resp);
        }
    }
}
