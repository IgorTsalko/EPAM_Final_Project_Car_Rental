package by.epamtc.tsalko.service;

import by.epamtc.tsalko.service.impl.CarServiceImpl;
import by.epamtc.tsalko.service.impl.ContentServiceImpl;
import by.epamtc.tsalko.service.impl.UserServiceImpl;

public class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private final UserService userService = new UserServiceImpl();
    private final CarService carService = new CarServiceImpl();
    private final ContentService contentService = new ContentServiceImpl();

    private ServiceProvider() {}

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public CarService getCarService() {
        return carService;
    }

    public ContentService getContentService() {
        return contentService;
    }
}
