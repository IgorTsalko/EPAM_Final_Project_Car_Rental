package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.content.Rating;
import by.epamtc.tsalko.bean.content.Role;
import by.epamtc.tsalko.service.exception.ServiceException;

import java.util.List;

public interface ContentService {

    List<Role> getAllRoles() throws ServiceException;
    List<Rating> getAllRatings() throws ServiceException;
}
