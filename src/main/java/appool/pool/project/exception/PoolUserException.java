package appool.pool.project.exception;

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
