package by.epamtc.tsalko.dao;

import by.epamtc.tsalko.bean.AuthorizationData;
import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.RegistrationData;
import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.dao.exception.DAOException;

import java.util.List;

public interface UserDAO {

    User authorization(AuthorizationData authorizationData) throws DAOException;
    boolean registration(RegistrationData registrationData) throws DAOException;
    List<Order> getUserOrders(int userID) throws DAOException;
}
