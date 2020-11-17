package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.content.OrderStatus;
import by.epamtc.tsalko.bean.content.Rating;
import by.epamtc.tsalko.bean.content.Role;
import by.epamtc.tsalko.dao.connection.ConnectionPool;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.exception.EntityNotFoundDAOException;
import by.epamtc.tsalko.dao.util.ScriptRunner;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContentDAOImplTest {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final ContentDAOImpl contentDAOImpl = new ContentDAOImpl();

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
    void retrieve_all_roles() throws DAOException {
        List<Role> expectedAllRoles = new ArrayList<>();
        Role role = new Role();
        role.setRoleID(1);
        role.setRoleName("customer");
        expectedAllRoles.add(role);

        role = new Role();
        role.setRoleID(2);
        role.setRoleName("admin");
        expectedAllRoles.add(role);

        List<Role> actualAllRoles = contentDAOImpl.getAllRoles();
        assertEquals(expectedAllRoles, actualAllRoles);
    }

    @Test
    void retrieve_role_by_id() throws DAOException {
        Role expectedRole = new Role();
        expectedRole.setRoleID(2);
        expectedRole.setRoleName("admin");

        Role actualRole = contentDAOImpl.getRoleByID(2);
        assertEquals(expectedRole, actualRole);
    }

    @Test
    void retrieve_role_by_not_existing_id() {
        assertThrows(EntityNotFoundDAOException.class, () -> contentDAOImpl.getRoleByID(252));
    }

    @Test
    void retrieve_all_ratings() throws DAOException {
        List<Rating> expectedAllRatings = new ArrayList<>();
        Rating rating = new Rating();
        rating.setRatingID(1);
        rating.setRatingName("C0");
        rating.setDiscount(0);
        expectedAllRatings.add(rating);

        rating = new Rating();
        rating.setRatingID(2);
        rating.setRatingName("C1");
        rating.setDiscount(3);
        expectedAllRatings.add(rating);

        rating = new Rating();
        rating.setRatingID(3);
        rating.setRatingName("C2");
        rating.setDiscount(7);
        expectedAllRatings.add(rating);

        rating = new Rating();
        rating.setRatingID(4);
        rating.setRatingName("C3");
        rating.setDiscount(12);
        expectedAllRatings.add(rating);

        List<Rating> actualAllRatings = contentDAOImpl.getAllRatings();
        assertEquals(expectedAllRatings, actualAllRatings);
    }

    @Test
    void retrieve_rating_by_id() throws DAOException {
        Rating expectedRating = new Rating();
        expectedRating.setRatingID(3);
        expectedRating.setRatingName("C2");
        expectedRating.setDiscount(7);

        Rating actualRating = contentDAOImpl.getRatingByID(3);
        assertEquals(expectedRating, actualRating);
    }

    @Test
    void retrieve_rating_by_not_existing_id() {
        assertThrows(EntityNotFoundDAOException.class, () -> contentDAOImpl.getRatingByID(252));
    }

    @Test
    void retrieve_all_order_statuses() throws DAOException {
        List<OrderStatus> expectedAllOrderStatuses = new ArrayList<>();
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setStatusID(1);
        orderStatus.setStatus("expired");
        expectedAllOrderStatuses.add(orderStatus);

        orderStatus = new OrderStatus();
        orderStatus.setStatusID(2);
        orderStatus.setStatus("new");
        expectedAllOrderStatuses.add(orderStatus);

        orderStatus = new OrderStatus();
        orderStatus.setStatusID(3);
        orderStatus.setStatus("processing");
        expectedAllOrderStatuses.add(orderStatus);

        orderStatus = new OrderStatus();
        orderStatus.setStatusID(4);
        orderStatus.setStatus("rent");
        expectedAllOrderStatuses.add(orderStatus);

        orderStatus = new OrderStatus();
        orderStatus.setStatusID(5);
        orderStatus.setStatus("ended");
        expectedAllOrderStatuses.add(orderStatus);

        orderStatus = new OrderStatus();
        orderStatus.setStatusID(6);
        orderStatus.setStatus("canceled");
        expectedAllOrderStatuses.add(orderStatus);

        orderStatus = new OrderStatus();
        orderStatus.setStatusID(7);
        orderStatus.setStatus("denied");
        expectedAllOrderStatuses.add(orderStatus);

        List<OrderStatus> actualAllOrderStatuses = contentDAOImpl.getAllOrderStatuses();
        assertEquals(expectedAllOrderStatuses, actualAllOrderStatuses);
    }

    @Test
    void retrieve_all_news() {
        assertDoesNotThrow(() -> contentDAOImpl.getAllNews());
    }
}