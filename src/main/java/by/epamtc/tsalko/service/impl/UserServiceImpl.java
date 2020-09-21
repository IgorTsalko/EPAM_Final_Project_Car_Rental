package by.epamtc.tsalko.service.impl;

import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.bean.UserData;
import by.epamtc.tsalko.dao.DAOProvider;
import by.epamtc.tsalko.dao.UserDAO;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;

public class UserServiceImpl implements UserService {

    @Override
    public User verification(String login, String password) throws ServiceException {
        User user;
        //todo: валидация, в случае некоректного ввода выбрасываем исключение
        // (меньше двух символов например, логическая валидация)

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            user = userDAO.verification(login, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public boolean registration(UserData userData) throws ServiceException {
        return false;
    }
}
