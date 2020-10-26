package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.car.Car;
import by.epamtc.tsalko.service.exception.ServiceException;

import java.util.List;

public interface CarService {

    List<Car> getAllCars() throws ServiceException;
    List<Car> getRecommendedCars(int count, int mainCarID) throws ServiceException;
    Car getCarByID(int carID) throws ServiceException;
    List<String> getAllCarImagesByID(int carID) throws ServiceException;
}
