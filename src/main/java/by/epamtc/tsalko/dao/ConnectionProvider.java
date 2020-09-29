package by.epamtc.tsalko.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private static final ConnectionProvider instance = new ConnectionProvider();

    private Connection connection;

    private final String URL = "jdbc:mysql://localhost/car_rental?serverTimezone=UTC&useSSL=false";
    private final String USER_NAME = "root";
    private final String PASSWORD = "4january";

    private ConnectionProvider() {}


    public static ConnectionProvider getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        }
        return connection;
    }
}
