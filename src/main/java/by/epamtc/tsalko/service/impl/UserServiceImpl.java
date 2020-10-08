package by.epamtc.tsalko.service.impl;

import by.epamtc.tsalko.bean.*;
import by.epamtc.tsalko.dao.DAOProvider;
import by.epamtc.tsalko.dao.UserDAO;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.UserAlreadyExistsDAOException;
import by.epamtc.tsalko.dao.exception.UserNotFoundDAOException;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;
import by.epamtc.tsalko.service.exception.UserAlreadyExistsServiceException;
import by.epamtc.tsalko.service.exception.UserNotFoundServiceException;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public User authorization(AuthorizationData authorizationData) throws ServiceException {
        User user;
        //todo: логическая валидация (выбрасываем исключение)

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            user = userDAO.authorization(authorizationData);
        } catch (UserNotFoundDAOException e) {
            throw new UserNotFoundServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public boolean registration(RegistrationData registrationData) throws ServiceException {
        boolean registration;
        //todo: логическая валидация (выбрасываем исключение)

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            registration = userDAO.registration(registrationData);
        } catch (UserAlreadyExistsDAOException e) {
            throw new UserAlreadyExistsServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return registration;
    }

    @Override
    public List<Order> getUserOrders(int userID) throws ServiceException {
        List<Order> userOrders;

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            userOrders = userDAO.getUserOrders(userID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return userOrders;
    }

    @Override
    public Passport getUserPassport(int userID) throws ServiceException {
        Passport passport;

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            passport = userDAO.getUserPassport(userID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return passport;
    }
}
