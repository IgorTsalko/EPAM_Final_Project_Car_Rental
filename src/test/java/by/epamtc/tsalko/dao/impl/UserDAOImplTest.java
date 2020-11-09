package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.user.AuthorizationData;
import by.epamtc.tsalko.bean.user.RegistrationData;
import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.dao.ScriptRunner;
import by.epamtc.tsalko.dao.connection.ConnectionPool;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.EntityAlreadyExistsDAOException;
import by.epamtc.tsalko.dao.exception.EntityNotFoundDAOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOImplTest {

    private static UserDAOImpl userDAOimpl = new UserDAOImpl();
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();

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
    void successful_authorization_test() throws DAOException {
        AuthorizationData authData = new AuthorizationData();
        authData.setLogin("Kiska_IUlka_test");
        authData.setPassword("2dbe5f10eed9523027160e70aa7d1ff4");

        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setLogin("Kiska_IUlka_test");
        expectedUser.setRole("admin");
        expectedUser.setRating("C2");
        expectedUser.setDiscount(7);
        expectedUser.setRegistrationDate(LocalDateTime.parse("2020-01-12 20:44:37",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        User actualUser = userDAOimpl.authorization(authData);
        assertEquals(expectedUser, actualUser);
    }

    @ParameterizedTest
    @MethodSource("incorrectAuthorizationParams")
    void failed_authorization_test(String login, String password) {
        UserDAOImpl userDAOimpl = new UserDAOImpl();
        AuthorizationData authData = new AuthorizationData();
        authData.setLogin(login);
        authData.setPassword(password);

        assertThrows(EntityNotFoundDAOException.class, () -> {
            userDAOimpl.authorization(authData);
        });
    }

    static Stream<Arguments> incorrectAuthorizationParams() {
        return Stream.of(
                Arguments.of("Kiska_IUlka_test", "sddfsvd"),
                Arguments.of("incorrect_login", "2dbe5f10eed9523027160e70aa7d1ff4"),
                Arguments.of("Kiska_IUlka_test", null),
                Arguments.of(null, "2dbe5f10eed9523027160e70aa7d1ff4"),
                Arguments.of(null, null)
        );
    }

    @Test
    void successful_registration_test() throws DAOException {
        RegistrationData regData = new RegistrationData();
        regData.setLogin("TestUser");
        regData.setPassword("fed3b61b26081849378080b34e693d2e");
        regData.setPhone("+375331232312");
        regData.setEmail("test@email.ru");
        userDAOimpl.registration(regData);
    }

    @Test
    void registration_existed_user() {
        RegistrationData regData = new RegistrationData();
        regData.setLogin("TestUser");
        regData.setPassword("fed3b61b26081849378080b34e693d2e");
        regData.setPhone("+375331232312");
        regData.setEmail("test@email.ru");
        assertThrows(EntityAlreadyExistsDAOException.class, () -> {
            userDAOimpl.registration(regData);
        });
    }

    @AfterAll
    static void disconnectDB() {
        connectionPool.dropAllConnections();
    }
}
