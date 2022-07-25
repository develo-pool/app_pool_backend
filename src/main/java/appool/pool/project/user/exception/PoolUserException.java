package appool.pool.project.user.exception;

import appool.pool.project.exception.BaseException;
import appool.pool.project.exception.BaseExceptionType;

public class PoolUserException extends BaseException {

    private BaseExceptionType exceptionType;

    public PoolUserException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
