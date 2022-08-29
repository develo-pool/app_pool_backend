package appool.pool.project.brand_user.exception;

import appool.pool.project.exception.BaseException;
import appool.pool.project.exception.BaseExceptionType;

public class BrandUserException extends BaseException {

    private BaseExceptionType exceptionType;

    public BrandUserException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
