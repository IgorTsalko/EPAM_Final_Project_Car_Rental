package by.epamtc.tsalko.dao;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.dao.exception.DAOException;

import java.util.List;

public interface OrderDAO {

    void addOrder(Order order) throws DAOException;

    List<Order> getUserOrders(int userID) throws DAOException;
    List<Order> getOrders(int offset, int rowsAmount) throws DAOException;
    Order getOrder(int orderID) throws DAOException;

    boolean updateOrder(Order order) throws DAOException;

    void setPayment(Order order) throws DAOException;
}
