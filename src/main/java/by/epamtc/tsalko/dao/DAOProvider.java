package by.epamtc.tsalko.dao;

import by.epamtc.tsalko.dao.impl.CarDAOImpl;
import by.epamtc.tsalko.dao.impl.UserDAOImpl;

public class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();

    private final UserDAO userDAO = new UserDAOImpl();
    private final CarDAO carDAO = new CarDAOImpl();

    private DAOProvider() {}

    public static DAOProvider getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public CarDAO getCarDAO() {
        return carDAO;
    }
}
