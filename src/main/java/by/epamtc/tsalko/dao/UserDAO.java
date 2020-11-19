package by.epamtc.tsalko.dao;

import by.epamtc.tsalko.bean.user.*;
import by.epamtc.tsalko.dao.exception.DAOException;

import java.util.List;

public interface UserDAO {

    User authorization(AuthorizationData authorizationData) throws DAOException;

    void registration(RegistrationData registrationData) throws DAOException;

    UserDetails getUserDetails(int userID) throws DAOException;

    List<User> getUsers(int offset, int linesAmount) throws DAOException;

    Passport getUserPassport(int userID) throws DAOException;

    void updateUserDetails(UserDetails userDetails) throws DAOException;

    void updateUserPassport(Passport passport) throws DAOException;

    void updateUserLogin(int userID, String newUserLogin) throws DAOException;

    void updateUserPassword(int userID, String oldPassword, String newPassword) throws DAOException;
}
