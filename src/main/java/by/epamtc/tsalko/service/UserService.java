package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.bean.UserData;
import by.epamtc.tsalko.service.exception.ServiceException;

public interface UserService {

    public User verification(String login, String password) throws ServiceException;
    public boolean registration(UserData userData) throws ServiceException;
}
