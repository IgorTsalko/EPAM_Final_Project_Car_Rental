package by.epamtc.tsalko.dao;

import by.epamtc.tsalko.bean.AuthorizationData;
import by.epamtc.tsalko.bean.RegistrationData;
import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.dao.exception.DAOException;

public interface UserDAO {

    User authorization(AuthorizationData authorizationData) throws DAOException;
    boolean registration(RegistrationData registrationData) throws DAOException;
}
