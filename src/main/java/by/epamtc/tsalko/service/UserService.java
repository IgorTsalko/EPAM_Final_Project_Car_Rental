package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.*;
import by.epamtc.tsalko.bean.user.*;
import by.epamtc.tsalko.service.exception.ServiceException;

import java.util.List;

public interface UserService {

    User authorization(AuthorizationData data) throws ServiceException;
    void registration(RegistrationData data) throws ServiceException;

    UserDetails getUserDetails(int userID) throws ServiceException;
    List<User> getUsers(int offset, int rowsAmount) throws ServiceException;
    List<Order> getUserOrders(int userID) throws ServiceException;
    List<Order> getOrders(int offset, int rowsAmount) throws ServiceException;
    Passport getUserPassport(int userID) throws ServiceException;
    List<Long> getBankcardNumbers(int userID) throws ServiceException;

    void updateUserDetails(UserDetails userDetails) throws ServiceException;
    void updateUserPassport(Passport passport) throws ServiceException;

    void createBankcard(Bankcard bankCard) throws ServiceException;
    void addOrder(Order order) throws ServiceException;

    void deleteBankcard(int userID, long cardNumber) throws ServiceException;
}
