package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.AuthorizationData;
import by.epamtc.tsalko.bean.RegistrationData;
import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.dao.ConnectionProvider;
import by.epamtc.tsalko.dao.UserDAO;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.UserAlreadyExistsDAOException;
import by.epamtc.tsalko.dao.exception.UserNotFoundDAOException;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    private static final String SELECT_USER_BY_LOGIN_SQL =
            "SELECT u.user_id, u.user_login, rol.user_role, rat.user_rating " +
                    "FROM users u, user_roles rol, user_ratings rat " +
                    "WHERE u.user_login=? and u.user_password=? and u.user_role=rol.user_role_id and u.user_rating=rat.user_rating_id;";

    private static final String INSERT_NEW_USER_SQL =
            "INSERT INTO users (user_email, user_phone, user_login, user_password, user_rating, user_role) " +
                    "VALUES (?, ?, ?, ?, 1, 1)";

    @Override
    public User authorization(AuthorizationData authorizationData) throws DAOException {
        User user = null;

        ConnectionProvider connectionProvider;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connectionProvider = ConnectionProvider.getInstance();
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_SQL);

            preparedStatement.setString(1, authorizationData.getLogin());
            preparedStatement.setString(2, authorizationData.getPassword());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String id = resultSet.getString("user_id");
                String login = resultSet.getString("user_login");
                String role = resultSet.getString("user_role");
                String rating = resultSet.getString("user_rating");

                user = new User();
                user.setId(id);
                user.setLogin(login);
                user.setRole(role);
                user.setRating(rating);
            } else {
                throw new UserNotFoundDAOException();
            }
        } catch (SQLException e) {
            // todo: log. Что то предпринимаем, ошибка базы или запроса
        } catch (ClassNotFoundException e) {
            // todo: log. Что то предпринимаем, ошибка java или др.
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwable) {
                    // todo: log и что то предпринимаем. Ошибка решаеться на уровне DAO. Ошибка закрытия
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwable) {
                    // todo: log и что то предпринимаем. Ошибка решаеться на уровне DAO. Ошибка закрытия
                }
            }
        }

        return user;
    }

    @Override
    public boolean registration(RegistrationData registrationData) throws DAOException {
        boolean registration = false;

        ConnectionProvider connectionProvider;
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connectionProvider = ConnectionProvider.getInstance();
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_NEW_USER_SQL);

            preparedStatement.setString(1, registrationData.getEmail());
            preparedStatement.setString(2, registrationData.getPhone());
            preparedStatement.setString(3, registrationData.getLogin());
            preparedStatement.setString(4, registrationData.getPassword());

            if (preparedStatement.executeUpdate() == 1) {
                registration = true;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new UserAlreadyExistsDAOException(e);
        } catch (SQLException e) {
            // todo: log и что то предпринимаем. Ошибка решаеться на уровне DAO. Ошибка вставки
        } catch (ClassNotFoundException e) {
            // todo: log и что то предпринимаем. Ошибка решаеться на уровне DAO. Ошибка базы
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwable) {
                    // todo: log и что то предпринимаем. Ошибка решаеться на уровне DAO. Ошибка закрытия
                }
            }
        }

        return registration;
    }

}
