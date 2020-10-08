package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.AuthorizationData;
import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.RegistrationData;
import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.dao.UserDAO;
import by.epamtc.tsalko.dao.connection.ConnectionPool;
import by.epamtc.tsalko.dao.exception.ConnectionPoolError;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.UserAlreadyExistsDAOException;
import by.epamtc.tsalko.dao.exception.UserNotFoundDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SELECT_USER_BY_LOGIN_SQL =
            "SELECT u.user_id, u.user_login, rol.user_role, rat.user_rating " +
                    "FROM users u JOIN user_roles rol ON u.user_role=rol.user_role_id " +
                    "JOIN user_ratings rat ON u.user_rating=rat.user_rating_id WHERE u.user_login=? and u.user_password=?;";

    private static final String INSERT_NEW_USER_SQL =
            "INSERT INTO users (user_email, user_phone, user_login, user_password, user_rating, user_role) " +
                    "VALUES (?, ?, ?, ?, 1, 1)";

    private static final String SELECT_ALL_USER_ORDERS =
            "SELECT o.customer_id, o.user_order_id, o.user_order_date, s.order_status, o.rental_start," +
                    "o.rental_end, o.car_id, c.car_brand, c.car_model, c.car_price_per_day, o.manager_id " +
                    "FROM user_orders o JOIN cars c ON o.car_id=c.car_id JOIN order_statuses s " +
                    "ON o.order_status=s.order_status_id WHERE o.customer_id=? ORDER BY user_order_date DESC";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_CUSTOMER_ID = "customer_id";
    private static final String COLUMN_USER_LOGIN = "user_login";
    private static final String COLUMN_USER_ROLE = "user_role";
    private static final String COLUMN_USER_RATING = "user_rating";
    private static final String COLUMN_USER_ORDER_ID = "user_order_id";
    private static final String COLUMN_USER_ORDER_DATE = "user_order_date";
    private static final String COLUMN_ORDER_STATUS = "order_status";
    private static final String COLUMN_RENTAL_START = "rental_start";
    private static final String COLUMN_RENTAL_END = "rental_end";
    private static final String COLUMN_CAR_ID = "car_id";
    private static final String COLUMN_CAR_BRAND = "car_brand";
    private static final String COLUMN_CAR_MODEL = "car_model";
    private static final String COLUMN_CAR_PRICE_PER_DAY = "car_price_per_day";
    private static final String COLUMN_MANAGER_ID = "manager_id";


    /** Executes SQL query and return object User if it exists or throws exception.
     * Never return null
     * @param authorizationData data about existing User
     * @return object User that contains data from data base about user
     * @exception DAOException if occurred severe problem with data base
     * @throws UserNotFoundDAOException if corresponding User not found
     */
    @Override
    public User authorization(AuthorizationData authorizationData) throws DAOException {
        User user;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_SQL);

            preparedStatement.setString(1, authorizationData.getLogin());
            preparedStatement.setString(2, authorizationData.getPassword());

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new UserNotFoundDAOException();
            }

            int id = resultSet.getInt(COLUMN_USER_ID);
            String login = resultSet.getString(COLUMN_USER_LOGIN);
            String role = resultSet.getString(COLUMN_USER_ROLE);
            String rating = resultSet.getString(COLUMN_USER_RATING);

            user = new User();
            user.setId(id);
            user.setLogin(login);
            user.setRole(role);
            user.setRating(rating);
        } catch (ConnectionPoolError | SQLException e) {
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
     * @exception DAOException if occurred severe problem with data base
     * @throws UserAlreadyExistsDAOException if User already exists
     */
    @Override
    public boolean registration(RegistrationData registrationData) throws DAOException {
        boolean registration = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
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
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error!", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }

        return registration;
    }

    @Override
    public List<Order> getUserOrders(int userID) throws DAOException {
        List<Order> userOrders = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_USER_ORDERS);
            preparedStatement.setInt(1, userID);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();

                order.setCustomerID(resultSet.getInt(COLUMN_CUSTOMER_ID));
                order.setOrderId(resultSet.getInt(COLUMN_USER_ORDER_ID));
                order.setOrderDate(resultSet.getDate(COLUMN_USER_ORDER_DATE));
                order.setOrderStatus(resultSet.getString(COLUMN_ORDER_STATUS));
                order.setRentalStart(resultSet.getDate(COLUMN_RENTAL_START));
                order.setRentalEnd(resultSet.getDate(COLUMN_RENTAL_END));
                order.setCarID(resultSet.getInt(COLUMN_CAR_ID));
                order.setCarBrand(resultSet.getString(COLUMN_CAR_BRAND));
                order.setCarModel(resultSet.getString(COLUMN_CAR_MODEL));
                order.setCarPricePerDay(resultSet.getString(COLUMN_CAR_PRICE_PER_DAY));
                order.setManagerID(resultSet.getInt(COLUMN_MANAGER_ID));

                userOrders.add(order);
            }
        } catch (SQLException e) {
            logger.error("Severe database error!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return userOrders;
    }
}
