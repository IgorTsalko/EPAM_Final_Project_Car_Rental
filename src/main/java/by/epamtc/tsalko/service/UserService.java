package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.*;
import by.epamtc.tsalko.service.exception.ServiceException;

import java.util.List;

public interface UserService {

    User authorization(AuthorizationData authorizationData) throws ServiceException;
    boolean registration(RegistrationData registrationData) throws ServiceException;
    List<Order> getUserOrders(int userID) throws ServiceException;
    List<Order> getAllOrders() throws ServiceException;
    Passport getUserPassport(int userID) throws ServiceException;
    List<Long> getUserCardAccounts(int userID) throws ServiceException;
}
