package app.pool.project.exception;

public class MessageNotFound extends BaseException {

    private static final String MESSAGE = "존재하지 않는 메시지입니다.";

    public MessageNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return null;
    }
}
