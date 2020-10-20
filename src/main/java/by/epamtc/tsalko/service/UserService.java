package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.*;
import by.epamtc.tsalko.bean.user.*;
import by.epamtc.tsalko.service.exception.ServiceException;

import java.util.List;

public interface UserService {

    User authorization(AuthorizationData authorizationData) throws ServiceException;
    boolean registration(RegistrationData registrationData) throws ServiceException;

    UserDetails getUserDetails(int userID) throws ServiceException;
    List<User> getAllUsers() throws ServiceException;
    List<Order> getUserOrders(int userID) throws ServiceException;
    List<Order> getAllOrders() throws ServiceException;
    Passport getUserPassport(int userID) throws ServiceException;
    List<Long> getBankcardNumbers(int userID) throws ServiceException;

    void updateUserDetails(UserDetails userDetails) throws ServiceException;
    void updateUserPassport(Passport passport) throws ServiceException;

    boolean addBankcard(Bankcard bankCard) throws ServiceException;

    boolean deleteBankcard(int userID, long cardNumber) throws ServiceException;
}
