package by.epamtc.tsalko.service.impl;

import by.epamtc.tsalko.bean.content.News;
import by.epamtc.tsalko.bean.content.OrderStatus;
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
    public Role getRoleByID(int roleID) throws ServiceException {
        Role role;

        DAOProvider daoProvider = DAOProvider.getInstance();
        ContentDAO contentDAO = daoProvider.getContentDAO();

        try {
            role = contentDAO.getRoleByID(roleID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return role;
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

    @Override
    public Rating getRatingByID(int RatingID) throws ServiceException {
        Rating rating;

        DAOProvider daoProvider = DAOProvider.getInstance();
        ContentDAO contentDAO = daoProvider.getContentDAO();

        try {
            rating = contentDAO.getRatingByID(RatingID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return rating;
    }

    @Override
    public List<OrderStatus> getAllOrderStatuses() throws ServiceException {
        List<OrderStatus> orderStatuses;

        DAOProvider daoProvider = DAOProvider.getInstance();
        ContentDAO contentDAO = daoProvider.getContentDAO();

        try {
            orderStatuses = contentDAO.getAllOrderStatuses();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return orderStatuses;
    }

    @Override
    public List<News> getAllNews() throws ServiceException {
        List<News> allNews;

        DAOProvider daoProvider = DAOProvider.getInstance();
        ContentDAO contentDAO = daoProvider.getContentDAO();

        try {
            allNews = contentDAO.getAllNews();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return allNews;
    }
}
