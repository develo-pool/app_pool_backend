package app.pool.project.exception;

public class PoolUserException extends BaseException {

    private BaseExceptionType exceptionType;

    public PoolUserException(BaseExceptionType exceptionType) {
        super();
        this.exceptionType = exceptionType;
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
