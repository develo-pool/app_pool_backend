package appool.pool.project.message.exception;

import appool.pool.project.exception.BaseException;
import appool.pool.project.exception.BaseExceptionType;

public class MessageException extends BaseException {

    private BaseExceptionType baseExceptionType;

    public MessageException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return null;
    }
}
