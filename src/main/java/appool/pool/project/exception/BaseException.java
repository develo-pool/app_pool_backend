package appool.pool.project.exception;


public abstract class BaseException extends RuntimeException{
    public abstract BaseExceptionType getExceptionType();
}
