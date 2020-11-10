package by.epamtc.tsalko.dao;

import by.epamtc.tsalko.bean.content.OrderStatus;
import by.epamtc.tsalko.bean.content.Rating;
import by.epamtc.tsalko.bean.content.Role;
import by.epamtc.tsalko.dao.exception.DAOException;

import java.util.List;

public interface ContentDAO {

    List<Role> getAllRoles() throws DAOException;
    Role getRoleByID(int roleID) throws DAOException;
    List<Rating> getAllRatings() throws DAOException;
    Rating getRatingByID(int ratingID) throws DAOException;
    List<OrderStatus> getAllOrderStatuses() throws DAOException;
}
