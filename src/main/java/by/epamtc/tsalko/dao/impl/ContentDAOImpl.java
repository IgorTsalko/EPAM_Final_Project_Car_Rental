package by.epamtc.tsalko.dao.impl;

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
    private static final String SELECT_ALL_RATINGS = "SELECT * FROM user_ratings";

    private static final String COLUMN_ROLE_ID = "user_role_id";
    private static final String COLUMN_ROLE = "user_role";

    private static final String COLUMN_RATING_ID = "user_rating_id";
    private static final String COLUMN_RATING = "user_rating";
    private static final String COLUMN_DISCOUNT = "user_discount";

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
}
