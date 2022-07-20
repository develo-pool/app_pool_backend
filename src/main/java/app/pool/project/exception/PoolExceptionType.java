package app.pool.project.exception;

import org.springframework.http.HttpStatus;

public interface PoolExceptionType {
    int getErrorCode();

    HttpStatus getHttpStatus();

    String getErrorMessage();
}
