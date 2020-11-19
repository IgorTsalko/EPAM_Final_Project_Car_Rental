package by.epamtc.tsalko.dao;

import by.epamtc.tsalko.dao.impl.*;

public class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();

    private final UserDAO userDAO = new UserDAOImpl();
    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final CarDAO carDAO = new CarDAOImpl();
    private final BankcardDAO bankcardDAO = new BankcardDAOImpl();
    private final ContentDAO contentDAO = new ContentDAOImpl();

    private DAOProvider() {
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public CarDAO getCarDAO() {
        return carDAO;
    }

    public BankcardDAO getBankcardDAO() {
        return bankcardDAO;
    }

    public ContentDAO getContentDAO() {
        return contentDAO;
    }
}
