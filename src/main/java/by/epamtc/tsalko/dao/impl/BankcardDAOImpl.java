package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.Bankcard;
import by.epamtc.tsalko.dao.BankcardDAO;
import by.epamtc.tsalko.dao.connection.ConnectionPool;
import by.epamtc.tsalko.dao.exception.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankcardDAOImpl implements BankcardDAO {

    private static final Logger logger = LogManager.getLogger(BankcardDAOImpl.class);

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String INSERT_BANKCARD =
            "INSERT INTO user_bankcards (user_bankcard_number, user_bankcard_valid_true, " +
                    "user_bankcard_firstname, user_bankcard_lastname, user_bankcard_cvv, user_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_BANKCARDS_BY_USER_ID =
            "SELECT user_id, user_bankcard_id, user_bankcard_number FROM user_bankcards WHERE user_id=?";

    private static final String SELECT_BANKCARD_BY_ID =
            "SELECT user_bankcard_id, user_bankcard_number, user_bankcard_valid_true, " +
                    "user_bankcard_firstname, user_bankcard_lastname, user_bankcard_cvv, user_id " +
                    "FROM user_bankcards WHERE user_bankcard_id=?";

    private static final String DELETE_BANKCARD_BY_USER_ID =
            "DELETE FROM user_bankcards WHERE user_id=? AND user_bankcard_id=?";

    private static final String COLUMN_USER_BANKCARD_ID = "user_bankcard_id";
    private static final String COLUMN_USER_BANKCARD_NUMBER = "user_bankcard_number";
    private static final String COLUMN_USER_BANKCARD_VALID_TRUE = "user_bankcard_valid_true";
    private static final String COLUMN_USER_BANKCARD_FIRSTNAME = "user_bankcard_firstname";
    private static final String COLUMN_USER_BANKCARD_LASTNAME = "user_bankcard_lastname";
    private static final String COLUMN_USER_BANKCARD_CVV = "user_bankcard_cvv";
    private static final String COLUMN_USER_ID = "user_id";

    /**
     * Execute the SQL statement and return list of objects <code>Bankcard</code>
     * created from data obtained from the database for specific user
     * by unique identifier or empty list if user do not have any bankcards or
     * user does not exist.
     *
     * @param userID unique user identifier in database
     * @return list of objects <code>Bankcard</code> that represent user bankcard or
     * empty list if user do not have any bankcards
     * @throws DAOException if occurred severe problem with database
     */
    @Override
    public List<Bankcard> getUserBankcards(int userID) throws DAOException {
        List<Bankcard> bankcards = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_BANKCARDS_BY_USER_ID);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Bankcard bankcard = new Bankcard();
                bankcard.setUserID(resultSet.getInt(COLUMN_USER_ID));
                bankcard.setBankcardID(resultSet.getInt(COLUMN_USER_BANKCARD_ID));
                bankcard.setBankcardNumber(resultSet.getLong(COLUMN_USER_BANKCARD_NUMBER));

                bankcards.add(bankcard);
            }
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not retrieve user bankcards.", e);
            throw new DAOException("Could not retrieve user bankcards.", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return bankcards;
    }

    /**
     * Execute the SQL statement and return object <code>Bankcard</code>
     * created from data obtained from the database for specific user
     * by unique bankcard identifier. Never return <code>null</code>.
     *
     * @param bankcardID unique bankcard identifier in database
     * @return <code>Bankcard</code> object that represent user bankcard
     * @throws EntityNotFoundDAOException if corresponding bankcard not found
     * @throws DAOException               if occurred severe problem with database
     */
    @Override
    public Bankcard getUserBankcard(int bankcardID) throws DAOException {
        Bankcard bankcard;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_BANKCARD_BY_ID);
            preparedStatement.setInt(1, bankcardID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                bankcard = new Bankcard();
                bankcard.setUserID(resultSet.getInt(COLUMN_USER_ID));
                bankcard.setBankcardID(resultSet.getInt(COLUMN_USER_BANKCARD_ID));
                bankcard.setBankcardNumber(resultSet.getLong(COLUMN_USER_BANKCARD_NUMBER));
                bankcard.setBankcardValidTrue(
                        resultSet.getDate(COLUMN_USER_BANKCARD_VALID_TRUE).toLocalDate());
                bankcard.setBankcardUserFirstname(resultSet.getString(COLUMN_USER_BANKCARD_FIRSTNAME));
                bankcard.setBankcardUserLastname(resultSet.getString(COLUMN_USER_BANKCARD_LASTNAME));
                bankcard.setBankcardCVV(resultSet.getString(COLUMN_USER_BANKCARD_CVV));
            } else {
                logger.info("Bankcard id: " + bankcardID + " not found");
                throw new EntityNotFoundDAOException();
            }
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not retrieve user bankcard.", e);
            throw new DAOException("Could not retrieve user bankcard.", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return bankcard;
    }

    /**
     * Enroll new <code>Bankcard</code> in data base for user. If such user do not
     * exists throws exception.
     *
     * @param bankCard data about new <code>Bankcard</code>
     * @throws UpdateDataDAOException if cannot add new data in database
     * @throws DAOException           if occurred severe problem with database
     */
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
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not add card.", e);
            throw new DAOException("Could not add card.", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    /**
     * Delete specific <code>Bankcard</code> in data base for user by <code>bankCardID</code>.
     * If such bankcard or user do not exists throws exception.
     *
     * @param userID     unique user identifier in database
     * @param bankCardID unique bankcard identifier in database
     * @throws UpdateDataDAOException if cannot update data in database
     * @throws DAOException           if occurred severe problem with database
     */
    @Override
    public void deleteBankcard(int userID, int bankCardID) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_BANKCARD_BY_USER_ID);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, bankCardID);

            if (preparedStatement.executeUpdate() != 1) {
                logger.warn("BankcardID: " + bankCardID + " for userID: " + userID + " was not deleted");
                throw new UpdateDataDAOException("Could not delete bankcard");
            }
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not delete bankcard.", e);
            throw new DAOException("Could not delete bankcard.", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }
}
