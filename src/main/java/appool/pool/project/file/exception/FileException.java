package appool.pool.project.file.exception;

import appool.pool.project.exception.BaseException;
import appool.pool.project.exception.BaseExceptionType;

public class FileException extends BaseException {
    private BaseExceptionType exceptionType;

    public FileException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
