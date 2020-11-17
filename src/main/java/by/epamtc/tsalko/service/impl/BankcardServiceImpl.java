package by.epamtc.tsalko.service.impl;

import by.epamtc.tsalko.bean.Bankcard;
import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.dao.BankcardDAO;
import by.epamtc.tsalko.dao.DAOProvider;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.service.BankcardService;
import by.epamtc.tsalko.service.util.UserValidator;
import by.epamtc.tsalko.service.exception.InvalidInputDataServiceException;
import by.epamtc.tsalko.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BankcardServiceImpl implements BankcardService {

    private static final Logger logger = LogManager.getLogger(BankcardServiceImpl.class);

    @Override
    public boolean makePayment(Bankcard bankcard, Order order) {
        double totalSum = order.getTotalSum();

        if (UserValidator.bankCardValidation(bankcard)) {
            // We make a mock payment by "contacting" a third-party service,
            // transfer bankcard data and always get true
            order.setPaid(true);
            logger.info(String.format(
                    "Mock payment in the amount of: %.2f is made. UserID: %d; bankcardNumber: %d",
                    totalSum, bankcard.getUserID(), bankcard.getBankcardNumber()));
            return true;
        } else {
            logger.info(bankcard + " failed validation");
            return false;
        }
    }

    @Override
    public boolean makePayment(int bankcardID, Order order) {
        double totalSum = order.getTotalSum();

        DAOProvider daoProvider = DAOProvider.getInstance();
        BankcardDAO bankcardDAO = daoProvider.getBankcardDAO();

        Bankcard bankcard;

        try {
            bankcard = bankcardDAO.getUserBankcard(bankcardID);
        } catch (DAOException e) {
            logger.info("Could not make payment for bankcardID: " + bankcardID);
            return false;
        }

        if (UserValidator.bankCardValidation(bankcard)) {
            // We make a mock payment by "contacting" a third-party service,
            // transfer bankcard data and always get true
            order.setPaid(true);
            logger.info(String.format(
                    "Mock payment in the amount of: %.2f is made. UserID: %d; bankcardNumber: %d",
                    totalSum, bankcard.getUserID(), bankcard.getBankcardNumber()));
        } else {
            logger.info("bankcardID: " + bankcardID + " failed validation");
            return false;
        }

        return true;
    }

    @Override
    public List<Bankcard> getUserBankcards(int userID) throws ServiceException {
        List<Bankcard> bankcards;

        DAOProvider daoProvider = DAOProvider.getInstance();
        BankcardDAO bankcardDAO = daoProvider.getBankcardDAO();

        try {
            bankcards = bankcardDAO.getUserBankcards(userID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return bankcards;
    }

    @Override
    public void createBankcard(Bankcard bankCard) throws ServiceException {
        if (!UserValidator.bankCardValidation(bankCard)) {
            logger.info(bankCard + " failed validation");
            throw new InvalidInputDataServiceException();
        }

        DAOProvider daoProvider = DAOProvider.getInstance();
        BankcardDAO bankcardDAO = daoProvider.getBankcardDAO();

        try {
            bankcardDAO.createBankcard(bankCard);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBankcard(int userID, int bankCardID) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        BankcardDAO bankcardDAO = daoProvider.getBankcardDAO();

        try {
            bankcardDAO.deleteBankcard(userID, bankCardID);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
