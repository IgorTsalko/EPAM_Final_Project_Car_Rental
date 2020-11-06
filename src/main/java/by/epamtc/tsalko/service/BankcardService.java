package by.epamtc.tsalko.service;

import by.epamtc.tsalko.bean.Bankcard;
import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.service.exception.ServiceException;

import java.util.List;

public interface BankcardService {

    boolean makePayment(Bankcard bankCard, Order order) throws ServiceException;
    boolean makePayment(int bankCardID, Order order) throws ServiceException;

    List<Bankcard> getUserBankcards(int userID) throws ServiceException;

    void createBankcard(Bankcard bankCard) throws ServiceException;

    void deleteBankcard(int userID, int bankCardID) throws ServiceException;
}
