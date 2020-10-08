package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.bean.AuthorizationData;
import by.epamtc.tsalko.bean.RegistrationData;
import by.epamtc.tsalko.service.exception.ServiceException;

import java.util.List;

public interface UserService {

    User authorization(AuthorizationData authorizationData) throws ServiceException;
    boolean registration(RegistrationData registrationData) throws ServiceException;
    List<Order> getUserOrders(int userID) throws ServiceException;
}
