package by.epamtc.tsalko.service.impl;

import by.epamtc.tsalko.bean.car.Car;
import by.epamtc.tsalko.dao.CarDAO;
import by.epamtc.tsalko.dao.DAOProvider;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.EntityNotFoundDAOException;
import by.epamtc.tsalko.service.CarService;
import by.epamtc.tsalko.service.exception.EntityNotFoundServiceException;
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
    public List<Car> getRecommendedCars(int count, int mainCarID) throws ServiceException {
        List<Car> randomCars;

        DAOProvider daoProvider = DAOProvider.getInstance();
        CarDAO carDAO = daoProvider.getCarDAO();

        try {
            randomCars = carDAO.getRecommendedCars(count, mainCarID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return randomCars;
    }

    @Override
    public Car getCarByID(int carID) throws ServiceException {
        Car car;

        DAOProvider daoProvider = DAOProvider.getInstance();
        CarDAO carDAO = daoProvider.getCarDAO();

        try {
            car = carDAO.getCarByID(carID);
        } catch (EntityNotFoundDAOException e) {
            throw new EntityNotFoundServiceException();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return car;
    }

    @Override
    public List<String> getAllCarImagesByID(int carID) throws ServiceException {
        List<String> carImages;

        DAOProvider daoProvider = DAOProvider.getInstance();
        CarDAO carDAO = daoProvider.getCarDAO();

        try {
            carImages = carDAO.getAllCarImagesByID(carID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return carImages;
    }
}
