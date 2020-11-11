package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.user.*;
import by.epamtc.tsalko.service.exception.ServiceException;

import java.util.List;

public interface UserService {

    User authorization(AuthorizationData data) throws ServiceException;
    void registration(RegistrationData data) throws ServiceException;

    UserDetails getUserDetails(int userID) throws ServiceException;
    List<User> getUsers(int offset, int rowsAmount) throws ServiceException;
    Passport getUserPassport(int userID) throws ServiceException;

    boolean updateUserDetails(UserDetails userDetails) throws ServiceException;
    boolean updateUserPassport(Passport passport) throws ServiceException;
    boolean updateUserLogin(int userID, String newUserLogin) throws ServiceException;
    boolean updateUserPassword(int userID, String oldPassword, String newPassword) throws ServiceException;
}
