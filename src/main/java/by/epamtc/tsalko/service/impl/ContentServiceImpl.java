package by.epamtc.tsalko.service.impl;

import by.epamtc.tsalko.bean.content.Rating;
import by.epamtc.tsalko.bean.content.Role;
import by.epamtc.tsalko.dao.ContentDAO;
import by.epamtc.tsalko.dao.DAOProvider;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.service.ContentService;
import by.epamtc.tsalko.service.exception.ServiceException;

import java.util.List;

public class ContentServiceImpl implements ContentService {

    @Override
    public List<Role> getAllRoles() throws ServiceException {
        List<Role> allRoles;

        DAOProvider daoProvider = DAOProvider.getInstance();
        ContentDAO contentDAO = daoProvider.getContentDAO();

        try {
            allRoles = contentDAO.getAllRoles();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return allRoles;
    }

    @Override
    public List<Rating> getAllRatings() throws ServiceException {
        List<Rating> allRatings;

        DAOProvider daoProvider = DAOProvider.getInstance();
        ContentDAO contentDAO = daoProvider.getContentDAO();

        try {
            allRatings = contentDAO.getAllRatings();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return allRatings;
    }
}
