package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.AuthorizationData;
import by.epamtc.tsalko.bean.RegistrationData;
import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.dao.UserDAO;
import by.epamtc.tsalko.dao.connection.ConnectionPool;
import by.epamtc.tsalko.dao.connection.ConnectionProvider;
import by.epamtc.tsalko.dao.exception.ConnectionPoolException;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.UserAlreadyExistsDAOException;
import by.epamtc.tsalko.dao.exception.UserNotFoundDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    private static final String SELECT_USER_BY_LOGIN_SQL =
            "SELECT u.user_login, rol.user_role, rat.user_rating " +
                    "FROM users u JOIN user_roles rol ON u.user_role=rol.user_role_id " +
                    "JOIN user_ratings rat ON u.user_rating=rat.user_rating_id WHERE u.user_login=? and u.user_password=?;";

    private static final String INSERT_NEW_USER_SQL =
            "INSERT INTO users (user_email, user_phone, user_login, user_password, user_rating, user_role) " +
                    "VALUES (?, ?, ?, ?, 1, 1)";


    /** Executes SQL query and return object User if it exists or throws exception.
     * Never return null
     * @param authorizationData data about existing User
     * @return object User that contains data from data base about user
     * @exception DAOException if occurred severe problem with connection to data base
     * @throws UserNotFoundDAOException if corresponding User not found
     */
    @Override
    public User authorization(AuthorizationData authorizationData) throws DAOException {
        User user;

        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connectionPool = ConnectionProvider.getInstance().getConnectionPool();
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_SQL);

            preparedStatement.setString(1, authorizationData.getLogin());
            preparedStatement.setString(2, authorizationData.getPassword());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String login = resultSet.getString("user_login");
                String role = resultSet.getString("user_role");
                String rating = resultSet.getString("user_rating");

                user = new User();
                user.setLogin(login);
                user.setRole(role);
                user.setRating(rating);
            } else {
                throw new UserNotFoundDAOException();
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Severe database error!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return user;
    }

    /** Executes SQL query and enroll new User if in data base or throws exception.
     * @param registrationData data about new User
     * @return true if managed to enroll a new User
     * @exception DAOException if occurred severe problem with connection to data base
     * @throws UserAlreadyExistsDAOException if User already exists
     */
    @Override
    public boolean registration(RegistrationData registrationData) throws DAOException {
        boolean registration = false;

        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connectionPool = ConnectionProvider.getInstance().getConnectionPool();
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(INSERT_NEW_USER_SQL);

            preparedStatement.setString(1, (registrationData.getEmail().length() > 1)
                    ?registrationData.getEmail() : null);
            preparedStatement.setString(2, registrationData.getPhone());
            preparedStatement.setString(3, registrationData.getLogin());
            preparedStatement.setString(4, registrationData.getPassword());

            if (preparedStatement.executeUpdate() == 1) {
                registration = true;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new UserAlreadyExistsDAOException(e);
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Severe database error!", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }

        return registration;
    }

}
