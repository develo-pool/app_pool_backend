package app.pool.project.exception;

public class InvalidRequest extends BaseException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public String fieldName;
    public String message;

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return null;
    }
}
