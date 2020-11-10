package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.car.Car;
import by.epamtc.tsalko.dao.OrderDAO;
import by.epamtc.tsalko.dao.connection.ConnectionPool;
import by.epamtc.tsalko.dao.exception.ConnectionPoolError;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.EntityNotFoundDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private static final Logger logger = LogManager.getLogger(OrderDAOImpl.class);

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String INSERT_ORDER =
            "INSERT INTO user_orders (order_pick_up_date, order_drop_off_date, order_car_id, user_id) " +
                    "VALUES (?, ?, ?, ?)";

    private static final String INSERT_BILL = "INSERT INTO bills (bill_sum, user_order_id) VALUES (?, ?)";

    private static final String SELECT_ALL_ORDERS_BY_USER_ID =
            "SELECT u.user_login, u.user_id, o.order_id, o.order_date, o.order_pick_up_date, " +
                    "o.order_drop_off_date, o.order_is_paid, s.order_status, SUM(b.bill_sum) as bill_sum, " +
                    "c.car_id, c.car_brand, c.car_model, c.car_year_production, o.order_comment " +
                    "FROM user_orders o JOIN users u ON o.user_id=u.user_id " +
                    "JOIN order_statuses s ON o.order_status_id=s.order_status_id " +
                    "JOIN cars c ON o.order_car_id=c.car_id JOIN bills b ON b.user_order_id=o.order_id " +
                    "WHERE u.user_id=? GROUP BY s.order_status_id, o.order_date, o.order_id " +
                    "ORDER BY s.order_status_id, o.order_date DESC";

    private static final String SELECT_ORDER_BY_ORDER_ID =
            "SELECT u.user_login, u.user_id, o.order_id, o.order_date, o.order_pick_up_date, " +
                    "o.order_drop_off_date, o.order_is_paid, s.order_status, b.bill_sum, " +
                    "c.car_id, c.car_brand, c.car_model, c.car_year_production, o.order_comment " +
                    "FROM user_orders o JOIN users u ON o.user_id=u.user_id " +
                    "JOIN order_statuses s ON o.order_status_id=s.order_status_id " +
                    "JOIN cars c ON o.order_car_id=c.car_id JOIN bills b ON b.user_order_id=o.order_id " +
                    "WHERE o.order_id=?";

    private static final String SELECT_ORDERS =
            "SELECT u.user_login, u.user_id, o.order_id, o.order_date, o.order_pick_up_date, " +
                    "o.order_drop_off_date, o.order_is_paid, s.order_status, SUM(b.bill_sum) as bill_sum, " +
                    "c.car_id, c.car_brand, c.car_model, c.car_year_production, o.order_comment " +
                    "FROM user_orders o JOIN users u ON o.user_id=u.user_id " +
                    "JOIN order_statuses s ON o.order_status_id=s.order_status_id " +
                    "JOIN cars c ON o.order_car_id=c.car_id JOIN bills b ON b.user_order_id=o.order_id " +
                    "GROUP BY s.order_status_id, o.order_date, o.order_id " +
                    "ORDER BY s.order_status_id, o.order_date DESC LIMIT ?, ?";

    private static final String UPDATE_ORDER =
            "UPDATE user_orders JOIN bills ON user_orders.order_id=bills.user_order_id " +
                    "SET order_pick_up_date=?, order_drop_off_date=?, order_car_id=?, " +
                    "order_status_id=(SELECT order_status_id FROM order_statuses WHERE order_status=?), " +
                    "order_is_paid=?, order_comment=?, bill_sum=? WHERE order_id=?";

    private static final String UPDATE_ORDER_SET_PAYMENT
            = "UPDATE user_orders SET order_is_paid=1 WHERE order_id=?";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_LOGIN = "user_login";

    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_ORDER_DATE = "order_date";
    private static final String COLUMN_ORDER_STATUS = "order_status";
    private static final String COLUMN_ORDER_PICK_UP_DATE = "order_pick_up_date";
    private static final String COLUMN_ORDER_DROP_OFF_DATE = "order_drop_off_date";
    private static final String COLUMN_ORDER_IS_PAID = "order_is_paid";
    private static final String COLUMN_COMMENT = "order_comment";

    private static final String COLUMN_BILL_SUM = "bill_sum";

    private static final String COLUMN_CAR_ID = "car_id";
    private static final String COLUMN_CAR_BRAND = "car_brand";
    private static final String COLUMN_CAR_MODEL = "car_model";
    private static final String COLUMN_CAR_YEAR_PRODUCTION = "car_year_production";

    /**
     *
     * @param order
     * @throws DAOException
     */
    @Override
    public void addOrder(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            preparedStatement =
                    connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setDate(1, java.sql.Date.valueOf(order.getPickUpDate()));
            preparedStatement.setDate(2, java.sql.Date.valueOf(order.getDropOffDate()));
            preparedStatement.setInt(3, order.getCar().getCarID());
            preparedStatement.setInt(4, order.getUserID());
            preparedStatement.executeUpdate();

            int generatedKey = 0;
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            order.setOrderId(generatedKey);

            preparedStatement = connection.prepareStatement(INSERT_BILL);
            preparedStatement.setDouble(1, order.getTotalSum());
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
    public Order getOrder(int orderID) throws DAOException {
        Order order;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ORDER_ID);
            preparedStatement.setInt(1, orderID);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new EntityNotFoundDAOException();
            }

            order = createOrder(resultSet);
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not retrieve order.", e);
            throw new DAOException("Could not retrieve order.", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }

        return order;
    }

    @Override
    public boolean updateOrder(Order order) throws DAOException {
        boolean updated = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_ORDER);
            preparedStatement.setDate(1, java.sql.Date.valueOf(order.getPickUpDate()));
            preparedStatement.setDate(2, java.sql.Date.valueOf(order.getDropOffDate()));
            preparedStatement.setInt(3, order.getCar().getCarID());
            preparedStatement.setString(4, order.getOrderStatus());
            preparedStatement.setBoolean(5, order.isPaid());
            preparedStatement.setString(6, order.getComment());
            preparedStatement.setDouble(7, order.getTotalSum());
            preparedStatement.setInt(8, order.getOrderId());

            if (preparedStatement.executeUpdate() > 0) {
                updated = true;
            }
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not update user order.", e);
            throw new DAOException("Could not update user order.", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }

        return updated;
    }

    @Override
    public void setPayment(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_ORDER_SET_PAYMENT);
            preparedStatement.setInt(1, order.getOrderId());

            preparedStatement.executeUpdate();
        } catch (ConnectionPoolError | SQLException e) {
            logger.error("Severe database error! Could not update order payment.", e);
            throw new DAOException("Could not update order payment.", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    private Order createOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        Car car = new Car();

        order.setUserID(resultSet.getInt(COLUMN_USER_ID));
        order.setUserLogin(resultSet.getString(COLUMN_USER_LOGIN));
        order.setOrderId(resultSet.getInt(COLUMN_ORDER_ID));
        order.setOrderDate(resultSet.getTimestamp(COLUMN_ORDER_DATE).toLocalDateTime());
        order.setOrderStatus(resultSet.getString(COLUMN_ORDER_STATUS));
        order.setPickUpDate(resultSet.getDate(COLUMN_ORDER_PICK_UP_DATE).toLocalDate());
        order.setDropOffDate(resultSet.getDate(COLUMN_ORDER_DROP_OFF_DATE).toLocalDate());
        order.setTotalSum(resultSet.getDouble(COLUMN_BILL_SUM));
        order.setPaid(resultSet.getBoolean(COLUMN_ORDER_IS_PAID));
        order.setComment(resultSet.getString(COLUMN_COMMENT));

        car.setCarID(resultSet.getInt(COLUMN_CAR_ID));
        car.setBrand(resultSet.getString(COLUMN_CAR_BRAND));
        car.setModel(resultSet.getString(COLUMN_CAR_MODEL));
        car.setYearProduction(resultSet.getInt(COLUMN_CAR_YEAR_PRODUCTION));
        order.setCar(car);

        return order;
    }
}
