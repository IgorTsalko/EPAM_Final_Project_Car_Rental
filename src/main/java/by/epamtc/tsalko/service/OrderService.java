package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.ReturnAct;
import by.epamtc.tsalko.service.exception.ServiceException;

import java.util.List;

public interface OrderService {

    void createOrder(Order order) throws ServiceException;

    List<Order> getUserOrders(int userID) throws ServiceException;

    List<Order> getOrders(int offset, int rowsAmount) throws ServiceException;

    Order getOrder(int orderID) throws ServiceException;

    ReturnAct getReturnAct(int orderID) throws ServiceException;

    void updateOrder(Order order) throws ServiceException;
}
