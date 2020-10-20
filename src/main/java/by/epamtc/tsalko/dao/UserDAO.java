package by.epamtc.tsalko.dao;

import by.epamtc.tsalko.bean.*;
import by.epamtc.tsalko.bean.user.*;
import by.epamtc.tsalko.dao.exception.DAOException;

import java.util.List;

public interface UserDAO {

    User authorization(AuthorizationData authorizationData) throws DAOException;
    boolean registration(RegistrationData registrationData) throws DAOException;

    UserDetails getUserDetails(int userID) throws DAOException;
    List<User> getAllUsers() throws DAOException;
    List<Order> getUserOrders(int userID) throws DAOException;
    List<Order> getAllOrders() throws DAOException;
    Passport getUserPassport(int userID) throws DAOException;
    List<Long> getBankcardNumbers(int userID) throws DAOException;

    void updateUserDetails(UserDetails userDetails) throws DAOException;
    void updateUserPassport(Passport passport) throws DAOException;

    boolean addBankcard(Bankcard bankcard) throws DAOException;

    boolean deleteBankcard(int userID, long cardNumber) throws DAOException;
}
