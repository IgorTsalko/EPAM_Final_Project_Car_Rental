package by.epamtc.tsalko.service.exception;

public class EntityNotFoundServiceException extends ServiceException {

    public EntityNotFoundServiceException() {
    }

    public EntityNotFoundServiceException(String message) {
        super(message);
    }

    public EntityNotFoundServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundServiceException(Throwable cause) {
        super(cause);
    }
}
