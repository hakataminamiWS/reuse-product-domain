package jp.my.spring.rest_open_api.application.exception;

/**
 * 登録されていない Attribute を指定した場合にスローされる例外。
 */
public class InvalidAttributeException extends RuntimeException {

    public InvalidAttributeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAttributeException(String message) {
        super(message);
    }

}
