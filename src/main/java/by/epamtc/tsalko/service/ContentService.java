package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.content.News;
import by.epamtc.tsalko.bean.content.OrderStatus;
import by.epamtc.tsalko.bean.content.Rating;
import by.epamtc.tsalko.bean.content.Role;
import by.epamtc.tsalko.service.exception.ServiceException;

import java.util.List;

public interface ContentService {

    List<Role> getAllRoles() throws ServiceException;
    Role getRoleByID(int roleID) throws ServiceException;
    List<Rating> getAllRatings() throws ServiceException;
    Rating getRatingByID(int RatingID) throws ServiceException;
    List<OrderStatus> getAllOrderStatuses() throws ServiceException;

    List<News> getAllNews() throws ServiceException;
}
