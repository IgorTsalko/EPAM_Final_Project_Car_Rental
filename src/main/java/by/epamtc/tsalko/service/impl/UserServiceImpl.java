package by.epamtc.tsalko.service.impl;

import by.epamtc.tsalko.bean.*;
import by.epamtc.tsalko.bean.user.*;
import by.epamtc.tsalko.dao.DAOProvider;
import by.epamtc.tsalko.dao.UserDAO;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.EntityAlreadyExistsDAOException;
import by.epamtc.tsalko.dao.exception.EntityNotFoundDAOException;
import by.epamtc.tsalko.service.UserValidator;
import by.epamtc.tsalko.service.encrypt.Encoder;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.InvalidInputDataServiceException;
import by.epamtc.tsalko.service.exception.ServiceException;
import by.epamtc.tsalko.service.exception.EntityAlreadyExistsServiceException;
import by.epamtc.tsalko.service.exception.EntityNotFoundServiceException;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public User authorization(AuthorizationData data) throws ServiceException {
        User user;

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            String password = Encoder.encrypt(data.getPassword());
            data.setPassword(password);

            user = userDAO.authorization(data);
        } catch (EntityNotFoundDAOException e) {
            throw new EntityNotFoundServiceException(e);
        } catch (DAOException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public boolean registration(RegistrationData data) throws ServiceException {
        boolean registration;

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            String password = Encoder.encrypt(data.getPassword());
            data.setPassword(password);

            registration = userDAO.registration(data);
        } catch (EntityAlreadyExistsDAOException e) {
            throw new EntityAlreadyExistsServiceException(e);
        } catch (DAOException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }

        return registration;
    }

    @Override
    public UserDetails getUserDetails(int userID) throws ServiceException {
        UserDetails userDetails;

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            userDetails = userDAO.getUserDetails(userID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return userDetails;
    }

    @Override
    public List<User> getUsers(int offset, int linesAmount) throws ServiceException {
        List<User> allUsers;

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            allUsers = userDAO.getUsers(offset, linesAmount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return allUsers;
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
    public List<Order> getOrders(int offset, int linesAmount) throws ServiceException {
        List<Order> allOrders;

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            allOrders = userDAO.getOrders(offset, linesAmount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return allOrders;
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

    @Override
    public List<Long> getBankcardNumbers(int userID) throws ServiceException {
        List<Long> bankcards;

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            bankcards = userDAO.getBankcardNumbers(userID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return bankcards;
    }

    @Override
    public void updateUserDetails(UserDetails userDetails) throws ServiceException {
        if (!UserValidator.userDetailsValidation(userDetails)) {
            throw new InvalidInputDataServiceException();
        }

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            userDAO.updateUserDetails(userDetails);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUserPassport(Passport passport) throws ServiceException {
        if (!UserValidator.passportValidation(passport)) {
            throw new InvalidInputDataServiceException();
        }

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            userDAO.updateUserPassport(passport);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addBankcard(Bankcard bankCard) throws ServiceException {
        if (!UserValidator.bankCardValidation(bankCard)) {
            throw new InvalidInputDataServiceException();
        }

        boolean bankcardAdded;

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            bankcardAdded = userDAO.addBankcard(bankCard);
        } catch (EntityAlreadyExistsDAOException e) {
            throw new EntityAlreadyExistsServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return bankcardAdded;
    }

    @Override
    public boolean deleteBankcard(int userID, long cardNumber) throws ServiceException {
        boolean bankcardDeleted;

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            bankcardDeleted = userDAO.deleteBankcard(userID, cardNumber);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return bankcardDeleted;
    }
}
