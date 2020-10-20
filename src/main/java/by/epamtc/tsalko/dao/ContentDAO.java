package by.epamtc.tsalko.dao;

import by.epamtc.tsalko.bean.content.Rating;
import by.epamtc.tsalko.bean.content.Role;
import by.epamtc.tsalko.dao.exception.DAOException;

import java.util.List;

public interface ContentDAO {

    List<Role> getAllRoles() throws DAOException;
    List<Rating> getAllRatings() throws DAOException;
}
