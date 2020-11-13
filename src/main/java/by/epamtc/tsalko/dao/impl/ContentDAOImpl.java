package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.content.News;
import by.epamtc.tsalko.bean.content.OrderStatus;
import by.epamtc.tsalko.bean.content.Rating;
import by.epamtc.tsalko.bean.content.Role;
import by.epamtc.tsalko.dao.ContentDAO;
import by.epamtc.tsalko.dao.connection.ConnectionPool;
import by.epamtc.tsalko.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContentDAOImpl implements ContentDAO {

    private static final Logger logger = LogManager.getLogger(ContentDAOImpl.class);

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SELECT_ALL_ROLES = "SELECT * FROM user_roles";
    private static final String SELECT_ROLE_BY_ID = "SELECT * FROM user_roles WHERE user_role_id=?";
    private static final String SELECT_ALL_RATINGS = "SELECT * FROM user_ratings";
    private static final String SELECT_RATING_BY_ID = "SELECT * FROM user_ratings WHERE user_rating_id=?";
    private static final String SELECT_ALL_ORDER_STATUSES = "SELECT * FROM order_statuses";
    private static final String SELECT_ALL_NEWS = "SELECT * FROM news";

    private static final String COLUMN_USER_ID = "user_id";

    private static final String COLUMN_ROLE_ID = "user_role_id";
    private static final String COLUMN_ROLE = "user_role";

    private static final String COLUMN_RATING_ID = "user_rating_id";
    private static final String COLUMN_RATING = "user_rating";
    private static final String COLUMN_DISCOUNT = "user_discount";

    private static final String COLUMN_ORDER_STATUS_ID = "order_status_id";
    private static final String COLUMN_ORDER_STATUS = "order_status";

    private static final String COLUMN_NEWS_TITLE_RU = "news_title_ru";
    private static final String COLUMN_NEWS_TITLE_EN = "news_title_en";
    private static final String COLUMN_NEWS_TEXT_RU = "news_text_ru";
    private static final String COLUMN_NEWS_TEXT_EN = "news_text_en";

    @Override
    public List<Role> getAllRoles() throws DAOException {
        List<Role> allRoles;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_ROLES);
            resultSet = preparedStatement.executeQuery();

            allRoles = new ArrayList<>();

            while (resultSet.next()) {
                Role role = new Role();
                role.setRoleID(resultSet.getInt(COLUMN_ROLE_ID));
                role.setRoleName(resultSet.getString(COLUMN_ROLE));
                allRoles.add(role);
            }
        } catch (SQLException e) {
            logger.error("Severe database error! Could not retrieve all roles!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return allRoles;
    }

    @Override
    public Role getRoleByID(int roleID) throws DAOException {
        Role role;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ROLE_BY_ID);
            preparedStatement.setInt(1, roleID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                role = new Role();
                role.setRoleID(resultSet.getInt(COLUMN_ROLE_ID));
                role.setRoleName(resultSet.getString(COLUMN_ROLE));
            } else {
                throw new DAOException("Could not retrieve role.");
            }
        } catch (SQLException e) {
            logger.error("Severe database error! Could not retrieve role!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return role;
    }

    @Override
    public List<Rating> getAllRatings() throws DAOException {
        List<Rating> allRatings;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_RATINGS);
            resultSet = preparedStatement.executeQuery();

            allRatings = new ArrayList<>();

            while (resultSet.next()) {
                Rating rating = new Rating();
                rating.setRatingID(resultSet.getInt(COLUMN_RATING_ID));
                rating.setRatingName(resultSet.getString(COLUMN_RATING));
                rating.setDiscount(resultSet.getInt(COLUMN_DISCOUNT));
                allRatings.add(rating);
            }
        } catch (SQLException e) {
            logger.error("Severe database error! Could not retrieve all ratings!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return allRatings;
    }

    @Override
    public Rating getRatingByID(int ratingID) throws DAOException {
        Rating rating;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_RATING_BY_ID);
            preparedStatement.setInt(1, ratingID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                rating = new Rating();
                rating.setRatingID(resultSet.getInt(COLUMN_RATING_ID));
                rating.setRatingName(resultSet.getString(COLUMN_RATING));
                rating.setDiscount(resultSet.getInt(COLUMN_DISCOUNT));
            } else {
                throw new DAOException("Could not retrieve rating.");
            }
        } catch (SQLException e) {
            logger.error("Severe database error! Could not retrieve rating!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return rating;
    }

    @Override
    public List<OrderStatus> getAllOrderStatuses() throws DAOException {
        List<OrderStatus> orderStatuses;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_ORDER_STATUSES);
            resultSet = preparedStatement.executeQuery();

            orderStatuses = new ArrayList<>();

            while (resultSet.next()) {
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setStatusID(resultSet.getInt(COLUMN_ORDER_STATUS_ID));
                orderStatus.setStatus(resultSet.getString(COLUMN_ORDER_STATUS));

                orderStatuses.add(orderStatus);
            }
        } catch (SQLException e) {
            logger.error("Severe database error! Could not retrieve all order statuses!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return orderStatuses;
    }

    @Override
    public List<News> getAllNews() throws DAOException {
        List<News> allNews;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_NEWS);
            resultSet = preparedStatement.executeQuery();

            allNews = new ArrayList<>();

            while (resultSet.next()) {
                News news = new News();
                news.setUserID(resultSet.getInt(COLUMN_USER_ID));
                news.setTitleRU(resultSet.getString(COLUMN_NEWS_TITLE_RU));
                news.setTitleEN(resultSet.getString(COLUMN_NEWS_TITLE_EN));
                news.setTextRU(resultSet.getString(COLUMN_NEWS_TEXT_RU));
                news.setTextEN(resultSet.getString(COLUMN_NEWS_TEXT_EN));

                allNews.add(news);
            }
        } catch (SQLException e) {
            logger.error("Severe database error! Could not retrieve all news!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return allNews;
    }
}
