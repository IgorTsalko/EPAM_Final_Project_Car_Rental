package by.epamtc.tsalko.dao;

import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.bean.UserData;
import by.epamtc.tsalko.dao.exception.DAOException;

public interface UserDAO {

    public User verification(String login, String password) throws DAOException;
    public boolean registration(UserData userData) throws DAOException;
}
