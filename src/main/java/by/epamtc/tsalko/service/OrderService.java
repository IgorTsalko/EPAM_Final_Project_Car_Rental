package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.service.exception.ServiceException;

import java.util.List;

public interface OrderService {

    void createOrder(Order order) throws ServiceException;

    List<Order> getUserOrders(int userID) throws ServiceException;
    List<Order> getOrders(int offset, int rowsAmount) throws ServiceException;
    Order getOrder(int orderID) throws ServiceException;

    boolean updateOrder(Order order) throws ServiceException;

    void setPayment(Order order) throws ServiceException;
}