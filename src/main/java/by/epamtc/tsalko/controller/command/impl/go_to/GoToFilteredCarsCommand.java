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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class GoToFilteredCarsCommand implements Command {

    private static final String ATTRIBUTE_CARS = "cars";

    private static final String PARAMETER_PICK_UP_DATE = "pick_up_date";

    private static final String OUR_CARS_PAGE = "/WEB-INF/jsp/car/ourCarsPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        CarService carService = serviceProvider.getCarService();

        try {
            LocalDate pickUpDate = LocalDate.parse(req.getParameter(PARAMETER_PICK_UP_DATE));

            List<Car> cars = carService.getAllCars();
            List<Car> filteredCars = new ArrayList<>();

            for (Car c : cars) {
                if (c.getAvailableFrom().isBefore(pickUpDate)
                        || c.getAvailableFrom().isEqual(pickUpDate)) {
                    filteredCars.add(c);
                }
            }

            req.setAttribute(ATTRIBUTE_CARS, filteredCars);

            req.getRequestDispatcher(OUR_CARS_PAGE).forward(req, resp);
        } catch (DateTimeParseException | ServiceException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
