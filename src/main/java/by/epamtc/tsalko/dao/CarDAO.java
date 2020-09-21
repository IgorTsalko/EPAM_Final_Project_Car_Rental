package by.epamtc.tsalko.dao;

import by.epamtc.tsalko.bean.Car;
import by.epamtc.tsalko.bean.CarData;

import java.util.List;

public interface CarDAO {

    public List<Car> getCars(CarData carData);
}
