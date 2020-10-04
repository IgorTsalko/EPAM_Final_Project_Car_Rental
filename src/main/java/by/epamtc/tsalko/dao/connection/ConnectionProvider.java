package by.epamtc.tsalko.dao.connection;

import by.epamtc.tsalko.dao.exception.ConnectionPoolException;

import java.sql.Connection;

public class ConnectionProvider {

    private static final ConnectionProvider instance = new ConnectionProvider();

    private static ConnectionPool connectionPool;

    private ConnectionProvider() {}

    public static ConnectionProvider getInstance() {
        return instance;
    }

    public Connection getConnection() throws ConnectionPoolException {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool.takeConnection();
    }
}
