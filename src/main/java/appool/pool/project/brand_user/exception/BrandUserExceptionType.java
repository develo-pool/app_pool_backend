package appool.pool.project.brand_user.exception;

import appool.pool.project.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum BrandUserExceptionType implements BaseExceptionType {
    NOT_FOUND_BRAND(701, HttpStatus.NOT_FOUND, "브랜드 정보가 없습니다.");

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    BrandUserExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
