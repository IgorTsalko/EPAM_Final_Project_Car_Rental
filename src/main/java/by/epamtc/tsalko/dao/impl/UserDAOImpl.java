package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.content.Rating;
import by.epamtc.tsalko.bean.content.Role;
import by.epamtc.tsalko.bean.user.*;
import by.epamtc.tsalko.dao.UserDAO;
import by.epamtc.tsalko.dao.connection.ConnectionPool;
import by.epamtc.tsalko.dao.exception.*;
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
                    "rol.user_role_id, rol.user_role, rat.user_rating_id, rat.user_rating, rat.user_discount " +
                    "FROM users u JOIN user_roles rol ON u.user_role=rol.user_role_id " +
                    "JOIN user_ratings rat ON u.user_rating=rat.user_rating_id WHERE u.user_id=?";

    private static final String SELECT_USERS =
            "SELECT u.user_id, u.user_login, rol.user_role, rat.user_rating, rat.user_discount, u.user_registration_date " +
                    "FROM users u JOIN user_roles rol ON rol.user_role_id=u.user_role " +
                    "JOIN user_ratings rat ON u.user_rating=rat.user_rating_id " +
                    "ORDER BY user_role_id DESC LIMIT ?, ?";

    private static final String INSERT_NEW_USER =
            "INSERT INTO users (user_email, user_phone, user_login, user_password) " +
                    "VALUES (?, ?, ?, ?)";

    private static final String SELECT_USER_PASSPORT_BY_USER_ID =
            "SELECT p.user_id, p.user_passport_id, p.user_passport_series, p.user_passport_number, " +
                    "p.user_passport_date_of_issue, p.user_passport_issued_by, p.user_address, " +
                    "p.user_surname, p.user_name, p.user_thirdname, p.user_date_of_birth " +
                    "FROM user_passports p WHERE user_id=?";

    private static final String UPDATE_PASSPORT_BY_USER_ID
            = "UPDATE user_passports SET user_passport_series=?, user_passport_number=?, " +
            "user_passport_date_of_issue=?, user_passport_issued_by=?, user_address=?, user_surname=?, " +
            "user_name=?, user_thirdname=?, user_date_of_birth=? WHERE user_id=?";

    private static final String INSERT_PASSPORT
            = "INSERT INTO user_passports (user_passport_series, user_passport_number, " +
            "user_passport_date_of_issue, user_passport_issued_by, user_address, user_surname, " +
            "user_name, user_thirdname, user_date_of_birth, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_USER_DETAILS_BY_USER_ID
            = "UPDATE users SET user_role=?, user_rating=?, user_phone=?, user_email=? WHERE user_id=?";

    private static final String UPDATE_USER_LOGIN_BY_USER_ID
            = "UPDATE users SET user_login=? WHERE user_id=?";

    private static final String UPDATE_USER_PASSWORD_BY_USER_ID
            = "UPDATE users SET user_password=? WHERE user_id=? AND user_password=?";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_LOGIN = "user_login";
    private static final String COLUMN_USER_PHONE = "user_phone";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_REGISTRATION_DATE = "user_registration_date";

    private static final String COLUMN_USER_ROLE_ID = "user_role_id";
    private static final String COLUMN_USER_ROLE = "user_role";

    private static final String COLUMN_USER_RATING_ID = "user_rating_id";
    private static final String COLUMN_USER_RATING = "user_rating";
    private static final String COLUMN_USER_DISCOUNT = "user_discount";

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

    /**
     * Execute the SQL statement and return <code>User</code> object created from data
     * obtained from the database by authorizationData or throws exception if
     * such user does not exists. Never return <code>null</code>.
     *
     * @param authorizationData data about existing <code>User</code>
     * @return <code>User</code> object that contains the main data about user from database
     * @throws EntityNotFoundDAOException if corresponding user not found
     * @throws DAOException               if occurred severe problem with database
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

    /**
     * Enroll new <code>User</code> in data base if such user does not exist or throws exception.
     *
     * @param registrationData data about new <code>User</code>
     * @throws EntityAlreadyExistsDAOException if such user already exists
     * @throws DAOException                    if occurred severe problem with database
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

    /**
     * Execute the SQL statement and return <code>UserDetails</code> object created from data
     * obtained from the database by unique user identifier or throws exception if
     * such user does not exist. Never return <code>null</code>.
     *
     * @param userID unique user identifier in database
     * @return <code>UserDetails</code> object that contains the details data about user
     * from database.
     * @throws EntityNotFoundDAOException if corresponding user not found
     * @throws DAOException               if occurred severe problem with database
     */
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

            if (!resultSet.next()) {
                throw new EntityNotFoundDAOException();
            }

            userDetails = new UserDetails();

            Rating rating = new Rating();
            rating.setRatingID(resultSet.getInt(COLUMN_USER_RATING_ID));
            rating.setRatingName(resultSet.getString(COLUMN_USER_RATING));
            rating.setDiscount(resultSet.getDouble(COLUMN_USER_DISCOUNT));

            Role role = new Role();
            role.setRoleID(resultSet.getInt(COLUMN_USER_ROLE_ID));
            role.setRoleName(resultSet.getString(COLUMN_USER_ROLE));

            userDetails.setUserRating(rating);
            userDetails.setUserRole(role);
            userDetails.setUserID(resultSet.getInt(COLUMN_USER_ID));
            userDetails.setUserLogin(resultSet.getString(COLUMN_USER_LOGIN));
            userDetails.setUserPhone(resultSet.getString(COLUMN_USER_PHONE));
            userDetails.setUserEmail(resultSet.getString(COLUMN_USER_EMAIL));
            userDetails.setUserRegistrationDate(
                    resultSet.getTimestamp(COLUMN_USER_REGISTRATION_DATE).toLocalDateTime());
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

    /**
     * Execute the SQL statement and return list of objects <code>User</code> created from data
     * obtained from the database or return empty list if <code>offset</code> is outside
     * the number of records or records are missing or there are no any users.
     *
     * @param offset      index number from which to start extraction
     * @param linesAmount number of retrievable objects
     * @return list of <code>User</code> objects from database or empty list if
     * <code>offset</code> is outside the number of records or records are missing
     * @throws DAOException if occurred severe problem with database
     */
    @Override
    public List<User> getUsers(int offset, int linesAmount) throws DAOException {
        List<User> users = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USERS);
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, linesAmount);
            resultSet = preparedStatement.executeQuery();

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

    /**
     * Execute the SQL statement and return <code>Passport</code> object created from data
     * obtained from the database by unique user identifier or return <code>null</code>.
     *
     * @param userID unique user identifier in database
     * @return <code>Passport</code> object that describes unique user passport data
     * @throws DAOException if occurred severe problem with database
     */
    @Override
    public Passport getUserPassport(int userID) throws DAOException {
        Passport passport = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_PASSPORT_BY_USER_ID);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                passport = new Passport();

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
            }
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not retrieve user passport.", e);
            throw new DAOException("Could not retrieve user passport.", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return passport;
    }

    /**
     * Execute the SQL statement and update details user data in database.
     *
     * @param userDetails object that describes details data user
     * @throws UpdateDataDAOException if cannot update data in database
     * @throws DAOException           if occurred severe problem with database
     */
    @Override
    public void updateUserDetails(UserDetails userDetails) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_DETAILS_BY_USER_ID);
            preparedStatement.setInt(1, userDetails.getUserRole().getRoleID());
            preparedStatement.setInt(2, userDetails.getUserRating().getRatingID());
            preparedStatement.setString(3, userDetails.getUserPhone());
            preparedStatement.setString(4, userDetails.getUserEmail());
            preparedStatement.setInt(5, userDetails.getUserID());

            if (preparedStatement.executeUpdate() != 1) {
                logger.warn(userDetails + " was not enrolled");
                throw new UpdateDataDAOException("Could not update user details.");
            }
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not update user details.", e);
            throw new DAOException("Could not update user details.", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    /**
     * Execute the SQL statement and check if user already has <code>passport</code>.
     * If user does not have <code>passport</code>, add a new, if <code>passport</code> exist,
     * update it in database.
     *
     * @param passport object that describes user passport data
     * @throws EntityNotFoundDAOException if user for passport does not exist
     * @throws UpdateDataDAOException     if cannot update data in database
     * @throws DAOException               if occurred severe problem with database
     */
    @Override
    public void updateUserPassport(Passport passport) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_PASSPORT_BY_USER_ID);
            preparedStatement.setInt(1, passport.getUserID());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                preparedStatement = connection.prepareStatement(UPDATE_PASSPORT_BY_USER_ID);
            } else {
                preparedStatement = connection.prepareStatement(INSERT_PASSPORT);
            }

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

            if (preparedStatement.executeUpdate() != 1) {
                logger.warn(passport + " was not enrolled");
                throw new UpdateDataDAOException("Could not update user passport.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new EntityNotFoundDAOException(e);
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not update user passport.", e);
            throw new DAOException("Could not update user passport.", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
    }

    /**
     * Execute the SQL statement and update user <code>login</code> in database.
     *
     * @param userID       unique user identifier in database
     * @param newUserLogin new user login
     * @throws UpdateDataDAOException if cannot update data in database
     * @throws DAOException           if occurred severe problem with database
     */
    @Override
    public void updateUserLogin(int userID, String newUserLogin) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_LOGIN_BY_USER_ID);
            preparedStatement.setString(1, newUserLogin);
            preparedStatement.setInt(2, userID);

            if (preparedStatement.executeUpdate() != 1) {
                logger.warn("New login " + newUserLogin + " for userID: " + userID + " was not updated");
                throw new UpdateDataDAOException("Could not update user login.");
            }
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not update user login.", e);
            throw new DAOException("Could not update user login.", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    /**
     * Execute the SQL statement and if <code>oldPassword</code> and <code>newPassword</code>
     * equals update user password in database.
     *
     * @param userID      unique user identifier in database
     * @param oldPassword old user oldPassword
     * @param newPassword new user password
     * @throws UpdateDataDAOException if cannot update data in database
     * @throws DAOException           if occurred severe problem with database
     */
    @Override
    public void updateUserPassword(int userID, String oldPassword, String newPassword) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_PASSWORD_BY_USER_ID);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userID);
            preparedStatement.setString(3, oldPassword);

            if (preparedStatement.executeUpdate() != 1) {
                logger.warn("New password: " + newPassword + " for userID: " + userID
                        + "with old password: " + oldPassword + " was not updated");
                throw new UpdateDataDAOException("Could not update user password.");
            }
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not update user password.", e);
            throw new DAOException("Could not update user password.", e);
        } finally {
            if (connection != null) {
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
}
