package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.car.Car;
import by.epamtc.tsalko.dao.CarDAO;
import by.epamtc.tsalko.dao.connection.ConnectionPool;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.EntityNotFoundDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CarDAOImpl implements CarDAO {

    private static final Logger logger = LogManager.getLogger(CarDAOImpl.class);

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SELECT_ALL_CARS =
            "SELECT c.car_id, car_brand, car_model, car_year_production, car_transmission, car_engine_size, " +
                    "car_fuel_type, car_odometer_value, car_price_per_day, car_comment, car_image_uri " +
                    "FROM cars c JOIN car_images i ON i.car_id=c.car_id GROUP BY c.car_id";

    private static final String SELECT_NUMBERS_OF_CARS = "SELECT COUNT(*) AS numbers_of_cars FROM cars";

    private static final String SELECT_CAR_BY_ID =
            "SELECT c.car_id, car_brand, car_model, car_year_production, car_transmission, car_engine_size, " +
                    "car_fuel_type, car_odometer_value, car_price_per_day, car_comment," +
                    "car_image_uri FROM cars c JOIN car_images i ON i.car_id=c.car_id " +
                    "WHERE c.car_id=? LIMIT 1";

    private static final String SELECT_CAR_IMAGES_BY_ID = "SELECT car_image_uri FROM car_images WHERE car_id=?";

    private static final String COLUMN_CAR_ID = "car_id";
    private static final String COLUMN_NUMBERS_OF_CARS = "numbers_of_cars";
    private static final String COLUMN_CAR_BRAND = "car_brand";
    private static final String COLUMN_CAR_MODEL = "car_model";
    private static final String COLUMN_CAR_YEAR_PRODUCTION = "car_year_production";
    private static final String COLUMN_CAR_TRANSMISSION = "car_transmission";
    private static final String COLUMN_CAR_ENGINE_SIZE = "car_engine_size";
    private static final String COLUMN_CAR_FUEL_TYPE = "car_fuel_type";
    private static final String COLUMN_CAR_ODOMETER_VALUE = "car_odometer_value";
    private static final String COLUMN_CAR_PRICE_PER_DAY = "car_price_per_day";
    private static final String COLUMN_CAR_COMMENT = "car_comment";
    private static final String COLUMN_CAR_IMAGE_URI = "car_image_uri";

    @Override
    public List<Car> getAllCars() throws DAOException {
        List<Car> cars = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_CARS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Car car = createCar(resultSet);
                cars.add(car);
            }
        } catch (SQLException e) {
            logger.error("Severe database error! Could not retrieve all cars!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return cars;
    }

    @Override
    public List<Car> getRecommendedCars(int count, int mainCarID) throws DAOException {
        List<Car> randomCars = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_NUMBERS_OF_CARS);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int numberOfCars = resultSet.getInt(COLUMN_NUMBERS_OF_CARS);

            Integer[] randomIDs = new Integer[numberOfCars - 1];
            int id = 1;
            for (int i = 0; i < randomIDs.length; i++) {
                if (id == mainCarID) {
                    id++;
                }
                randomIDs[i] = id++;
            }
            Collections.shuffle(Arrays.asList(randomIDs));

            for (int i = 0; i < count; i++) {
                preparedStatement = connection.prepareStatement(SELECT_CAR_BY_ID);
                preparedStatement.setInt(1, randomIDs[i]);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                Car car = createCar(resultSet);
                randomCars.add(car);
            }

        } catch (SQLException e) {
            logger.error("Severe database error! Could not retrieve all cars!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return randomCars;
    }

    @Override
    public Car getCarByID(int carID) throws DAOException {
        Car car;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_CAR_BY_ID);
            preparedStatement.setInt(1, carID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new EntityNotFoundDAOException();
            }

            car = createCar(resultSet);
        } catch (SQLException e) {
            logger.error("Severe database error! Could not retrieve car!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return car;
    }

    @Override
    public List<String> getAllCarImagesByID(int carID) throws DAOException {
        List<String> carImages;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_CAR_IMAGES_BY_ID);
            preparedStatement.setInt(1, carID);
            resultSet = preparedStatement.executeQuery();

            carImages = new ArrayList<>();

            while (resultSet.next()) {
                carImages.add(resultSet.getString(COLUMN_CAR_IMAGE_URI));
            }
        } catch (SQLException e) {
            logger.error("Severe database error! Could not retrieve car images!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return carImages;
    }

    private Car createCar(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        List<String> carImages = new ArrayList<>();

        car.setCarID(resultSet.getInt(COLUMN_CAR_ID));
        car.setBrand(resultSet.getString(COLUMN_CAR_BRAND));
        car.setModel(resultSet.getString(COLUMN_CAR_MODEL));
        car.setYearProduction(resultSet.getInt(COLUMN_CAR_YEAR_PRODUCTION));
        car.setTransmission(resultSet.getString(COLUMN_CAR_TRANSMISSION));
        car.setEngineSize(resultSet.getString(COLUMN_CAR_ENGINE_SIZE));
        car.setFuelType(resultSet.getString(COLUMN_CAR_FUEL_TYPE));
        car.setOdometerValue(resultSet.getInt(COLUMN_CAR_ODOMETER_VALUE));
        car.setPricePerDay(resultSet.getDouble(COLUMN_CAR_PRICE_PER_DAY));
        car.setComment(resultSet.getString(COLUMN_CAR_COMMENT));

        carImages.add(resultSet.getString(COLUMN_CAR_IMAGE_URI));
        car.setCarImages(carImages);

        return car;
    }
}
