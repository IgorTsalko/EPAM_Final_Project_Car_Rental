package by.epamtc.tsalko.service.impl;

import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.dao.DAOProvider;
import by.epamtc.tsalko.dao.OrderDAO;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.EntityNotFoundDAOException;
import by.epamtc.tsalko.service.OrderService;
import by.epamtc.tsalko.service.UserValidator;
import by.epamtc.tsalko.service.exception.EntityNotFoundServiceException;
import by.epamtc.tsalko.service.exception.InvalidInputDataServiceException;
import by.epamtc.tsalko.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Override
    public void createOrder(Order order) throws ServiceException {
        if (!UserValidator.orderValidation(order)) {
            logger.info(order + " failed validation");
            throw new InvalidInputDataServiceException();
        }

        if (!setUpTotalSum(order)) {
            throw new ServiceException("Could not set up total sum");
        }

        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO orderDAO = daoProvider.getOrderDAO();

        try {
            orderDAO.addOrder(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getUserOrders(int userID) throws ServiceException {
        List<Order> userOrders;

        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO orderDAO = daoProvider.getOrderDAO();

        try {
            userOrders = orderDAO.getUserOrders(userID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return userOrders;
    }

    @Override
    public List<Order> getOrders(int offset, int linesAmount) throws ServiceException {
        List<Order> allOrders;

        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO orderDAO = daoProvider.getOrderDAO();

        try {
            allOrders = orderDAO.getOrders(offset, linesAmount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return allOrders;
    }

    @Override
    public Order getOrder(int orderID) throws ServiceException {
        Order order;

        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO orderDAO = daoProvider.getOrderDAO();

        try {
            order = orderDAO.getOrder(orderID);
        } catch (EntityNotFoundDAOException e) {
            throw new EntityNotFoundServiceException(e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return order;
    }

    @Override
    public boolean updateOrder(Order order) throws ServiceException {
        if (!UserValidator.updateOrderValidation(order)) {
            logger.info(order + " failed validation");
            throw new InvalidInputDataServiceException();
        }

        boolean updated;

        if (!setUpTotalSum(order)) {
            throw new ServiceException("Could not set up total sum");
        }

        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO orderDAO = daoProvider.getOrderDAO();

        try {
            updated = orderDAO.updateOrder(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return updated;
    }

    @Override
    public void setPayment(Order order) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO orderDAO = daoProvider.getOrderDAO();

        try {
            orderDAO.setPayment(order);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private boolean setUpTotalSum(Order order) {
        boolean setUp = false;
        LocalDate pickUpDate = order.getPickUpDate();
        LocalDate dropOffDate = order.getDropOffDate();

        if (pickUpDate != null && dropOffDate != null) {
            long amountOfDays = ChronoUnit.DAYS.between(pickUpDate, dropOffDate);
            double pricePerDay = order.getCar().getPricePerDay();
            double totalSum = amountOfDays * pricePerDay;

            double discount = order.getDiscount();
            if (discount > 0) {
                totalSum = (pricePerDay * (1 - discount / 100)) * amountOfDays;
            }
            order.setTotalSum(totalSum);
            setUp = true;
        }

        return setUp;
    }
}
