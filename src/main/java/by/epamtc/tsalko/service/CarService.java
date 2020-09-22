package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.Car;
import by.epamtc.tsalko.bean.CarData;

import java.util.List;

public interface CarService {

    List<Car> getCars(CarData carData);
}
