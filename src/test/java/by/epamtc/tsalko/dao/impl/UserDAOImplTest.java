package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.content.Rating;
import by.epamtc.tsalko.bean.content.Role;
import by.epamtc.tsalko.bean.user.*;
import by.epamtc.tsalko.dao.util.ScriptRunner;
import by.epamtc.tsalko.dao.connection.ConnectionPool;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.EntityAlreadyExistsDAOException;
import by.epamtc.tsalko.dao.exception.EntityNotFoundDAOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOImplTest {

    private static final UserDAOImpl userDAOImpl = new UserDAOImpl();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

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

    @AfterAll
    static void disconnectDB() {
        connectionPool.dropAllConnections();
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
        expectedUser.setRegistrationDate(LocalDateTime.parse("2020-01-12T20:44:37"));

        User actualUser = userDAOImpl.authorization(authData);
        assertEquals(expectedUser, actualUser);
    }

    @ParameterizedTest
    @CsvSource({
            "Kiska_IUlka_test, sddfsvd",
            "Kiska_IUlka_test, ''",
            "Kiska_IUlka_test, ",
            "incorrect_login, 2dbe5f10eed9523027160e70aa7d1ff4",
            "'' , 2dbe5f10eed9523027160e70aa7d1ff4",
            " , 2dbe5f10eed9523027160e70aa7d1ff4",
            " , ",
            "'' , ''",
    })
    void failed_authorization_test(String login, String password) {
        UserDAOImpl userDAOimpl = new UserDAOImpl();
        AuthorizationData authData = new AuthorizationData();
        authData.setLogin(login);
        authData.setPassword(password);

        assertThrows(EntityNotFoundDAOException.class, () -> userDAOimpl.authorization(authData));
    }

    @Test
    void successful_registration_test() throws DAOException, SQLException {
        RegistrationData expectedRegData = new RegistrationData();
        expectedRegData.setLogin("TestUser");
        expectedRegData.setPassword("fed3b61b26081849378080b34e693d2e");
        expectedRegData.setPhone("+375331232312");
        expectedRegData.setEmail("test@email.ru");
        userDAOImpl.registration(expectedRegData);

        Connection con = connectionPool.takeConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE user_login=?");
        ps.setString(1, "TestUser");
        ResultSet rs = ps.executeQuery();

        RegistrationData actualRegData = new RegistrationData();
        if (rs.next()) {
            actualRegData.setLogin(rs.getString(2));
            actualRegData.setPassword(rs.getString(3));
            actualRegData.setPhone(rs.getString(6));
            actualRegData.setEmail(rs.getString(7));
        }
        connectionPool.closeConnection(con, ps, rs);

        assertEquals(expectedRegData, actualRegData);
    }

    @Test
    void registration_existed_user() {
        RegistrationData regData = new RegistrationData();
        regData.setLogin("Kiska_IUlka_test");
        regData.setPassword("2dbe5f10eed9523027160e70aa7d1ff4");
        regData.setPhone("+375295288517");
        regData.setEmail("kiska-iulka-test@mail.ru");
        assertThrows(EntityAlreadyExistsDAOException.class, () -> userDAOImpl.registration(regData));
    }

    @Test
    void retrieve_user_details() throws DAOException {
        UserDetails expectedUserDetails = new UserDetails();

        expectedUserDetails.setUserID(15);
        expectedUserDetails.setUserLogin("kiskaKat");
        expectedUserDetails.setUserPhone("+375298938399");
        expectedUserDetails.setUserEmail("kiskaKat@mail.ru");
        expectedUserDetails.setUserRegistrationDate(LocalDateTime.parse("2020-01-12T20:44:37"));

        Role role = new Role();
        role.setRoleID(1);
        role.setRoleName("customer");
        expectedUserDetails.setUserRole(role);

        Rating rating = new Rating();
        rating.setRatingID(1);
        rating.setRatingName("C0");
        rating.setDiscount(0);
        expectedUserDetails.setUserRating(rating);

        UserDetails actualUserDetails = userDAOImpl.getUserDetails(15);
        assertEquals(expectedUserDetails, actualUserDetails);
    }

    @Test
    void retrieve_not_existing_user_details() {
        assertThrows(EntityNotFoundDAOException.class, () -> userDAOImpl.getUserDetails(252));
    }

    @Test
    void retrieve_users_list() throws DAOException {
        List<User> expectedUsersList = new ArrayList<>();

        User user = new User();
        user.setId(1);
        user.setLogin("Kiska_IUlka_test");
        user.setRole("admin");
        user.setRating("C2");
        user.setDiscount(7);
        user.setRegistrationDate(LocalDateTime.parse("2020-01-12T20:44:37"));
        expectedUsersList.add(user);

        user = new User();
        user.setId(7);
        user.setLogin("Samoilov-IUrgen");
        user.setRole("customer");
        user.setRating("C0");
        user.setDiscount(0);
        user.setRegistrationDate(LocalDateTime.parse("2020-01-12T20:44:37"));
        expectedUsersList.add(user);

        user = new User();
        user.setId(8);
        user.setLogin("Petr");
        user.setRole("customer");
        user.setRating("C0");
        user.setDiscount(0);
        user.setRegistrationDate(LocalDateTime.parse("2020-01-12T20:44:37"));
        expectedUsersList.add(user);

        List<User> actualUsersList = userDAOImpl.getUsers(2, 3);
        assertEquals(expectedUsersList, actualUsersList);
    }

    @Test
    void retrieve_users_list_beyond_number_of_users() throws DAOException {
        assertEquals(0, userDAOImpl.getUsers(258, 10).size());
    }

    @Test
    void retrieve_user_passport() throws DAOException {
        Passport expectedPassport = new Passport();
        expectedPassport.setUserID(4);
        expectedPassport.setPassportID(4);
        expectedPassport.setPassportSeries("MP");
        expectedPassport.setPassportNumber("2398745");
        expectedPassport.setPassportDateOfIssue(LocalDate.parse("2011-12-03"));
        expectedPassport.setPassportIssuedBy("Фрунзенское РУВД г.Минска");
        expectedPassport.setPassportUserAddress("Нёманская улица, 34, 52, Минск, Беларусь");
        expectedPassport.setPassportUserSurname("Абрамчик");
        expectedPassport.setPassportUserName("Ольга");
        expectedPassport.setPassportUserThirdName("Николаевна");
        expectedPassport.setPassportUserDateOfBirth(LocalDate.parse("1987-12-03"));

        Passport actualPassport = userDAOImpl.getUserPassport(4);
        assertEquals(expectedPassport, actualPassport);
    }

    @Test
    void retrieve_not_existing_passport() throws DAOException {
        assertNull(userDAOImpl.getUserPassport(252));
    }

    @Test
    void update_user_details() throws DAOException, SQLException {
        UserDetails expectedUserDetails = new UserDetails();
        expectedUserDetails.setUserID(2);
        expectedUserDetails.setUserPhone("+375(33)357-12-14");
        expectedUserDetails.setUserEmail("sasha_85_test@mail.ru");

        Role role = new Role();
        role.setRoleID(2);
        expectedUserDetails.setUserRole(role);

        Rating rating = new Rating();
        rating.setRatingID(3);
        expectedUserDetails.setUserRating(rating);

        assertTrue(userDAOImpl.updateUserDetails(expectedUserDetails));

        Connection con = connectionPool.takeConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE user_id=?");
        ps.setInt(1, 2);
        ResultSet rs = ps.executeQuery();

        UserDetails actualUserDetails = new UserDetails();
        if (rs.next()) {
            actualUserDetails.setUserID(rs.getInt(1));
            actualUserDetails.setUserPhone(rs.getString(6));
            actualUserDetails.setUserEmail(rs.getString(7));

            role = new Role();
            role.setRoleID(rs.getInt(4));
            actualUserDetails.setUserRole(role);

            rating = new Rating();
            rating.setRatingID(rs.getInt(5));
            actualUserDetails.setUserRating(rating);
        }
        connectionPool.closeConnection(con, ps, rs);

        assertEquals(expectedUserDetails, actualUserDetails);
    }

    @Test
    void update_user_details_if_does_not_exist() throws DAOException {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserID(252);
        userDetails.setUserPhone("+375(33)357-12-14");
        userDetails.setUserEmail("sasha_85_test@mail.ru");

        Role role = new Role();
        role.setRoleID(2);
        role.setRoleName("admin");
        userDetails.setUserRole(role);

        Rating rating = new Rating();
        rating.setRatingID(3);
        rating.setRatingName("C2");
        rating.setDiscount(7);
        userDetails.setUserRating(rating);

        assertFalse(userDAOImpl.updateUserDetails(userDetails));
    }

    @Test
    void update_user_passport() throws DAOException, SQLException {
        Passport expectedPassport = new Passport();
        expectedPassport.setUserID(2);
        expectedPassport.setPassportSeries("MP");
        expectedPassport.setPassportNumber("2123516");
        expectedPassport.setPassportDateOfIssue(LocalDate.parse("2020-08-24"));
        expectedPassport.setPassportIssuedBy("Ленинское РУВД г.Минска");
        expectedPassport.setPassportUserAddress("улица Якубова, 66к2, 235, Минск, Беларусь");
        expectedPassport.setPassportUserSurname("Петров");
        expectedPassport.setPassportUserName("Александр");
        expectedPassport.setPassportUserThirdName("Васильевич");
        expectedPassport.setPassportUserDateOfBirth(LocalDate.parse("1985-05-11"));

        assertTrue(userDAOImpl.updateUserPassport(expectedPassport));

        Connection con = connectionPool.takeConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM user_passports WHERE user_id=?");
        ps.setInt(1, 2);
        ResultSet rs = ps.executeQuery();

        Passport actualPassport = new Passport();
        if (rs.next()) {
            actualPassport.setUserID(rs.getInt(11));
            actualPassport.setPassportSeries(rs.getString(2));
            actualPassport.setPassportNumber(rs.getString(3));
            actualPassport.setPassportDateOfIssue(rs.getDate(4).toLocalDate());
            actualPassport.setPassportIssuedBy(rs.getString(5));
            actualPassport.setPassportUserAddress(rs.getString(6));
            actualPassport.setPassportUserSurname(rs.getString(7));
            actualPassport.setPassportUserName(rs.getString(8));
            actualPassport.setPassportUserThirdName(rs.getString(9));
            actualPassport.setPassportUserDateOfBirth(rs.getDate(10).toLocalDate());
        }
        connectionPool.closeConnection(con, ps, rs);

        assertEquals(expectedPassport, actualPassport);
    }

    @Test
    void update_user_passport_if_passport_does_not_exist() throws DAOException, SQLException {
        Passport expectedPassport = new Passport();
        expectedPassport.setUserID(10);
        expectedPassport.setPassportSeries("MC");
        expectedPassport.setPassportNumber("2458624");
        expectedPassport.setPassportDateOfIssue(LocalDate.parse("2016-03-10"));
        expectedPassport.setPassportIssuedBy("Ленинское РУВД г.Минска");
        expectedPassport.setPassportUserAddress("улица Мельникайте, 9, 14, Минск, Беларусь");
        expectedPassport.setPassportUserSurname("Цалко");
        expectedPassport.setPassportUserName("Игорь");
        expectedPassport.setPassportUserThirdName("Петрович");
        expectedPassport.setPassportUserDateOfBirth(LocalDate.parse("1994-01-04"));

        assertTrue(userDAOImpl.updateUserPassport(expectedPassport));

        Connection con = connectionPool.takeConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM user_passports WHERE user_id=?");
        ps.setInt(1, 10);
        ResultSet rs = ps.executeQuery();

        Passport actualPassport = new Passport();
        if (rs.next()) {
            actualPassport.setUserID(rs.getInt(11));
            actualPassport.setPassportSeries(rs.getString(2));
            actualPassport.setPassportNumber(rs.getString(3));
            actualPassport.setPassportDateOfIssue(rs.getDate(4).toLocalDate());
            actualPassport.setPassportIssuedBy(rs.getString(5));
            actualPassport.setPassportUserAddress(rs.getString(6));
            actualPassport.setPassportUserSurname(rs.getString(7));
            actualPassport.setPassportUserName(rs.getString(8));
            actualPassport.setPassportUserThirdName(rs.getString(9));
            actualPassport.setPassportUserDateOfBirth(rs.getDate(10).toLocalDate());
        }
        connectionPool.closeConnection(con, ps, rs);

        assertEquals(expectedPassport, actualPassport);
    }

    @Test
    void update_user_passport_if_user_does_not_exist() {
        Passport passport = new Passport();
        passport.setUserID(252);
        passport.setPassportSeries("MP");
        passport.setPassportNumber("2123516");
        passport.setPassportDateOfIssue(LocalDate.parse("2020-08-24"));
        passport.setPassportIssuedBy("Ленинское РУВД г.Минска");
        passport.setPassportUserAddress("улица Якубова, 66к2, 235, Минск, Беларусь");
        passport.setPassportUserSurname("Петров");
        passport.setPassportUserName("Александр");
        passport.setPassportUserThirdName("Васильевич");
        passport.setPassportUserDateOfBirth(LocalDate.parse("1985-05-11"));

        assertThrows(EntityNotFoundDAOException.class, () -> userDAOImpl.updateUserPassport(passport));
    }

    @Test
    void update_user_login() throws DAOException, SQLException {
        String expectedLogin = "loplov_new_login";

        assertTrue(userDAOImpl.updateUserLogin(11, expectedLogin));

        Connection con = connectionPool.takeConnection();
        PreparedStatement ps = con.prepareStatement("SELECT user_login FROM users WHERE user_id=?");
        ps.setInt(1, 11);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String actualLogin = rs.getString("user_login");
        connectionPool.closeConnection(con, ps, rs);

        assertEquals(expectedLogin, actualLogin);
    }

    @Test
    void update_user_login_if_user_does_not_exist() throws DAOException {
        assertFalse(userDAOImpl.updateUserLogin(252, "some_new_login"));
    }

    @Test
    void update_user_password() throws DAOException, SQLException {
        String oldPassword = "c6f12a67bfb0d06f4a704ebb9e698946";
        String expectedPassword = "2dbe5f10eed9523027160e70aa7d1ff4";

        assertTrue(userDAOImpl.updateUserPassword(12, oldPassword, expectedPassword));

        Connection con = connectionPool.takeConnection();
        PreparedStatement ps = con.prepareStatement("SELECT user_password FROM users WHERE user_id=?");
        ps.setInt(1, 12);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String actualPassword = rs.getString("user_password");

        connectionPool.closeConnection(con, ps, rs);

        assertEquals(expectedPassword, actualPassword);
    }

    @Test
    void update_user_password_if_inccorrect_old_password() throws DAOException {
        assertFalse(userDAOImpl.updateUserPassword(
                11,
                "incorrect_old_password",
                "2dbe5f10eed9523027160e70aa7d1ff4"));
    }

    @Test
    void update_user_password_if_user_does_not_exist() throws DAOException {
        assertFalse(userDAOImpl.updateUserPassword(
                252,
                "c6f12a67bfb0d06f4a704ebb9e698946",
                "2dbe5f10eed9523027160e70aa7d1ff4"));
    }
}
