package jp.my.spring.rest_open_api.application.exception;

public class UniqueConstraintViolationException extends RuntimeException {

    public UniqueConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueConstraintViolationException(String message) {
        super(message);
    }

}