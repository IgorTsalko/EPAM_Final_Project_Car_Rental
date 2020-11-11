package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.content.Rating;
import by.epamtc.tsalko.bean.content.Role;
import by.epamtc.tsalko.bean.user.*;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOImplTest {

    private static final UserDAOImpl userDAOimpl = new UserDAOImpl();
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

        assertThrows(EntityNotFoundDAOException.class, () -> userDAOimpl.authorization(authData));
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
        regData.setLogin("Kiska_IUlka_test");
        regData.setPassword("2dbe5f10eed9523027160e70aa7d1ff4");
        regData.setPhone("+375295288517");
        regData.setEmail("kiska-iulka-test@mail.ru");
        assertThrows(EntityAlreadyExistsDAOException.class, () -> userDAOimpl.registration(regData));
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

        UserDetails actualUserDetails = userDAOimpl.getUserDetails(15);
        assertEquals(expectedUserDetails, actualUserDetails);
    }

    @Test
    void retrieve_not_existing_user_details() {
        assertThrows(EntityNotFoundDAOException.class, () -> userDAOimpl.getUserDetails(252));
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

        List<User> actualUsersList = userDAOimpl.getUsers(2, 3);
        assertEquals(expectedUsersList, actualUsersList);
    }

    @Test
    void retrieve_users_list_beyond_number_of_users() throws DAOException {
        assertEquals(0, userDAOimpl.getUsers(258, 10).size());
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

        Passport actualPassport = userDAOimpl.getUserPassport(4);
        assertEquals(expectedPassport, actualPassport);
    }

    @Test
    void retrieve_not_existing_passport() throws DAOException {
        assertNull(userDAOimpl.getUserPassport(252));
    }

    @Test
    void update_user_details() throws DAOException {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserID(2);
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

        assertTrue(userDAOimpl.updateUserDetails(userDetails));
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

        assertFalse(userDAOimpl.updateUserDetails(userDetails));
    }

    @Test
    void update_user_passport() throws DAOException {
        Passport passport = new Passport();
        passport.setUserID(2);
        passport.setPassportSeries("MP");
        passport.setPassportNumber("2123516");
        passport.setPassportDateOfIssue(LocalDate.parse("2020-08-24"));
        passport.setPassportIssuedBy("Ленинское РУВД г.Минска");
        passport.setPassportUserAddress("улица Якубова, 66к2, 235, Минск, Беларусь");
        passport.setPassportUserSurname("Петров");
        passport.setPassportUserName("Александр");
        passport.setPassportUserThirdName("Васильевич");
        passport.setPassportUserDateOfBirth(LocalDate.parse("1985-05-11"));

        assertTrue(userDAOimpl.updateUserPassport(passport));
    }

    @Test
    void update_user_passport_if_passport_does_not_exist() throws DAOException {
        Passport passport = new Passport();
        passport.setUserID(10);
        passport.setPassportSeries("MC");
        passport.setPassportNumber("2458624");
        passport.setPassportDateOfIssue(LocalDate.parse("2016-03-10"));
        passport.setPassportIssuedBy("Ленинское РУВД г.Минска");
        passport.setPassportUserAddress("улица Мельникайте, 9, 14, Минск, Беларусь");
        passport.setPassportUserSurname("Цалко");
        passport.setPassportUserName("Игорь");
        passport.setPassportUserThirdName("Петрович");
        passport.setPassportUserDateOfBirth(LocalDate.parse("1994-01-04"));

        assertTrue(userDAOimpl.updateUserPassport(passport));
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

        assertThrows(EntityNotFoundDAOException.class, () -> userDAOimpl.updateUserPassport(passport));
    }

    @Test
    void update_user_login() throws DAOException {
        assertTrue(userDAOimpl.updateUserLogin(11, "loplov_new_login"));
    }

    @Test
    void update_user_login_if_user_does_not_exist() throws DAOException {
        assertFalse(userDAOimpl.updateUserLogin(252, "some_new_login"));
    }

    @Test
    void update_user_password() throws DAOException {
        assertTrue(userDAOimpl.updateUserPassword(
                12,
                "c6f12a67bfb0d06f4a704ebb9e698946",
                "2dbe5f10eed9523027160e70aa7d1ff4"));
    }

    @Test
    void update_user_password_if_inccorrect_old_password() throws DAOException {
        assertFalse(userDAOimpl.updateUserPassword(
                11,
                "incorrect_old_password",
                "2dbe5f10eed9523027160e70aa7d1ff4"));
    }

    @Test
    void update_user_password_if_user_does_not_exist() throws DAOException {
        assertFalse(userDAOimpl.updateUserPassword(
                252,
                "c6f12a67bfb0d06f4a704ebb9e698946",
                "2dbe5f10eed9523027160e70aa7d1ff4"));
    }

    @AfterAll
    static void disconnectDB() {
        connectionPool.dropAllConnections();
    }
}
