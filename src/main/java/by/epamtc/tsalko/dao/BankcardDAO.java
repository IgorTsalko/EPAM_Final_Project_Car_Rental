package by.epamtc.tsalko.dao;

import by.epamtc.tsalko.bean.Bankcard;
import by.epamtc.tsalko.dao.exception.DAOException;

import java.util.List;

public interface BankcardDAO {

    List<Bankcard> getUserBankcards(int userID) throws DAOException;
    Bankcard getUserBankcard(int bankcardID) throws DAOException;

    void createBankcard(Bankcard bankcard) throws DAOException;

    void deleteBankcard(int userID, int bankCardID) throws DAOException;
}
