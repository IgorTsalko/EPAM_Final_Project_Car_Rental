package by.epamtc.tsalko.service.impl;

import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.bean.RegistrationData;
import by.epamtc.tsalko.bean.AuthorizationData;
import by.epamtc.tsalko.dao.DAOProvider;
import by.epamtc.tsalko.dao.UserDAO;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;

public class UserServiceImpl implements UserService {

    @Override
    public User authorization(AuthorizationData authorizationData) throws ServiceException {
        User user;
        //todo: логическая валидация (выбрасываем исключение)

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            user = userDAO.authorization(authorizationData);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public boolean registration(RegistrationData registrationData) throws ServiceException {
        boolean registration;
        //todo: логическая валидация (выбрасываем исключение)

        DAOProvider daoProvider = DAOProvider.getInstance();
        UserDAO userDAO = daoProvider.getUserDAO();

        try {
            registration = userDAO.registration(registrationData);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return registration;
    }
}
