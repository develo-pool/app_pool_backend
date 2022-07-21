package app.pool.project.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class BaseException extends RuntimeException{

    private final Map<String, String> validation = new HashMap<>();

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException() {

    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }

    public abstract BaseExceptionType getExceptionType();
}
