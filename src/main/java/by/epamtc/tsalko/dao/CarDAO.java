package by.epamtc.tsalko.dao;

import by.epamtc.tsalko.bean.car.Car;
import by.epamtc.tsalko.dao.exception.DAOException;

import java.util.List;

public interface CarDAO {

    List<Car> getAllCars() throws DAOException;

    List<Car> getRecommendedCars(int count, int mainCarID) throws DAOException;

    Car getCarByID(int carID) throws DAOException;

    List<String> getAllCarImagesByID(int carID) throws DAOException;
}
