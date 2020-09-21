package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.bean.UserData;
import by.epamtc.tsalko.dao.ConnectionProvider;
import by.epamtc.tsalko.dao.UserDAO;
import by.epamtc.tsalko.dao.exception.DAOException;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    private static final String SELECT_USER_BY_LOGIN_SQL =
            "SELECT * FROM users WHERE user_login = ? AND user_password = ?";

    @Override
    public User verification(String login, String password) throws DAOException {
        User user = null;
        ConnectionProvider connectionProvider;
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connectionProvider = ConnectionProvider.getInstance();
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_SQL);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(Integer.parseInt(resultSet.getString("user_id")));
                user.setEmail(resultSet.getString("user_email"));
                user.setPhone(resultSet.getString("user_phone"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e);
        }

        return user;
    }

    @Override
    public boolean registration(UserData userData) throws DAOException {
        return false;
    }

}
