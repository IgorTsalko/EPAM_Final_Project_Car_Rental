package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.*;
import by.epamtc.tsalko.dao.UserDAO;
import by.epamtc.tsalko.dao.connection.ConnectionPool;
import by.epamtc.tsalko.dao.exception.ConnectionPoolError;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.UserAlreadyExistsDAOException;
import by.epamtc.tsalko.dao.exception.EntityNotFoundDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SELECT_USER_BY_LOGIN =
            "SELECT u.user_id, u.user_login, rol.user_role, rat.user_rating, rat.user_discount " +
                    "FROM users u JOIN user_roles rol ON u.user_role=rol.user_role_id " +
                    "JOIN user_ratings rat ON u.user_rating=rat.user_rating_id WHERE u.user_login=? " +
                    "and u.user_password=?";

    private static final String SELECT_ALL_USERS =
            "SELECT u.user_id, u.user_login, rol.user_role, rat.user_rating, rat.user_discount " +
                    "FROM users u JOIN user_roles rol ON u.user_role=rol.user_role_id " +
                    "JOIN user_ratings rat ON u.user_rating=rat.user_rating_id ORDER BY user_role_id DESC, u.user_id";

    private static final String INSERT_NEW_USER =
            "INSERT INTO users (user_email, user_phone, user_login, user_password, user_rating, user_role) " +
                    "VALUES (?, ?, ?, ?, 1, 1)";

    private static final String SELECT_ALL_USER_ORDERS =
            "SELECT o.user_id, o.order_id, o.order_date, s.order_status, o.order_rental_start," +
                    "o.order_rental_end, o.order_car_id, o.order_price, c.car_brand, c.car_model, " +
                    "o.manager_id, o.order_comment " +
                    "FROM user_orders o JOIN cars c ON o.order_car_id=c.car_id JOIN order_statuses s " +
                    "ON o.order_status=s.order_status_id WHERE o.user_id=? ORDER BY order_date DESC";

    private static final String SELECT_ALL_ORDERS =
            "SELECT o.user_id, o.order_id, o.order_date, s.order_status, o.order_rental_start," +
                    "o.order_rental_end, o.order_car_id, o.order_price, c.car_brand, c.car_model, " +
                    "o.manager_id, o.order_comment " +
                    "FROM user_orders o JOIN cars c ON o.order_car_id=c.car_id JOIN order_statuses s " +
                    "ON o.order_status=s.order_status_id ORDER BY s.order_status_id, o.order_date DESC";

    private static final String SELECT_USER_PASSPORT =
            "SELECT p.user_id, p.user_passport_id, p.user_passport_series, p.user_passport_number, " +
                    "p.user_passport_date_of_issue, p.user_passport_issued_by, p.user_address, " +
                    "p.user_surname, p.user_name, p.user_thirdname, p.user_date_of_birth " +
                    "FROM user_passports p WHERE user_id=?";

    private static final String SELECT_ALL_USER_CARD_ACCOUNTS =
            "SELECT user_card_account FROM user_cards WHERE user_id=?";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_LOGIN = "user_login";
    private static final String COLUMN_USER_ROLE = "user_role";
    private static final String COLUMN_USER_RATING = "user_rating";
    private static final String COLUMN_USER_DISCOUNT = "user_discount";

    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_ORDER_DATE = "order_date";
    private static final String COLUMN_ORDER_STATUS = "order_status";
    private static final String COLUMN_ORDER_RENTAL_START = "order_rental_start";
    private static final String COLUMN_ORDER_RENTAL_END = "order_rental_end";
    private static final String COLUMN_ORDER_CAR_ID = "order_car_id";
    private static final String COLUMN_ORDER_PRICE = "order_price";
    private static final String COLUMN_COMMENT = "order_comment";

    private static final String COLUMN_PASSPORT_ID = "user_passport_id";
    private static final String COLUMN_PASSPORT_SERIES = "user_passport_series";
    private static final String COLUMN_PASSPORT_NUMBER = "user_passport_number";
    private static final String COLUMN_PASSPORT_DATE_OF_ISSUE = "user_passport_date_of_issue";
    private static final String COLUMN_PASSPORT_ISSUED_BY = "user_passport_issued_by";
    private static final String COLUMN_PASSPORT_USER_ADDRESS = "user_address";
    private static final String COLUMN_PASSPORT_USER_SURNAME = "user_surname";
    private static final String COLUMN_PASSPORT_USER_NAME = "user_name";
    private static final String COLUMN_PASSPORT_USER_THIRDNAME = "user_thirdname";
    private static final String COLUMN_PASSPORT_USER_DATE_OF_BIRTH = "user_date_of_birth";

    private static final String COLUMN_CARD_ACCOUNT = "user_card_account";

    private static final String COLUMN_CAR_BRAND = "car_brand";
    private static final String COLUMN_CAR_MODEL = "car_model";

    private static final String COLUMN_MANAGER_ID = "manager_id";


    /** Executes SQL query and return object User if it exists or throws exception.
     * Never return null
     * @param authorizationData data about existing User
     * @return object User that contains data from data base about user
     * @exception DAOException if occurred severe problem with data base
     * @throws EntityNotFoundDAOException if corresponding User not found
     */
    @Override
    public User authorization(AuthorizationData authorizationData) throws DAOException {
        User user;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1, authorizationData.getLogin());
            preparedStatement.setString(2, authorizationData.getPassword());
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new EntityNotFoundDAOException();
            }

            user = createUser(resultSet);
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Cannot authorize", e);
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
            preparedStatement = connection.prepareStatement(INSERT_NEW_USER);

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
            logger.error("Severe database error! Cannot register", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }

        return registration;
    }

    @Override
    public List<User> getAllUsers() throws DAOException {
        List<User> users;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
            resultSet = preparedStatement.executeQuery();

            users = new ArrayList<>();

            while (resultSet.next()) {
                User user = createUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Severe database error! Cannot retrieve all user orders!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return users;
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
                Order order = createOrder(resultSet);
                userOrders.add(order);
            }
        } catch (SQLException e) {
            logger.error("Severe database error! Cannot retrieve user orders!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return userOrders;
    }

    @Override
    public List<Order> getAllOrders() throws DAOException {
        List<Order> allOrders = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = createOrder(resultSet);
                allOrders.add(order);
            }
        } catch (SQLException e) {
            logger.error("Severe database error! Cannot retrieve all user orders!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return allOrders;
    }

    @Override
    public Passport getUserPassport(int userID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_PASSPORT);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            Passport passport = new Passport();

            passport.setUserID(resultSet.getInt(COLUMN_USER_ID));
            passport.setPassportID(resultSet.getInt(COLUMN_PASSPORT_ID));
            passport.setPassportSeries(resultSet.getString(COLUMN_PASSPORT_SERIES));
            passport.setPassportNumber(resultSet.getString(COLUMN_PASSPORT_NUMBER));
            passport.setPassportDateOfIssue(resultSet.getDate(COLUMN_PASSPORT_DATE_OF_ISSUE));
            passport.setPassportIssuedBy(resultSet.getString(COLUMN_PASSPORT_ISSUED_BY));
            passport.setPassportUserAddress(resultSet.getString(COLUMN_PASSPORT_USER_ADDRESS));
            passport.setPassportUserSurname(resultSet.getString(COLUMN_PASSPORT_USER_SURNAME));
            passport.setPassportUserName(resultSet.getString(COLUMN_PASSPORT_USER_NAME));
            passport.setPassportUserThirdName(resultSet.getString(COLUMN_PASSPORT_USER_THIRDNAME));
            passport.setPassportUserDateOfBirth(resultSet.getDate(COLUMN_PASSPORT_USER_DATE_OF_BIRTH));

            return passport;
        } catch (SQLException e) {
            logger.error("Severe database error! Cannot retrieve user passport!", e);
            throw new DAOException(e);
        }  finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
    }

    @Override
    public List<Long> getUserCardAccounts(int userID) throws DAOException {
        List<Long> userCardAccounts = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_USER_CARD_ACCOUNTS);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long cardAccount = resultSet.getLong(COLUMN_CARD_ACCOUNT);
                userCardAccounts.add(cardAccount);
            }
        } catch (SQLException e) {
            logger.error("Severe database error! Cannot retrieve user cards!", e);
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return userCardAccounts;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(COLUMN_USER_ID));
        user.setLogin(resultSet.getString(COLUMN_USER_LOGIN));
        user.setRole(resultSet.getString(COLUMN_USER_ROLE));
        user.setRating(resultSet.getString(COLUMN_USER_RATING));
        user.setDiscount(resultSet.getInt(COLUMN_USER_DISCOUNT));

        return user;
    }

    private Order createOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setUserID(resultSet.getInt(COLUMN_USER_ID));
        order.setOrderId(resultSet.getInt(COLUMN_ORDER_ID));
        order.setOrderDate(resultSet.getDate(COLUMN_ORDER_DATE));
        order.setOrderStatus(resultSet.getString(COLUMN_ORDER_STATUS));
        order.setRentalStart(resultSet.getDate(COLUMN_ORDER_RENTAL_START));
        order.setRentalEnd(resultSet.getDate(COLUMN_ORDER_RENTAL_END));
        order.setCarID(resultSet.getInt(COLUMN_ORDER_CAR_ID));
        order.setCarBrand(resultSet.getString(COLUMN_CAR_BRAND));
        order.setCarModel(resultSet.getString(COLUMN_CAR_MODEL));
        order.setCarPricePerDay(resultSet.getString(COLUMN_ORDER_PRICE));
        order.setOrderPrice(resultSet.getString(COLUMN_ORDER_PRICE));
        order.setComment(resultSet.getString(COLUMN_COMMENT));
        order.setManagerID(resultSet.getInt(COLUMN_MANAGER_ID));

        return order;
    }
}
