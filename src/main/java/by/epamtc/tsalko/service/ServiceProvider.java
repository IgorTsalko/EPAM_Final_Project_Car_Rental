package by.epamtc.tsalko.service;

import by.epamtc.tsalko.service.impl.*;

public class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private final UserService userService = new UserServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();
    private final CarService carService = new CarServiceImpl();
    private final BankcardService bankcardService = new BankcardServiceImpl();
    private final ContentService contentService = new ContentServiceImpl();

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public CarService getCarService() {
        return carService;
    }

    public BankcardService getBankcardService() {
        return bankcardService;
    }

    public ContentService getContentService() {
        return contentService;
    }
}
