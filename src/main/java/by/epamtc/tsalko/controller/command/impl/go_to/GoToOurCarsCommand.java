package by.epamtc.tsalko.controller.command.impl.go_to;

import by.epamtc.tsalko.bean.car.Car;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.CarService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToOurCarsCommand implements Command {

    private static final String ATTRIBUTE_CARS = "cars";
    private static final String ATTRIBUTE_MESSAGE = "message";

    private static final String ERROR_DATA_RETRIEVE = "data_retrieve_error";

    private static final String OUR_CARS_PAGE = "/WEB-INF/jsp/car/ourCarsPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        CarService carService = serviceProvider.getCarService();

        List<Car> cars;

        try {
            cars = carService.getAllCars();
            req.setAttribute(ATTRIBUTE_CARS, cars);
        } catch (ServiceException e) {
            req.setAttribute(ATTRIBUTE_MESSAGE, ERROR_DATA_RETRIEVE);
        }

        req.getRequestDispatcher(OUR_CARS_PAGE).forward(req, resp);
    }
}
