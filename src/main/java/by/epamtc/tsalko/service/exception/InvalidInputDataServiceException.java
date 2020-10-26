package by.epamtc.tsalko.service.exception;

public class InvalidInputDataServiceException extends ServiceException {

    public InvalidInputDataServiceException() {
    }

    public InvalidInputDataServiceException(String message) {
        super(message);
    }

    public InvalidInputDataServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputDataServiceException(Throwable cause) {
        super(cause);
    }
}
