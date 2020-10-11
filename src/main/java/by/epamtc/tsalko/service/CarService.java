package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.Car;
import by.epamtc.tsalko.service.exception.ServiceException;

import java.util.List;

public interface CarService {

    List<Car> getAllCars() throws ServiceException;
    Car getCarByID(int carID) throws ServiceException;
}
