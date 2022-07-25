package appool.pool.project.message.exception;

import appool.pool.project.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum MessageExceptionType implements BaseExceptionType {
    MESSAGE_NOT_FOUND(700, HttpStatus.NOT_FOUND, "찾으시는 메시지가 없습니다."),
    NOT_AUTHORITY_UPDATE_MESSAGE(701, HttpStatus.FORBIDDEN, "메시지를 수정할 권한이 없습니다."),
    NOT_AUTHORITY_DELETE_MESSAGE(702, HttpStatus.FORBIDDEN, "메시지를 삭제할 권한이 없습니다.");

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    MessageExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
