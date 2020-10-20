package by.epamtc.tsalko.service.exception;

public class EntityAlreadyExistsServiceException extends ServiceException {

    public EntityAlreadyExistsServiceException() {
    }

    public EntityAlreadyExistsServiceException(String message) {
        super(message);
    }

    public EntityAlreadyExistsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAlreadyExistsServiceException(Throwable cause) {
        super(cause);
    }
}
