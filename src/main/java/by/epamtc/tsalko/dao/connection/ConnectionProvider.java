package by.epamtc.tsalko.dao.connection;

import by.epamtc.tsalko.dao.exception.ConnectionPoolException;

public class ConnectionProvider {

    private static ConnectionProvider instance;
    private static ConnectionPool connectionPool;

    private ConnectionProvider() {}

    public static ConnectionProvider getInstance() throws ConnectionPoolException {
        if (instance == null) {
            instance = new ConnectionProvider();
        }
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return instance;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }
}
