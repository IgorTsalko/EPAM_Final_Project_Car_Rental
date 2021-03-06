package by.epamtc.tsalko.dao.exception;

public class EntityAlreadyExistsDAOException extends DAOException {

    public EntityAlreadyExistsDAOException() {
    }

    public EntityAlreadyExistsDAOException(String message) {
        super(message);
    }

    public EntityAlreadyExistsDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAlreadyExistsDAOException(Throwable cause) {
        super(cause);
    }
}
