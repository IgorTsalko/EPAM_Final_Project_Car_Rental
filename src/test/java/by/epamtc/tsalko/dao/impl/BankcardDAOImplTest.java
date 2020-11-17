package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.Bankcard;
import by.epamtc.tsalko.dao.connection.ConnectionPool;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.EntityNotFoundDAOException;
import by.epamtc.tsalko.dao.exception.UpdateDataDAOException;
import by.epamtc.tsalko.dao.util.ScriptRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankcardDAOImplTest {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final BankcardDAOImpl bankcardDAOImpl = new BankcardDAOImpl();

    private static final String RELOAD_DB_SQL_FILE
            = "E:\\Studies\\Java\\JavaWebDevelopment\\FinalProject\\DB\\car_rental_test_DLL.sql";

    @BeforeAll
    static void reloadTestDB() throws IOException, SQLException {
        Connection con = connectionPool.takeConnection();
        ScriptRunner scriptRunner = new ScriptRunner(con, false, false);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(RELOAD_DB_SQL_FILE));
        scriptRunner.runScript(bufferedReader);
        connectionPool.closeConnection(con, null);
    }

    @Test
    void retrieve_user_bankcards() throws DAOException {
        List<Bankcard> expectedBankcards = new ArrayList<>();

        Bankcard bankcard = new Bankcard();
        bankcard.setUserID(6);
        bankcard.setBankcardID(8);
        bankcard.setBankcardNumber(5422656685536988L);
        expectedBankcards.add(bankcard);

        bankcard = new Bankcard();
        bankcard.setUserID(6);
        bankcard.setBankcardID(9);
        bankcard.setBankcardNumber(5634752556855354L);
        expectedBankcards.add(bankcard);

        List<Bankcard> actualBankcards = bankcardDAOImpl.getUserBankcards(6);
        assertEquals(expectedBankcards, actualBankcards);
    }

    @Test
    void retrieve_user_bankcards_if_user_does_not_exist() throws DAOException {
        assertEquals(0, bankcardDAOImpl.getUserBankcards(252).size());
    }

    @Test
    void retrieve_user_bankcard() throws DAOException {
        Bankcard expectedBankcard = new Bankcard();
        expectedBankcard.setUserID(2);
        expectedBankcard.setBankcardID(5);
        expectedBankcard.setBankcardNumber(4578232158745698L);
        expectedBankcard.setBankcardValidTrue(LocalDate.parse("2023-02-01"));
        expectedBankcard.setBankcardUserFirstname("ALEKSANDR");
        expectedBankcard.setBankcardUserLastname("PETROV");
        expectedBankcard.setBankcardCVV("120");

        Bankcard actualBankcard = bankcardDAOImpl.getUserBankcard(5);
        assertEquals(expectedBankcard, actualBankcard);
    }

    @Test
    void retrieve_user_bankcard_if_bankcard_does_not_exist() {
        assertThrows(EntityNotFoundDAOException.class,
                () -> bankcardDAOImpl.getUserBankcard(252));
    }

    @Test
    void add_new_bankcard() throws DAOException, SQLException {
        Bankcard expectedBankcard = new Bankcard();
        expectedBankcard.setUserID(8);
        expectedBankcard.setBankcardNumber(4328233641745698L);
        expectedBankcard.setBankcardValidTrue(LocalDate.parse("2021-06-01"));
        expectedBankcard.setBankcardUserFirstname("PETR");
        expectedBankcard.setBankcardUserLastname("SIDOROV");
        expectedBankcard.setBankcardCVV("046");

        bankcardDAOImpl.createBankcard(expectedBankcard);

        Connection con = connectionPool.takeConnection();
        PreparedStatement ps =
                con.prepareStatement("SELECT * FROM user_bankcards WHERE user_bankcard_id=?");
        ps.setInt(1, 11);
        ResultSet rs = ps.executeQuery();

        Bankcard actualBankcard = new Bankcard();
        if (rs.next()) {
            actualBankcard.setUserID(rs.getInt("user_id"));
            actualBankcard.setBankcardNumber(rs.getLong("user_bankcard_number"));
            actualBankcard.setBankcardValidTrue(
                    rs.getDate("user_bankcard_valid_true").toLocalDate());
            actualBankcard.setBankcardUserFirstname(rs.getString("user_bankcard_firstname"));
            actualBankcard.setBankcardUserLastname(rs.getString("user_bankcard_lastname"));
            actualBankcard.setBankcardCVV(rs.getString("user_bankcard_cvv"));
        }
        connectionPool.closeConnection(con, ps, rs);

        assertEquals(expectedBankcard, actualBankcard);
    }

    @Test
    void add_new_bankcard_if_bankcard_exists() {
        Bankcard bankcard = new Bankcard();
        bankcard.setUserID(1);
        bankcard.setBankcardNumber(5456211109041115L);
        bankcard.setBankcardValidTrue(LocalDate.parse("2021-05-01"));
        bankcard.setBankcardUserFirstname("IULIIA");
        bankcard.setBankcardUserLastname("ABAKUNCHIK");
        bankcard.setBankcardCVV("009");

        assertThrows(DAOException.class, () -> bankcardDAOImpl.createBankcard(bankcard));
    }

    @Test
    void add_new_bankcard_if_user_does_not_exist() {
        Bankcard bankcard = new Bankcard();
        bankcard.setUserID(252);
        bankcard.setBankcardNumber(4122366648755698L);
        bankcard.setBankcardValidTrue(LocalDate.parse("2022-04-01"));
        bankcard.setBankcardUserFirstname("KIRILL");
        bankcard.setBankcardUserLastname("VICOTIEV");
        bankcard.setBankcardCVV("024");

        assertThrows(DAOException.class, () -> bankcardDAOImpl.createBankcard(bankcard));
    }

    @Test
    void delete_bankcard() throws DAOException, SQLException {
        bankcardDAOImpl.deleteBankcard(7, 10);

        Connection con = connectionPool.takeConnection();
        PreparedStatement ps =
                con.prepareStatement("SELECT * FROM user_bankcards WHERE user_bankcard_id=?");
        ps.setInt(1, 10);
        ResultSet rs = ps.executeQuery();

        assertFalse(rs.next());
        connectionPool.closeConnection(con, ps, rs);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 8",
            "5, 4",
            "6, 24",
            "252, 7",
    })
    void delete_bankcard_incorrect_data(int userID, int bankCardID) {
        assertThrows(UpdateDataDAOException.class, ()
                -> bankcardDAOImpl.deleteBankcard(userID, bankCardID));
    }
}