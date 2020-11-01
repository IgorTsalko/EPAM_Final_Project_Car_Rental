package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.*;
import by.epamtc.tsalko.bean.user.*;
import by.epamtc.tsalko.dao.UserDAO;
import by.epamtc.tsalko.dao.connection.ConnectionPool;
import by.epamtc.tsalko.dao.exception.ConnectionPoolError;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.EntityAlreadyExistsDAOException;
import by.epamtc.tsalko.dao.exception.EntityNotFoundDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SELECT_USER_BY_LOGIN_AND_PASS =
            "SELECT u.user_id, u.user_login, u.user_registration_date, rol.user_role, rat.user_rating, " +
                    "rat.user_discount FROM users u JOIN user_roles rol ON u.user_role=rol.user_role_id " +
                    "JOIN user_ratings rat ON u.user_rating=rat.user_rating_id WHERE u.user_login=? " +
                    "and u.user_password=?";

    private static final String SELECT_USER_BY_ID =
            "SELECT u.user_id, u.user_login, u.user_email, u.user_phone, u.user_registration_date, " +
                    "rol.user_role, rat.user_rating, rat.user_discount " +
                    "FROM users u JOIN user_roles rol ON u.user_role=rol.user_role_id " +
                    "JOIN user_ratings rat ON u.user_rating=rat.user_rating_id WHERE u.user_id=?";

    private static final String SELECT_USERS =
            "SELECT u.user_id, u.user_login, u.user_registration_date, rol.user_role, rat.user_rating, " +
                    "rat.user_discount FROM users u JOIN user_roles rol ON u.user_role=rol.user_role_id " +
                    "JOIN user_ratings rat ON u.user_rating=rat.user_rating_id " +
                    "ORDER BY user_role_id DESC LIMIT ?, ?";

    private static final String INSERT_NEW_USER =
            "INSERT INTO users (user_email, user_phone, user_login, user_password) " +
                    "VALUES (?, ?, ?, ?)";
    
    private static final String INSERT_BANKCARD =
            "INSERT INTO user_bankcards (user_bankcard_number, user_bankcard_valid_true, user_bankcard_firstname, " +
                    "user_bankcard_lastname, user_bankcard_cvv, user_id) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String INSERT_ORDER =
            "INSERT INTO user_orders (order_pick_up_date, order_drop_off_date, order_car_id, user_id) " +
                    "VALUES (?, ?, ?, ?)";

    private static final String INSERT_BILL = "INSERT INTO bills (bill_sum, user_order_id) VALUES (?, ?)";

    private static final String SELECT_ALL_ORDERS_BY_USER_ID =
            "SELECT u.user_login, u.user_id, o.order_id, o.order_date, o.order_pick_up_date, " +
                    "o.order_drop_off_date, o.order_is_paid, s.order_status, SUM(b.bill_sum) as bill_sum, " +
                    "c.car_id, c.car_brand, c.car_model, o.order_comment " +
                    "FROM user_orders o JOIN users u ON o.user_id=u.user_id " +
                    "JOIN order_statuses s ON o.order_status=s.order_status_id " +
                    "JOIN cars c ON o.order_car_id=c.car_id JOIN bills b ON b.user_order_id=o.order_id " +
                    "WHERE u.user_id=? GROUP BY o.order_id ORDER BY s.order_status_id, o.order_date DESC";

    private static final String SELECT_ORDERS =
            "SELECT u.user_login, u.user_id, o.order_id, o.order_date, o.order_pick_up_date, " +
                    "o.order_drop_off_date, o.order_is_paid, s.order_status, SUM(b.bill_sum) as bill_sum, " +
                    "c.car_id, c.car_brand, c.car_model, o.order_comment " +
                    "FROM user_orders o JOIN users u ON o.user_id=u.user_id " +
                    "JOIN order_statuses s ON o.order_status=s.order_status_id " +
                    "JOIN cars c ON o.order_car_id=c.car_id JOIN bills b ON b.user_order_id=o.order_id " +
                    "GROUP BY o.order_id ORDER BY s.order_status_id, o.order_date DESC LIMIT ?, ?";

    private static final String SELECT_USER_PASSPORT_BY_USER_ID =
            "SELECT p.user_id, p.user_passport_id, p.user_passport_series, p.user_passport_number, " +
                    "p.user_passport_date_of_issue, p.user_passport_issued_by, p.user_address, " +
                    "p.user_surname, p.user_name, p.user_thirdname, p.user_date_of_birth " +
                    "FROM user_passports p WHERE user_id=?";

    private static final String SELECT_ALL_BANKCARD_NUMBERS_BY_USER_ID =
            "SELECT user_bankcard_number FROM user_bankcards WHERE user_id=?";

    private static final String UPDATE_USER_DETAILS_BY_USER_ID
            = "UPDATE users SET user_phone=?, user_email=? WHERE user_id=?";

    private static final String EX_UPDATE_USER_DETAILS_BY_USER_ID
            = "UPDATE users SET user_role=?, user_rating=?, user_phone=?, user_email=? WHERE user_id=?";

    private static final String UPDATE_PASSPORT_BY_USER_ID
            = "UPDATE user_passports SET user_passport_series=?, user_passport_number=?, " +
            "user_passport_date_of_issue=?, user_passport_issued_by=?, user_address=?, user_surname=?, " +
            "user_name=?, user_thirdname=?, user_date_of_birth=? WHERE user_id=?";

    private static final String DELETE_BANKCARD_BY_USER_ID =
            "DELETE FROM user_bankcards WHERE user_id=? AND user_bankcard_number=?;";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_LOGIN = "user_login";
    private static final String COLUMN_USER_ROLE = "user_role";
    private static final String COLUMN_USER_RATING = "user_rating";
    private static final String COLUMN_USER_DISCOUNT = "user_discount";
    private static final String COLUMN_USER_PHONE = "user_phone";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_REGISTRATION_DATE = "user_registration_date";

    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_ORDER_DATE = "order_date";
    private static final String COLUMN_ORDER_STATUS = "order_status";
    private static final String COLUMN_ORDER_PICK_UP_DATE = "order_pick_up_date";
    private static final String COLUMN_ORDER_DROP_OFF_DATE = "order_drop_off_date";
    private static final String COLUMN_ORDER_IS_PAID = "order_is_paid";
    private static final String COLUMN_COMMENT = "order_comment";

    private static final String COLUMN_BILL_SUM = "bill_sum";

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

    private static final String COLUMN_BANKCARD_NUMBER = "user_bankcard_number";

    private static final String COLUMN_CAR_ID = "car_id";
    private static final String COLUMN_CAR_BRAND = "car_brand";
    private static final String COLUMN_CAR_MODEL = "car_model";

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
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_AND_PASS);
            preparedStatement.setString(1, authorizationData.getLogin());
            preparedStatement.setString(2, authorizationData.getPassword());
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new EntityNotFoundDAOException();
            }

            user = createUser(resultSet);
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not authorize.", e);
            throw new DAOException("Could not authorize.", e);
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
     * @throws EntityAlreadyExistsDAOException if User already exists
     */
    @Override
    public void registration(RegistrationData registrationData) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(INSERT_NEW_USER);

            preparedStatement.setString(1, registrationData.getEmail());
            preparedStatement.setString(2, registrationData.getPhone());
            preparedStatement.setString(3, registrationData.getLogin());
            preparedStatement.setString(4, registrationData.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new EntityAlreadyExistsDAOException(e);
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not register.", e);
            throw new DAOException("Could not register.", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public UserDetails getUserDetails(int userID) throws DAOException {
        UserDetails userDetails;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            userDetails = new UserDetails();

            if (resultSet.next()) {
                userDetails.setUserID(resultSet.getInt(COLUMN_USER_ID));
                userDetails.setUserLogin(resultSet.getString(COLUMN_USER_LOGIN));
                userDetails.setUserRoleName(resultSet.getString(COLUMN_USER_ROLE));
                userDetails.setUserRatingName(resultSet.getString(COLUMN_USER_RATING));
                userDetails.setUserPhone(resultSet.getString(COLUMN_USER_PHONE));
                userDetails.setUserEmail(resultSet.getString(COLUMN_USER_EMAIL));
                userDetails.setUserRegistrationDate(
                        resultSet.getTimestamp(COLUMN_USER_REGISTRATION_DATE).toLocalDateTime());
            }
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not retrieve user details.", e);
            throw new DAOException("Could not retrieve user details.", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return userDetails;
    }

    @Override
    public List<User> getUsers(int offset, int linesAmount) throws DAOException {
        List<User> users;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USERS);
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, linesAmount);
            resultSet = preparedStatement.executeQuery();
            
            users = new ArrayList<>();

            while (resultSet.next()) {
                User user = createUser(resultSet);
                users.add(user);
            }
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not retrieve all user orders.", e);
            throw new DAOException("Could not retrieve all user orders.", e);
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
            preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS_BY_USER_ID);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = createOrder(resultSet);
                userOrders.add(order);
            }
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not retrieve user orders.", e);
            throw new DAOException("Could not retrieve user orders.", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return userOrders;
    }

    @Override
    public List<Order> getOrders(int offset, int linesAmount) throws DAOException {
        List<Order> allOrders = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ORDERS);
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, linesAmount);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = createOrder(resultSet);
                allOrders.add(order);
            }
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not retrieve all user orders.", e);
            throw new DAOException("Could not retrieve all user orders.", e);
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
            preparedStatement = connection.prepareStatement(SELECT_USER_PASSPORT_BY_USER_ID);
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
            passport.setPassportDateOfIssue(
                    resultSet.getDate(COLUMN_PASSPORT_DATE_OF_ISSUE).toLocalDate());
            passport.setPassportIssuedBy(resultSet.getString(COLUMN_PASSPORT_ISSUED_BY));
            passport.setPassportUserAddress(resultSet.getString(COLUMN_PASSPORT_USER_ADDRESS));
            passport.setPassportUserSurname(resultSet.getString(COLUMN_PASSPORT_USER_SURNAME));
            passport.setPassportUserName(resultSet.getString(COLUMN_PASSPORT_USER_NAME));
            passport.setPassportUserThirdName(resultSet.getString(COLUMN_PASSPORT_USER_THIRDNAME));
            passport.setPassportUserDateOfBirth(
                    resultSet.getDate(COLUMN_PASSPORT_USER_DATE_OF_BIRTH).toLocalDate());

            return passport;
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not retrieve user passport.", e);
            throw new DAOException("Could not retrieve user passport.", e);
        }  finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
    }

    @Override
    public List<Long> getBankcardNumbers(int userID) throws DAOException {
        List<Long> userCardAccounts = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_BANKCARD_NUMBERS_BY_USER_ID);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long cardAccount = resultSet.getLong(COLUMN_BANKCARD_NUMBER);
                userCardAccounts.add(cardAccount);
            }
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not retrieve user cards.", e);
            throw new DAOException("Could not retrieve user cards.", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return userCardAccounts;
    }

    @Override
    public void updateUserDetails(UserDetails userDetails) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();

            if (userDetails.getUserRoleID() > 0 && userDetails.getUserRatingID() > 0) {
                preparedStatement = connection.prepareStatement(EX_UPDATE_USER_DETAILS_BY_USER_ID);
                preparedStatement.setInt(1, userDetails.getUserRoleID());
                preparedStatement.setInt(2, userDetails.getUserRatingID());
                preparedStatement.setString(3, userDetails.getUserPhone());
                preparedStatement.setString(4, userDetails.getUserEmail());
                preparedStatement.setInt(5, userDetails.getUserID());
            } else {
                preparedStatement = connection.prepareStatement(UPDATE_USER_DETAILS_BY_USER_ID);
                preparedStatement.setString(1, userDetails.getUserPhone());
                preparedStatement.setString(2, userDetails.getUserEmail());
                preparedStatement.setInt(3, userDetails.getUserID());
            }

            preparedStatement.executeUpdate();
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not update user details.", e);
            throw new DAOException("Could not update user details.", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public void updateUserPassport(Passport passport) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_PASSPORT_BY_USER_ID);
            preparedStatement.setString(1, passport.getPassportSeries());
            preparedStatement.setString(2, passport.getPassportNumber());
            preparedStatement.setDate(3,
                    java.sql.Date.valueOf(passport.getPassportDateOfIssue()));
            preparedStatement.setString(4, passport.getPassportIssuedBy());
            preparedStatement.setString(5, passport.getPassportUserAddress());
            preparedStatement.setString(6, passport.getPassportUserSurname());
            preparedStatement.setString(7, passport.getPassportUserName());
            preparedStatement.setString(8, passport.getPassportUserThirdName());
            preparedStatement.setDate(9,
                    java.sql.Date.valueOf(passport.getPassportUserDateOfBirth()));
            preparedStatement.setInt(10, passport.getUserID());

            preparedStatement.executeUpdate();
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not update user passport.", e);
            throw new DAOException("Could not update user passport.", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public void createBankcard(Bankcard bankCard) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(INSERT_BANKCARD);
            preparedStatement.setLong(1, bankCard.getBankcardNumber());
            preparedStatement.setDate(2,
                    java.sql.Date.valueOf(bankCard.getBankcardValidTrue()));
            preparedStatement.setString(3, bankCard.getBankcardUserFirstname());
            preparedStatement.setString(4, bankCard.getBankcardUserLastname());
            preparedStatement.setString(5, bankCard.getBankcardCVV());
            preparedStatement.setInt(6, bankCard.getUserID());

            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new EntityAlreadyExistsDAOException(e);
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not add card.", e);
            throw new DAOException("Could not add card.", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public void addOrder(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            preparedStatement =
                    connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1,
                    java.sql.Date.valueOf(order.getPickUpDate()));
            preparedStatement.setDate(2,
                    java.sql.Date.valueOf(order.getDropOffDate()));
            preparedStatement.setInt(3, order.getCarID());
            preparedStatement.setInt(4, order.getUserID());
            preparedStatement.executeUpdate();

            int generatedKey = 0;
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            preparedStatement = connection.prepareStatement(INSERT_BILL);
            preparedStatement.setDouble(1, order.getBillSum());
            preparedStatement.setInt(2, generatedKey);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (ConnectionPoolError | SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                logger.error("Severe database error! Could not make rollback", ex);
            }
            logger.error("Severe database error! Could not add order.", e);
            throw new DAOException("Could not add order.", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public void deleteBankcard(int userID, long cardNumber) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_BANKCARD_BY_USER_ID);
            preparedStatement.setInt(1, userID);
            preparedStatement.setLong(2, cardNumber);

            preparedStatement.executeUpdate();
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not delete bankcard.", e);
            throw new DAOException("Could not delete bankcard.", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(COLUMN_USER_ID));
        user.setLogin(resultSet.getString(COLUMN_USER_LOGIN));
        user.setRole(resultSet.getString(COLUMN_USER_ROLE));
        user.setRating(resultSet.getString(COLUMN_USER_RATING));
        user.setDiscount(resultSet.getDouble(COLUMN_USER_DISCOUNT));
        user.setRegistrationDate(
                resultSet.getTimestamp(COLUMN_USER_REGISTRATION_DATE).toLocalDateTime());

        return user;
    }

    private Order createOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setUserID(resultSet.getInt(COLUMN_USER_ID));
        order.setUserLogin(resultSet.getString(COLUMN_USER_LOGIN));
        order.setOrderId(resultSet.getInt(COLUMN_ORDER_ID));
        order.setOrderDate(resultSet.getTimestamp(COLUMN_ORDER_DATE).toLocalDateTime());
        order.setOrderStatus(resultSet.getString(COLUMN_ORDER_STATUS));
        order.setPickUpDate(resultSet.getDate(COLUMN_ORDER_PICK_UP_DATE).toLocalDate());
        order.setDropOffDate(resultSet.getDate(COLUMN_ORDER_DROP_OFF_DATE).toLocalDate());
        order.setCarID(resultSet.getInt(COLUMN_CAR_ID));
        order.setCarBrand(resultSet.getString(COLUMN_CAR_BRAND));
        order.setCarModel(resultSet.getString(COLUMN_CAR_MODEL));
        order.setBillSum(resultSet.getDouble(COLUMN_BILL_SUM));
        order.setPaid(resultSet.getBoolean(COLUMN_ORDER_IS_PAID));
        order.setComment(resultSet.getString(COLUMN_COMMENT));

        return order;
    }
}
