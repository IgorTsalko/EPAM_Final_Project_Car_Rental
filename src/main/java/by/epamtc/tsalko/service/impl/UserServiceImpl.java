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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final String MOCK_PASSWORD = "12345678";
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
            String password = data.getPassword();
            if (password == null) {
                password = MOCK_PASSWORD;
            }
            data.setPassword(Encoder.encrypt(password));

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
    public void createBankcard(Bankcard bankCard) throws ServiceException {
        if (!UserValidator.bankCardValidation(bankCard)) {
            throw new InvalidInputDataServiceException();
        }

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            userDAO.createBankcard(bankCard);
        } catch (EntityAlreadyExistsDAOException e) {
            throw new EntityAlreadyExistsServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addOrder(Order order) throws ServiceException {
        if (!UserValidator.orderValidation(order)) {
            throw new InvalidInputDataServiceException();
        }

        LocalDate pickUpDate = order.getPickUpDate();
        LocalDate dropOffDate = order.getDropOffDate();

        if (pickUpDate != null && dropOffDate != null) {
            long amountOfDays = ChronoUnit.DAYS.between(pickUpDate, dropOffDate);
            double pricePerDay = order.getCar().getPricePerDay();
            double billSum = amountOfDays * pricePerDay;

            double discount = order.getDiscount();
            if (discount > 0) {
                billSum = (pricePerDay * (1 - discount / 100)) * amountOfDays;
            }
            order.setBillSum(billSum);
        }

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            userDAO.addOrder(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBankcard(int userID, long cardNumber) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            userDAO.deleteBankcard(userID, cardNumber);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
