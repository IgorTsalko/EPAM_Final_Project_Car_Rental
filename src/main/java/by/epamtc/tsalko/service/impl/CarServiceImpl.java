package by.epamtc.tsalko.service.impl;

import by.epamtc.tsalko.bean.Car;
import by.epamtc.tsalko.dao.CarDAO;
import by.epamtc.tsalko.dao.DAOProvider;
import by.epamtc.tsalko.dao.UserDAO;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.service.CarService;
import by.epamtc.tsalko.service.exception.ServiceException;

import java.util.List;

public class CarServiceImpl implements CarService {

    @Override
    public List<Car> getAllCars() throws ServiceException {
        List<Car> cars;

        DAOProvider daoProvider = DAOProvider.getInstance();
        CarDAO carDAO = daoProvider.getCarDAO();

        try {
            cars = carDAO.getAllCars();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return cars;
    }

    @Override
    public Car getCarByID(int carID) throws ServiceException {
        Car car;

        DAOProvider daoProvider = DAOProvider.getInstance();
        CarDAO carDAO = daoProvider.getCarDAO();

        try {
            car = carDAO.getCarByID(carID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return car;
    }
}
