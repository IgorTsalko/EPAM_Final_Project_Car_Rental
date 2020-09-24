package by.epamtc.tsalko.service.exception;

public class UserNotFoundServiceException extends ServiceException {

    public UserNotFoundServiceException() {
    }

    public UserNotFoundServiceException(String message) {
        super(message);
    }

    public UserNotFoundServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundServiceException(Throwable cause) {
        super(cause);
    }
}
