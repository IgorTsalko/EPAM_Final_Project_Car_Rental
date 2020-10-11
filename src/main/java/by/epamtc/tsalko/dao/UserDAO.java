package by.epamtc.tsalko.dao;

import by.epamtc.tsalko.bean.*;
import by.epamtc.tsalko.dao.exception.DAOException;

import java.util.List;

public interface UserDAO {

    User authorization(AuthorizationData authorizationData) throws DAOException;
    boolean registration(RegistrationData registrationData) throws DAOException;
    List<Order> getUserOrders(int userID) throws DAOException;
    Passport getUserPassport(int userID) throws DAOException;
    List<Long> getUserCardAccounts(int userID) throws DAOException;
}
