package jp.my.spring.rest_open_api.application.exception;

public class InvalidAttributeValueException extends RuntimeException {

    public InvalidAttributeValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAttributeValueException(String message) {
        super(message);
    }
}
