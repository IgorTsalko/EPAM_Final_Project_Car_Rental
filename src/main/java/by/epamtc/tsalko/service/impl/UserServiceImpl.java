package by.epamtc.tsalko.service.impl;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private static final int MIN_EMAIL_LENGTH = 5;

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
    public void registration(RegistrationData data) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            data.setPassword(Encoder.encrypt(data.getPassword()));

            String email = data.getEmail();
            if (email != null && email.length() < MIN_EMAIL_LENGTH) {
                data.setEmail(null);
            }

            userDAO.registration(data);
        } catch (EntityAlreadyExistsDAOException e) {
            throw new EntityAlreadyExistsServiceException(e);
        } catch (DAOException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
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
    public void updateUserDetails(UserDetails userDetails) throws ServiceException {
        if (!UserValidator.userDetailsValidation(userDetails)) {
            logger.info(userDetails + " failed validation");
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
            logger.info(passport + " failed validation");
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
}
