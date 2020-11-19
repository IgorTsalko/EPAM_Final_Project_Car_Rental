package by.epamtc.tsalko.dao.impl;

import by.epamtc.tsalko.bean.car.Car;
import by.epamtc.tsalko.dao.connection.ConnectionPool;
import by.epamtc.tsalko.dao.exception.DAOException;
import by.epamtc.tsalko.dao.util.ScriptRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarDAOImplTest {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final CarDAOImpl carDAOImpl = new CarDAOImpl();

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
    void retrieve_all_cars() {
        assertDoesNotThrow(carDAOImpl::getAllCars);
    }

    @Test
    void retrieve_recommended_cars() throws DAOException {
        List<Car> recommendedCars = carDAOImpl.getRecommendedCars(3, 4);

        assertEquals(recommendedCars.size(), 3);
        for (Car c : recommendedCars) {
            assertNotEquals(c.getCarID(), 4);
        }
    }

    @Test
    void retrieve_car_by_id() throws DAOException {
        Car car = carDAOImpl.getCarByID(2);
        assertEquals(2, car.getCarID());
    }

    @Test
    void retrieve_all_car_images() {
        assertDoesNotThrow(() -> carDAOImpl.getAllCarImagesByID(3));
    }
}